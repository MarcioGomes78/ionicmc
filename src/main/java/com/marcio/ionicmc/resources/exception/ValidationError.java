package com.marcio.ionicmc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    //construtor parametrizado
    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }
    
    //getters and setters
    public List<FieldMessage> getErrors() {
        return errors;
    }   
    public void addError(String fieldName, String msg) {
        errors.add(new FieldMessage(fieldName, msg));
    }
}
