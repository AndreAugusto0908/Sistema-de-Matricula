package br.com.pucminas.moedaestudantil.DTO.Validators.rules;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.RG;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Senha;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorSenha implements ConstraintValidator<Senha, String> {

    @Override
    public void initialize(Senha constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        if (senha == null || senha.trim().isEmpty()) {
            return false;
        }

        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temMinuscula = senha.matches(".*[a-z].*");
        boolean temNumero    = senha.matches(".*\\d.*");
        boolean temEspecial  = senha.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|].*");

        return temMaiuscula && temMinuscula && temNumero && temEspecial;
    }

}
