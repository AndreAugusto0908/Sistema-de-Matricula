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
public class EnviarMoedasDTO {
    @CPFouCNPJ
    private String documentoRecebedor;
    @Dinheiro
    private Double valor;
    @NotBlank(message = "Observação não pode ser em Branco")
    private String observacao;
}
