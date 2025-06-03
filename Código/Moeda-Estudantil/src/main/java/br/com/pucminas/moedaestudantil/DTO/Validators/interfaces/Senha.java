package br.com.pucminas.moedaestudantil.DTO.Validators.interfaces;

import br.com.pucminas.moedaestudantil.DTO.Validators.rules.ValidadorRG;
import br.com.pucminas.moedaestudantil.DTO.Validators.rules.ValidadorSenha;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorSenha.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Senha {

    String message() default "A senha deve conter letra maiúscula, minúscula, número e caractere especial.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
