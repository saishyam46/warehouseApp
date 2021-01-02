package com.it.sero.exception;

public class UomNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public UomNotFoundException() {
		super();
	}
	
	public UomNotFoundException(String message) {
		super(message);
	}
}
