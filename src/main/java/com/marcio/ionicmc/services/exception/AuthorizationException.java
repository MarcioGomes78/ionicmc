package com.marcio.ionicmc.services.exception;

public class AuthorizationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    //so receber a mesnagem
    public AuthorizationException(String msg) {
        super(msg);
    }

    //receber a mesnagem e a causa (erro)
    public AuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

