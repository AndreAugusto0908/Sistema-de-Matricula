package br.com.pucminas.moedaestudantil.DTO.Validators.interfaces;

import br.com.pucminas.moedaestudantil.DTO.Validators.rules.ValidadorDinheiro;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorDinheiro.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dinheiro {

    String message() default "Valor monetário inválido. Deve ser maior que 0 e até 111111.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
