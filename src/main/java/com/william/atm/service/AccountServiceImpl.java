package com.william.atm.service;

import com.william.atm.domain.Account;
import com.william.atm.exception.AccountNotFoundException;
import com.william.atm.exception.InvalidPinException;
import com.william.atm.repository.AccountRepository;
import com.william.atm.response.BalanceResponse;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void validatePin(long accountId, short pin){
        Account account = getAccount(accountId);
        if(account.getPin() != pin)
            throw new InvalidPinException("Incorrect pin specified");
    }

    /**
     * @inheritDoc
     */
    @Override
    public Account getAccount(long accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
    }

    /**
     * @inheritDoc
     */
    @Override
    public BalanceResponse getBalanceForAccount(long accountId){
        Account account = getAccount(accountId);
        BalanceResponse balanceResponse = new BalanceResponse(account);
        balanceResponse.setMaxWithdrawal(account.getBalance().add(account.getOverdraft()));
        //TODO: maxWithdrawal should reflect the notes the ATM has
        return balanceResponse;
    }

    /**
     * @inheritDoc
     */
    public void save(Account account){
        accountRepository.save(account);
    }

}
