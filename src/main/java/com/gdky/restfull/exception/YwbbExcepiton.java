package com.gdky.restfull.exception;

public class YwbbExcepiton extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public YwbbExcepiton(String message) {
        this.message = message;
    }	

    public String getMessage() {
        return this.message;
    }

}
