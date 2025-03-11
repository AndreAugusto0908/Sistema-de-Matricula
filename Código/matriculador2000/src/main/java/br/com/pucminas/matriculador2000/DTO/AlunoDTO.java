package br.com.pucminas.matriculador2000.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlunoDTO extends UsuarioDTO{
    private Long idMatricula;
}
