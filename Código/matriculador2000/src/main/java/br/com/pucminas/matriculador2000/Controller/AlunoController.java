package br.com.pucminas.matriculador2000.Controller;

import br.com.pucminas.matriculador2000.DTO.GenericResponse;
import br.com.pucminas.matriculador2000.DTO.RealizarMatriculaDTO;
import br.com.pucminas.matriculador2000.Models.Matricula;
import br.com.pucminas.matriculador2000.Repositories.IAlunoRepository;
import br.com.pucminas.matriculador2000.Services.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/aluno")
@RestController
public class AlunoController {

    private final IAlunoRepository repositorie;
    private final AlunoService service;

    public AlunoController(IAlunoRepository repositorie, AlunoService service) {
        this.repositorie = repositorie;
        this.service = service;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repositorie.findAll());
    }

    @PutMapping(value = "/matricularTurma")
    public ResponseEntity<?> matricularTurma(@RequestBody RealizarMatriculaDTO dto) {
        GenericResponse resposta = new GenericResponse();
        resposta.setMensagem(this.service.realizarMatricula(dto.email, dto.senha, dto.turmaId));
        return ResponseEntity.ok(resposta);
    }

    @PutMapping(value = "/cancelarMatricula")
    public ResponseEntity<?> cancelarMatricula(@RequestBody RealizarMatriculaDTO dto) {
        GenericResponse resposta = new GenericResponse();
        resposta.setMensagem(this.service.cancelarMatricula(dto.email, dto.senha, dto.turmaId));
        return ResponseEntity.ok(resposta);
    }
}
