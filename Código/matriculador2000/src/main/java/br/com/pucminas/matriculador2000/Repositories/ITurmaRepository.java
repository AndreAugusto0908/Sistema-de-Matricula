package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITurmaRepository extends JpaRepository<Turma, Long> {
}
