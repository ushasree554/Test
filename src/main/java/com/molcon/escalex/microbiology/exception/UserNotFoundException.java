package com.molcon.escalex.microbiology.exception;

public class UserNotFoundException  extends RuntimeException {

    public UserNotFoundException(String error) {
        super(error);
    }
	
	
}