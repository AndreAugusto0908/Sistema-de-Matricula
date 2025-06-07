package br.com.pucminas.moedaestudantil.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoExtratoDTO {
    private String nomeAluno;
    private Double valorVantagem;
    private String descricaoVantagem;
}