package com.molcon.escalex.microbiology.exception;

public class TokenNotFoundException  extends RuntimeException {

    public TokenNotFoundException(String error) {
        super(error);
    }

}