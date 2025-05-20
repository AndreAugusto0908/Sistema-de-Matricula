package br.com.pucminas.moedaestudantil.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarTransacaoDTO {
    private double valor;
    private String documentoRecebedor;
    private String documentoOrigem;
    private String observacao;
}
