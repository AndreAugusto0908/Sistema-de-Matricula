package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import br.com.pucminas.moedaestudantil.repository.VantagemAlunoRepository;
import br.com.pucminas.moedaestudantil.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.VantagemAlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vantagem-aluno")
public class VantagemAlunoController {

    private final VantagemAlunoRepository vantagemAlunoRepository;
    private final VantagemAlunoService vantagemAlunoService;

    public VantagemAlunoController(VantagemAlunoRepository vantagemAlunoRepository, VantagemAlunoService vantagemAlunoService) {
        this.vantagemAlunoRepository = vantagemAlunoRepository;
        this.vantagemAlunoService = vantagemAlunoService;
    }

    @PostMapping("/resgatar")
    public ResponseEntity<?> resgatarVantagem(@RequestBody VantagemAluno vantagemAluno) {
        try {
            return ResponseEntity.ok(vantagemAlunoService.resgatarVantagem(vantagemAluno));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao resgatar vantagem", "erro"));
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarResgate(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(vantagemAlunoService.deletarResgate(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao deletar resgate", "erro"));
        }
    }

    @GetMapping("/obter")
    public ResponseEntity<?> obterResgate(@RequestParam("id") Long id) {
        Optional<VantagemAluno> vantagemAluno = vantagemAlunoRepository.findById(id);
        if (vantagemAluno.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse("Resgate não encontrado", "erro"));
        }
        return ResponseEntity.ok(vantagemAluno.get());
    }
}
