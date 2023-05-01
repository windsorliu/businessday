package com.windsor.businessday.exception;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String str) {
        super("Currency not found : " + str);
    }

}
