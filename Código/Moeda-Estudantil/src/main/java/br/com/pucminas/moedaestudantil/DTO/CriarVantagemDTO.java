package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Dinheiro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarVantagemDTO(
        @NotBlank(message = "Valor não pode ser nulo")
        @Dinheiro
        double valorMoedas,

        @NotBlank(message = "Descrição não pode ser null")
        @Size(min = 1, max = 100)
        String descricao,

        @NotBlank(message = "A vantagem deve ter uma foto")
        String foto,

        @NotBlank(message = "A vantagem deve ter uma empresa responsavel")
        Long idEmpresa
) {
}
