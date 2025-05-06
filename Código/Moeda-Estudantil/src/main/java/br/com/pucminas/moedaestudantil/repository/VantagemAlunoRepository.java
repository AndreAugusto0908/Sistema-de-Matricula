package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VantagemAlunoRepository extends JpaRepository<VantagemAluno, Long> {
    List<VantagemAluno> getByAluno(Aluno aluno);
}
