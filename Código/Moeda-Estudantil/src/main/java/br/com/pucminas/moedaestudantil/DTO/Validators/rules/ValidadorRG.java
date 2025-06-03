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

        String cleanedRg = rg.replaceAll("[^\\dXx]", "");

        if (cleanedRg.length() != 9) {
            return false;
        }

        return isValidRG(cleanedRg);
    }

    /**
     * Valida um RG com base na regra de cálculo do dígito verificador.
     */
    private boolean isValidRG(String rg) {
        try {
            int sum = 0;
            for (int i = 0; i < 8; i++) {
                int digit = Character.getNumericValue(rg.charAt(i));
                sum += digit * (9 - i);
            }

            char checkDigitChar = rg.charAt(8);
            int checkDigit;

            if (checkDigitChar == 'X' || checkDigitChar == 'x') {
                checkDigit = 10;
            } else if (Character.isDigit(checkDigitChar)) {
                checkDigit = Character.getNumericValue(checkDigitChar);
            } else {
                return false;
            }

            int expectedDigit = (sum % 11);
            expectedDigit = expectedDigit > 9 ? 0 : expectedDigit;

            return checkDigit == expectedDigit;
        } catch (Exception e) {
            return false;
        }
    }
}

