package br.com.pucminas.moedaestudantil.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponseDTO {
    private Long id;
    private LocalDate data;
    private Double quantidadeMoedas;
    private String nomeOrigem;
    private String nomeDestino;
    private String mensagem;
} 