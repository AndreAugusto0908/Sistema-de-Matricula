package br.com.pucminas.moedaestudantil.DTO;

import jakarta.validation.constraints.NotBlank;

public record RequestResgatarVantagem(
        @NotBlank(message = "ID do ALuno não pode ser nulo")
        Long idAluno,

        @NotBlank(message = "ID da Vantagem não pode ser nulo")
        Long idVantagem
) {
}
