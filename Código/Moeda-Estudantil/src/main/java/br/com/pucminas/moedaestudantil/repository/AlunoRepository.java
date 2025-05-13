package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> getAlunoByEmail(String email);

    Aluno getAlunoByDocumento(String documento);

    List<Aluno> getAlunoById(Long id);
}
