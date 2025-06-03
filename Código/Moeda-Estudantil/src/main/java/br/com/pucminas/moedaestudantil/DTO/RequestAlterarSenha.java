package br.com.pucminas.moedaestudantil.DTO;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Senha;
import jakarta.validation.constraints.NotBlank;

public record RequestAlterarSenha(
        @NotBlank
        @Senha
        String senha,
        @NotBlank
        @Senha
        String confirmarSenha) {
}
