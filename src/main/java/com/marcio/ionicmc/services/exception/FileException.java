package com.marcio.ionicmc.services.exception;

public class FileException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    //so receber a mesnagem
    public FileException(String msg) {
        super(msg);
    }

    //receber a mesnagem e a causa (erro)
    public FileException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

