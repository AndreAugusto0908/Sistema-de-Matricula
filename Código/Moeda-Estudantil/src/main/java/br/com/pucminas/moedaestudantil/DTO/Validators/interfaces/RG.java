package br.com.pucminas.moedaestudantil.DTO.Validators.interfaces;

import br.com.pucminas.moedaestudantil.DTO.Validators.rules.ValidadorRG;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorRG.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface RG {

    String message() default "RG invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
