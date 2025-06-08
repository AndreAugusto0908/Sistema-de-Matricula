package br.com.pucminas.moedaestudantil.DTO.Mappers;

import br.com.pucminas.moedaestudantil.DTO.responses.ResponseVantagens;
import br.com.pucminas.moedaestudantil.model.Vantagem;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseVantagensMapper {

    public static ResponseVantagens toResponse(Vantagem vantagem) {
        return new ResponseVantagens(
                vantagem.getId(),
                String.format("%.2f", vantagem.getValorMoedas()),
                vantagem.getDescricao(),
                vantagem.getEmpresa() != null ? vantagem.getEmpresa().getNome() : null
        );
    }

    public static List<ResponseVantagens> toResponseList(List<Vantagem> vantagens) {
        return vantagens.stream()
                .map(ResponseVantagensMapper::toResponse)
                .collect(Collectors.toList());
    }
}
