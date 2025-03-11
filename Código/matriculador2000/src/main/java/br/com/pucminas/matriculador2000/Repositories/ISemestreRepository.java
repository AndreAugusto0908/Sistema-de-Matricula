package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISemestreRepository extends JpaRepository<Semestre, Long> {
}
