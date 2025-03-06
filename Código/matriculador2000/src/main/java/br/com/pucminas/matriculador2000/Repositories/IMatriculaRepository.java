package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMatriculaRepository extends JpaRepository<Matricula, Long> {
}
