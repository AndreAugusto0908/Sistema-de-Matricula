package br.com.pucminas.moedaestudantil.DTO.Validators.rules;


import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.RG;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorRG implements ConstraintValidator<RG, String>{

    @Override
    public void initialize(RG constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String rg, ConstraintValidatorContext context) {
        if (rg == null || rg.trim().isEmpty()) {
            return true;
        }

        // Remove espaços e converte para maiúsculas
        String cleanedRg = rg.trim().toUpperCase();

        // Verifica se está no formato: 2 letras + 8 dígitos numéricos (sem verificador)
        return cleanedRg.matches("^[A-Z]{2}\\d{8}$");
    }

}

