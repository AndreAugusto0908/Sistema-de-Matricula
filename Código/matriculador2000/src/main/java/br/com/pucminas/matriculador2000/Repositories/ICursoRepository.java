package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICursoRepository extends JpaRepository<Curso, Long> {
}
