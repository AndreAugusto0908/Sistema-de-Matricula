package br.com.pucminas.matriculador2.Repositorie;

import br.com.pucminas.matriculador2.Models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AlunoRepositorie extends JpaRepository<Aluno, Long> {
    public Aluno findAlunoByEmailAndSenha(String nome, String senha);
}
