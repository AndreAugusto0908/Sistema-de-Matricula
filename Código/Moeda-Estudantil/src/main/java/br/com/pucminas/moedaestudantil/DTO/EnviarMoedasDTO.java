package br.com.pucminas.moedaestudantil.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnviarMoedasDTO {
    private String documentoRecebedor;
    private Double valor;
    private String observacao;
}
