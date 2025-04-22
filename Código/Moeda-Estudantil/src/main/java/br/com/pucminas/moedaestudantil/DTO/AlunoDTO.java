package br.com.pucminas.moedaestudantil.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private Long id;
    private String nome;
    private String documento;
    private String curso;
    private String senha;
    private String endereco;
    private String email;
    private String rg;
}
