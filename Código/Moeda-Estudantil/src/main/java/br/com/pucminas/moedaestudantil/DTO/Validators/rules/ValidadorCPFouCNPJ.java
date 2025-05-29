package br.com.pucminas.moedaestudantil.DTO.Validators.rules;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CPFouCNPJ;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorCPFouCNPJ implements ConstraintValidator<CPFouCNPJ, String> {

    @Override
    public void initialize(CPFouCNPJ constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        String documento = valor.replaceAll("[^\\d]", "");

        if (documento.length() == 11) {
            return isValidCPF(documento);
        } else if (documento.length() == 14) {
            return isValidCNPJ(documento);
        }

        return false;
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false;

        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum1 += digit * (10 - i);
            sum2 += digit * (11 - i);
        }

        int d1 = 11 - (sum1 % 11);
        d1 = (d1 > 9) ? 0 : d1;

        sum2 += d1 * 2;
        int d2 = 11 - (sum2 % 11);
        d2 = (d2 > 9) ? 0 : d2;

        return d1 == Character.getNumericValue(cpf.charAt(9)) &&
                d2 == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.chars().distinct().count() == 1) return false;

        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum1 = 0, sum2 = 0;

        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum1 += digit * peso1[i];
            sum2 += digit * peso2[i];
        }

        int d1 = 11 - (sum1 % 11);
        d1 = (d1 > 9) ? 0 : d1;

        sum2 += d1 * peso2[12];
        int d2 = 11 - (sum2 % 11);
        d2 = (d2 > 9) ? 0 : d2;

        return d1 == Character.getNumericValue(cnpj.charAt(12)) &&
                d2 == Character.getNumericValue(cnpj.charAt(13));
    }
}
