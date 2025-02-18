package br.com.pucminas.matriculador2.Service;

import br.com.pucminas.matriculador2.Models.Aluno;
import br.com.pucminas.matriculador2.Repositorie.AlunoRepositorie;

public class UserService {
    private AlunoRepositorie alunoRepositorie;

    UserService(AlunoRepositorie alunoRepositorie) {
        this.alunoRepositorie = alunoRepositorie;
    }

    public String loginAluno(String login, String senha) {
        Aluno a = this.alunoRepositorie.findAlunoByEmailAndSenha(login, senha);
        if(a != null) {

        }
    }
}
