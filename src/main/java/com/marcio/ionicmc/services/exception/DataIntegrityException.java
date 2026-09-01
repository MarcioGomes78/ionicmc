package com.marcio.ionicmc.services.exception;

public class DataIntegrityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    //so receber a mesnagem
    public DataIntegrityException(String msg) {
        super(msg);
    }

    //receber a mesnagem e a causa (erro)
    public DataIntegrityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

