package br.com.pucminas.moedaestudantil.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciais de login")
public record AuthenticationDTO(
        @Schema(description = "Documento do usuário", example = "12345678900")
        String documento,

        @Schema(description = "Senha do usuário", example = "123456")
        String senha
) {
}
