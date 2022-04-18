package com.william.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Account object representing a bank account.
 *
 * @author William Barrett
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
public class Account {

    @Id
    private long id;
    private short pin;
    private BigDecimal balance;
    private BigDecimal overdraft;

    /**
     * Get the max an account can withdraw.
     *
     * @return max withdrawal including overdraft
     */
    public BigDecimal getMaxWithdrawal(){
        return balance.add(overdraft);
    }

    /**
     * Deduct ammount from balance of an account
     *
     * @return true if deduction was successful.
     */
    public boolean subtractAmount(long amount){
        BigDecimal balanceTemp = balance.subtract(BigDecimal.valueOf(amount));
        if(balanceTemp.add(overdraft).compareTo(new BigDecimal(0)) <0)
            return false;
        this.balance = balanceTemp;
        return true;
    }
}
