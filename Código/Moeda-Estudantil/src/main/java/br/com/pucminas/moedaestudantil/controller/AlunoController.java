package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.AlunoDTO;
import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.repository.AlunoRepository;
import br.com.pucminas.moedaestudantil.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoRepository alunoRepository;
    private final AlunoService service;

    public AlunoController(AlunoRepository alunoRepository, AlunoService service) {
        this.alunoRepository = alunoRepository;
        this.service = service;
    }

    @PostMapping(value = "/registrar")
    public ResponseEntity<?> gerarAluno(@RequestBody AlunoDTO aluno) {
        try {
            GenericResponse response = this.service.criarAluno(aluno);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao criar aluno", "erro"));
        }
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity<?> atualizarAluno(@RequestBody AlunoDTO aluno) {
        try {
            GenericResponse response = this.service.atualizarAluno(aluno);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao atualizar aluno", "erro"));
        }
    }

    @DeleteMapping(value = "/deletar")
    public ResponseEntity<?> deletarAluno(@RequestBody AlunoDTO aluno) {
        try {
            GenericResponse response = this.service.deletarAluno(aluno.getId());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao deletar aluno", "erro"));
        }


    }

    @GetMapping(value = "/obter")
    public ResponseEntity<?> obterAluno(@RequestParam("id") Long id) {
            List<Aluno> aluno = alunoRepository.getAlunoById(id);
            if(aluno.isEmpty()) {
                return ResponseEntity.badRequest().body(new GenericResponse("O id que enviou está incorreto ou não existe", "erro"));
            }
            return ResponseEntity.ok().body(aluno.get(0));

    }
}
