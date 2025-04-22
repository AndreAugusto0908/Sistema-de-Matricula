package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
