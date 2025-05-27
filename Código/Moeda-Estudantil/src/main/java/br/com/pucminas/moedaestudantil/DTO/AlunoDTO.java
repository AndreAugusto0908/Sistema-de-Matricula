package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CPF;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private Long id;
    private String nome;
    @CPF
    private String documento;
    private String curso;
    private String senha;
    private String endereco;
    @Email
    private String email;
    private String rg;
}
