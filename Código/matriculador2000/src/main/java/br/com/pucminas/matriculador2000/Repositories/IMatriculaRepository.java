package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findMatriculaById(Long id);
}
