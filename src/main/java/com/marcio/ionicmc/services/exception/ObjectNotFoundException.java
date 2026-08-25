package com.marcio.ionicmc.services.exception;

public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    //so receber a mesnagem
    public ObjectNotFoundException(String msg) {
        super(msg);
    }

    //receber a mesnagem e a causa (erro)
    public ObjectNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

