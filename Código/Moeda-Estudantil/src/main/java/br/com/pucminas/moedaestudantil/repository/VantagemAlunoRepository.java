package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.model.Transacao;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VantagemAlunoRepository extends JpaRepository<VantagemAluno, Long> {
    List<VantagemAluno> getByAluno(Aluno aluno);

    @Query("SELECT t FROM VantagemAluno t " +
            "WHERE t.vantagem.empresa.id = :id " +
            "AND t.vantagem IS NOT NULL")
    List<VantagemAluno> findByVantagem_Empresa_Id(@Param("id") Long id);
}
