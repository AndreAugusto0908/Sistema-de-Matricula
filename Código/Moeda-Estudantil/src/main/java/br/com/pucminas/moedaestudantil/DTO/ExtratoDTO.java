package br.com.pucminas.moedaestudantil.DTO;


import br.com.pucminas.moedaestudantil.model.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDTO {
    private double quantidade;
    private String origem;
    private String destino;
    private String data;
}
