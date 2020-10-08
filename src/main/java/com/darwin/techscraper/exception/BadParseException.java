package com.darwin.techscraper.exception;

import java.text.ParseException;

public class BadParseException extends RuntimeException {
    public BadParseException(String msg) {
        super(msg);
    }
}
