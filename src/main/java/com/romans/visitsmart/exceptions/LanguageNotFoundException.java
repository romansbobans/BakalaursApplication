package com.romans.visitsmart.exceptions;

/**
 * Created by Romans on 05/05/14.
 */
public class LanguageNotFoundException extends Exception {
    public LanguageNotFoundException() {
        super();
    }

    public LanguageNotFoundException(String detailMessage) {
        super(detailMessage);
    }
}
