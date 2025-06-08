package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VantagemAlunoRepository extends JpaRepository<VantagemAluno, Long> {
    List<VantagemAluno> getByAluno(Aluno aluno);

    @Query("SELECT va.vantagem FROM VantagemAluno va WHERE va.aluno = :aluno")
    List<Vantagem> findVantagensByAluno(@Param("aluno") Aluno aluno);

}
