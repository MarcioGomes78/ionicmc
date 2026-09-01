package com.marcio.ionicmc.resources.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.marcio.ionicmc.services.exception.DataIntegrityException;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice // indica que é um controlador de exceções
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class) // indica que é um método que trata a exceção ObjectNotFoundException
    public ResponseEntity<StandarError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) { // recebe a exceção como parâmetro
        // cria o objeto StandarError
        StandarError err = new StandarError(
                HttpStatus.NOT_FOUND.value(), // código HTTP 404
                e.getMessage(), // mensagem da exceção
                System.currentTimeMillis());// timestamp atual
        
        // retorna o objeto StandarError com o código HTTP 200
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

     @ExceptionHandler(DataIntegrityException.class) // indica que é um método que trata a exceção ObjectNotFoundException
    public ResponseEntity<StandarError> dataIntegrityException(DataIntegrityException e, HttpServletRequest request) { // recebe a exceção como parâmetro
        // cria o objeto StandarError
        StandarError err = new StandarError(
                HttpStatus.BAD_REQUEST.value(), // código HTTP 400
                e.getMessage(), // mensagem da exceção
                System.currentTimeMillis());// timestamp atual
        
        // retorna o objeto StandarError com o código HTTP 200
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
