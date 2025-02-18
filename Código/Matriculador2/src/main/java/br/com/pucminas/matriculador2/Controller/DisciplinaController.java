package br.com.pucminas.matriculador2.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {
    @GetMapping("/alunos")
    public ResponseEntity<?> getAlunos(@RequestParam("idDisciplina") String id) {
        return ResponseEntity.ok().build();
    }
}
