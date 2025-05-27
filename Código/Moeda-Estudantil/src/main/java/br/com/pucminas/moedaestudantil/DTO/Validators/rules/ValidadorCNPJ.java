package br.com.pucminas.moedaestudantil.DTO.Validators.rules;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CNPJ;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorCNPJ implements ConstraintValidator<CNPJ,String>  {
    @Override
    public void initialize(CNPJ constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
