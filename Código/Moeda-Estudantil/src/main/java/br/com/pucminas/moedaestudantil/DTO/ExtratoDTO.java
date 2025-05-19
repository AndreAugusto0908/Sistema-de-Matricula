package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.model.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ExtratoDTO {
    private List<Transacao> transacoesDestino;
    private List<Transacao> transacoesOrigem;

    public ExtratoDTO(List<Transacao> transacoesDestino, List<Transacao> transacoesOrigem) {
        this.transacoesDestino = transacoesDestino;
        this.transacoesOrigem = transacoesOrigem;
    }
}
