package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProfessorRepository extends JpaRepository<Professor, Long> {

    Professor findProfessorByEmailAndSenha(String email, String senha);
}
