package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CPF;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Email;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.RG;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Senha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 255, message = "O nome deve ter entre {min} e {max} caracteres.")
    private String nome;

    @CPF
    private String documento;

    @NotBlank(message = "O curso é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome do curso deve ter entre {min} e {max} caracteres.")
    private String curso;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 20, message = "A senha deve ter entre {min} e {max} caracteres.")
    @Senha
    private String senha;

    @NotBlank(message = "O endereço é obrigatório.")
    @Size(min = 5, max = 255, message = "O endereço deve ter entre {min} e {max} caracteres.")
    private String endereco;

    @Email
    private String email;

    @RG
    private String rg;
}
