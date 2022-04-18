package com.william.atm.service;

import com.william.atm.domain.Account;
import com.william.atm.exception.AccountNotFoundException;
import com.william.atm.response.BalanceResponse;

public interface AccountService {

    /**
     * Validate a pin code against an account number
     *
     * @param  accountId  accountId for an account
     * @param  pin  pin code for account
     * @throws AccountNotFoundException if account does not exist
     */
    void validatePin(long accountId, short pin);

    /**
     * Retrieve an Account for a given account ID
     *
     * @param  accountId  accountId for an account
     * @return      The account for a given accountId
     * @throws AccountNotFoundException if account does not exist
     */
    Account getAccount(long accountId);

    /**
     * Retrieve Balance information for a given account ID
     *
     * @param  accountId  accountId for an account
     * @return      Balance details for an account
     * @see         BalanceResponse
     * @throws AccountNotFoundException if account does not exist
     */
    BalanceResponse getBalanceForAccount(long accountId);

    /**
     * Persist an account.
     *
     * @param  account  Account to be persisted
     */
    void save(Account account);
}
