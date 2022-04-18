package com.william.atm.service;

import com.william.atm.domain.Account;
import com.william.atm.exception.AccountNotFoundException;
import com.william.atm.exception.InvalidPinException;
import com.william.atm.repository.AccountRepository;
import com.william.atm.response.BalanceResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);

    @Test
    void validatePinTest(){
        Account account = new Account();
        account.setPin((short) 1234);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository);
        when(accountRepository.findById(123456789L)).thenReturn(Optional.of(account));

        accountService.validatePin(123456789L, (short) 1234);
        assertThrows(InvalidPinException.class, () -> accountService.validatePin(123456789L, (short) 4321));
    }

    @Test
    void getAccountTest(){
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository);
        when(accountRepository.findById(123456789L)).thenReturn(Optional.of(new Account()));

        accountService.getAccount(123456789L);
        assertThrows(AccountNotFoundException.class, () -> accountService.validatePin(987654321L, (short) 4321));
    }

    @Test
    void getBalanceForAccountMaxWithdrawalTest(){
        Account account = new Account();
        account.setBalance(new BigDecimal(800));
        account.setOverdraft(new BigDecimal(200));
        account.setPin((short) 1234);

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository);
        when(accountRepository.findById(123456789L)).thenReturn(Optional.of(account));

        BalanceResponse balanceResponse = accountService.getBalanceForAccount(123456789L);
        assertEquals(balanceResponse.getMaxWithdrawal(), BigDecimal.valueOf(1000));

        account.setOverdraft(BigDecimal.valueOf(0));
        when(accountRepository.findById(123456789L)).thenReturn(Optional.of(account));
        balanceResponse = accountService.getBalanceForAccount(123456789L);
        assertEquals(balanceResponse.getMaxWithdrawal(), BigDecimal.valueOf(800));
    }


}