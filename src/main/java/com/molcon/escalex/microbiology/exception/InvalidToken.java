package com.molcon.escalex.microbiology.exception;


public class InvalidToken extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	private final String message;
	
	
	public InvalidToken(String message) {
		super();
		this.message = message;
	}
	
	
	
	@Override
	public String getMessage() {
		return message;
	}
	
	
}
