package com.gdky.restfull.exception;

import org.springframework.validation.BindingResult;

public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private BindingResult errors;
    
    private String errorMessages;

    public InvalidRequestException(BindingResult errors) {
        this.errors = errors;
    }
    public InvalidRequestException(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public BindingResult getErrors() {
        return this.errors;
    }

	public String getErrorMessages() {
		return errorMessages;
	}

    
}