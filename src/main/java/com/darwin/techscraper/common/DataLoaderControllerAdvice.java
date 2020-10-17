package com.darwin.techscraper.common;

import com.darwin.techscraper.exception.BadLinkException;
import com.darwin.techscraper.exception.BadParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class DataLoaderControllerAdvice {

    @ExceptionHandler({ BadLinkException.class })
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public CommonResponse handleBadLinkException(BadLinkException ex) {
        return new CommonResponse(400, ex.getMessage());
    }

    @ExceptionHandler({ BadParseException.class })
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public CommonResponse handleBadParseException(BadParseException ex) {
        return new CommonResponse(400, ex.getMessage());
    }
}
