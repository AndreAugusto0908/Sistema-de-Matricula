package br.com.pucminas.moedaestudantil.DTO.Validators.interfaces;

import br.com.pucminas.moedaestudantil.DTO.Validators.rules.ValidadorCPFouCNPJ;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorCPFouCNPJ.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFouCNPJ {

    String message() default "CPF ou CNPJ invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
