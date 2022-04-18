package com.william.atm.exception;

/**
 * ATMInsufficientFundsException used when ATM has insufficient funds or lacks note composition
 *
 * @author William Barrett
 */
public class ATMInsufficientException extends RuntimeException{
    public ATMInsufficientException(String message){
        super(message);
    }
}
