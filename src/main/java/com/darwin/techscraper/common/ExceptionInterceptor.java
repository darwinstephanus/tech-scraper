//package com.darwin.techscraper.common;
//
//import com.darwin.techscraper.exception.BadLinkException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class ExceptionInterceptor extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(BadLinkException.class)
//    public final ResponseEntity<Object> handleAllExceptions(BadLinkException ex) {
//        return new ResponseEntity("BAD LINK", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}