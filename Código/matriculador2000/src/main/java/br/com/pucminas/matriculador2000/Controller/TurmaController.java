package br.com.pucminas.matriculador2000.Controller;

import br.com.pucminas.matriculador2000.Models.Turma;
import br.com.pucminas.matriculador2000.Repositories.ITurmaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {
    private final ITurmaRepository turmaRepository;

    public TurmaController(ITurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        List<Turma> turma = turmaRepository.findAll();
        return ResponseEntity.ok(turma);
    }

    @GetMapping(value = "/getByName")
    public ResponseEntity<?> getByName(@RequestParam("name") String name) {
        name = name.replaceAll("-", " ");
        List<Turma> turma = turmaRepository.findTurmaByDisciplina_Nome(name);
        return ResponseEntity.ok(turma);
    }
}
