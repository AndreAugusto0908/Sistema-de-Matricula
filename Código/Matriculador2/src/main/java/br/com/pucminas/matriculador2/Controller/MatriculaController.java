package br.com.pucminas.matriculador2.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class MatriculaController {
    @PutMapping("/cancelar")
    public ResponseEntity<?> cancelarMatricula(@RequestParam("matricula") String matricula) {
       return ResponseEntity.ok().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarMatricula(@RequestParam("idAluno") String idAluno) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> alterarMatricula(@RequestParam("matricula") String matricula, @RequestParam("idDisciplina") String idDisciplina) {
        return ResponseEntity.ok().build();
    }
}
