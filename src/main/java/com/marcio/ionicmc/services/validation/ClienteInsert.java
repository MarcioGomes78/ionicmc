package com.marcio.ionicmc.services.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ClienteInsertValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteInsert {

    String message() default "Erro de validação";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
