package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findTurmaByDisciplina_Nome(String disciplinaNome);

    List<Turma> findTurmasById(Long id);
}
