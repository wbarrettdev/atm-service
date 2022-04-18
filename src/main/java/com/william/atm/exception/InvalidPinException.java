package com.william.atm.exception;

/**
 * InvalidPinException used when pin code is invalid
 *
 * @author William Barrett
 */
public class InvalidPinException extends RuntimeException {
    public InvalidPinException(String message){
        super(message);
    }
}
