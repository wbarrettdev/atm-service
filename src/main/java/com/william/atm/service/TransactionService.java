package com.william.atm.service;

import com.william.atm.response.WithdrawResponse;

public interface TransactionService {
    /**
     * Withdraw money given an account ID and an ammount to withdraw
     *
     * @param accountId The account to withraw funds from
     * @param amount The amount to withdraw
     */
    WithdrawResponse withdraw(long accountId, long amount);
}
