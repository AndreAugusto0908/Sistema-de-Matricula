package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Dinheiro;
import br.com.pucminas.moedaestudantil.model.Empresa;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VantagemDTO {
    protected Long id;
    @Dinheiro
    private Double valorMoedas;
    @NotBlank(message = "Descrição não pode estar vazia")
    private String descricao;
    @NotBlank(message = "Foto não pode estar vazia")
    private String foto;
    @NotNull(message = "Empresa não pode estar vazia")
    private Long empresa;
}
