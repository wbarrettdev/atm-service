package com.william.atm.controller;

import com.william.atm.response.BalanceResponse;
import com.william.atm.response.WithdrawResponse;
import com.william.atm.service.AccountServiceImpl;
import com.william.atm.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    private static AccountServiceImpl accountService = Mockito.mock(AccountServiceImpl.class);

    private static TransactionServiceImpl transactionService = Mockito.mock(TransactionServiceImpl.class);

    @Test
    void accountControllerGetBalanceTest() throws Exception {
        BalanceResponse mockBalanceResponse = new BalanceResponse();
        when(accountService.getBalanceForAccount(123456789L)).thenReturn(mockBalanceResponse);
        AccountController controller = new AccountController(accountService,transactionService);
        ResponseEntity<BalanceResponse> response = controller.getBalance("123456789", "1234");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void accountControllerWithdrawTest() {
        WithdrawResponse mockWithDrawResponse = new WithdrawResponse();
        AccountController controller = new AccountController(accountService,transactionService);
        when(transactionService.withdraw(123456789L, 200)).thenReturn(mockWithDrawResponse);
        ResponseEntity<WithdrawResponse> response = controller.withdraw("123456789", "1234", 200);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}