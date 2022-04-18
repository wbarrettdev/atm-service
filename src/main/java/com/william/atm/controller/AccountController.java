package com.william.atm.controller;

import com.william.atm.response.BalanceResponse;
import com.william.atm.response.WithdrawResponse;
import com.william.atm.service.AccountService;
import com.william.atm.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Account Controller.
 *
 * @author William Barrett
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class AccountController{

    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountId}/{pin}/balance")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable("accountId") String accountId,
            @PathVariable("pin") String pin)
    {
        accountService.validatePin(Long.parseLong(accountId), Short.parseShort(pin));

        BalanceResponse balanceResponse = accountService.getBalanceForAccount(Long.parseLong(accountId));
        balanceResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(balanceResponse);
    }

    @GetMapping("/{accountId}/{pin}/withdraw/{amount}")
    public ResponseEntity<WithdrawResponse> withdraw(
            @PathVariable("accountId") String accountId,
            @PathVariable("pin") String pin,
            @PathVariable("amount") long amount )
    {
        accountService.validatePin(Long.parseLong(accountId), Short.parseShort(pin));

        WithdrawResponse withdrawResponse = transactionService.withdraw(Long.parseLong(accountId),amount);
        withdrawResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(withdrawResponse);

    }
}
