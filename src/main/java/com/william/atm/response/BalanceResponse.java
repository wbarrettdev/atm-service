package com.william.atm.response;

import com.william.atm.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
final public class BalanceResponse extends BaseResponse{
    private String accountId;
    private BigDecimal balance;
    private BigDecimal maxWithdrawal;

    public BalanceResponse(Account account){
        this.accountId = String.valueOf(account.getId());
        this.balance = account.getBalance();
    }
}
