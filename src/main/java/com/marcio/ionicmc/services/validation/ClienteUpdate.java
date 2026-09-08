package com.marcio.ionicmc.services.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ClienteUpdateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteUpdate{

    String message() default "Erro de validação";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
