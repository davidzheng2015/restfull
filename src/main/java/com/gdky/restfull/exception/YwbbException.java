package com.gdky.restfull.exception;

public class YwbbException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public YwbbException(String message) {
        this.message = message;
    }	

    public String getMessage() {
        return this.message;
    }

}
