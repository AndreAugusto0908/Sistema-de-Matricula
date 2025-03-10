package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Matricula;
import br.com.pucminas.matriculador2000.Models.Turma;
import br.com.pucminas.matriculador2000.Models.Turma_Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITurmaMatriculaRepository extends JpaRepository<Turma_Matricula, Long> {

    List<Turma_Matricula> findTurma_MatriculasByMatriculaAndTurma(Matricula matricula, Turma turma);

    List<Turma_Matricula> findTurma_MatriculasByMatricula(Matricula matricula);

    List<Turma_Matricula> findTurma_MatriculasByTurma(Turma turma);
}
