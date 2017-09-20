package com.alelk.bcpt.restapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for Validation and Exception Handling
 *
 * Created by Alex Elkin on 19.09.2017.
 */
@ControllerAdvice
public class BcptResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BcptResponseEntityExceptionHandler.class);

    private ApplicationContext context;

    @Autowired
    public BcptResponseEntityExceptionHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        FieldError e = (FieldError) error;
                        return new FieldError(e.getObjectName(), e.getField(), e.getRejectedValue(), e.isBindingFailure(), null, null,
                                context.getMessage(e.getCode(), new String[]{"" + e.getRejectedValue()}, request.getLocale()));
                    }
                    return new ObjectError(error.getObjectName(), context.getMessage(error.getCode(), error.getArguments(), request.getLocale()));
                }).collect(Collectors.toList());
        return new ResponseEntity<>(errorList, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
