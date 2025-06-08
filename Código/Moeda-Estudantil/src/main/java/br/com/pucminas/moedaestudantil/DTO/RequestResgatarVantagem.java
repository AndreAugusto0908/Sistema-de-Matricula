package br.com.pucminas.moedaestudantil.DTO;


import jakarta.validation.constraints.NotNull;

public record RequestResgatarVantagem(
        @NotNull(message = "ID do ALuno não pode ser nulo")
        Long idAluno,

        @NotNull(message = "ID da Vantagem não pode ser nulo")
        Long idVantagem
) {
}
