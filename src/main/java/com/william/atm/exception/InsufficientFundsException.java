package com.william.atm.exception;

/**
 * InsufficientFundsException used when Account has insufficient funds
 *
 * @author William Barrett
 */
public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String message){
        super(message);
    }
}
