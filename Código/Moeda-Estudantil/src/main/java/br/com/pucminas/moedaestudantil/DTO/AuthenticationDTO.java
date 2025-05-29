package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CPFouCNPJ;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Senha;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Credenciais de login")
public record AuthenticationDTO(
        @Schema(description = "Documento do usuário", example = "12345678900")
                @CPFouCNPJ
                @NotBlank(message = "O documento é obrigatório.")
        String documento,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 20, message = "A senha deve ter entre {min} e {max} caracteres.")
        @Senha
        String senha
) {
}
