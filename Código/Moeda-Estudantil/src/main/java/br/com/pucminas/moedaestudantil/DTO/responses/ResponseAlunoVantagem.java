package br.com.pucminas.moedaestudantil.DTO.responses;

public record ResponseAlunoVantagem(
        String nomeEmpresa,
        String descricao,
        Double valor,
        String nomeAluno,
        String cupom
) {
}
