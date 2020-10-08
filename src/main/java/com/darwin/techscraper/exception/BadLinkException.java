package com.darwin.techscraper.exception;

import java.io.IOException;

public class BadLinkException extends IOException {
    public BadLinkException(String msg) {
        super(msg);
    }
}
