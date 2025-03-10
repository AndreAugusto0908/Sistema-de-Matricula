package br.com.pucminas.matriculador2000.DTO;

import br.com.pucminas.matriculador2000.Models.PerfilEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private PerfilEnum perfil;
}
