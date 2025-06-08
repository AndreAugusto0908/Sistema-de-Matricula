package br.com.pucminas.moedaestudantil.DTO.responses;

public record ResponseVantagens(
        Long idVantagem,
        String valorVantagem,
        String descricaoVantagem,
        String nomeEmpresaVantagem
) {
}
