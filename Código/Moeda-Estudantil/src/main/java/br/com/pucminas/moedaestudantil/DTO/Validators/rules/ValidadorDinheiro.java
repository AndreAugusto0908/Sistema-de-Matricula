package br.com.pucminas.moedaestudantil.DTO.Validators.rules;


import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Dinheiro;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorDinheiro implements ConstraintValidator<Dinheiro, Double> {

    @Override
    public void initialize(Dinheiro constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(Double valor, ConstraintValidatorContext context) {
        if (valor == null) {
            return false;
        }

        return valor > 0 && valor <= 99999;
    }

}
