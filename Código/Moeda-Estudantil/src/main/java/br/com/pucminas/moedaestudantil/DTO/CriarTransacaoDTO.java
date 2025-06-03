package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CPFouCNPJ;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Dinheiro;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarTransacaoDTO {

    @Dinheiro
    private double valor;
    @CPFouCNPJ
    @NotBlank(message = "Documento não pode ser nulo")
    private String documentoRecebedor;
    @CPFouCNPJ
    @NotBlank(message = "Documento não pode ser nulo")
    private String documentoOrigem;
    @NotBlank(message = "Observação não pode ser nulo")
    private String observacao;
}
