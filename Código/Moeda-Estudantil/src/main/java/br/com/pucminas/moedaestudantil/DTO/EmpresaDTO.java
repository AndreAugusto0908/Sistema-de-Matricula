package br.com.pucminas.moedaestudantil.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmpresaDTO {
    private Long id;
    private String nome;
    private String documento;
}
