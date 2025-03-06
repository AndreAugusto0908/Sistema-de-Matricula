package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAlunoRepository extends JpaRepository<Aluno, Long> {
    Aluno findAlunoByEmailAndSenha(String email, String senha);

}
