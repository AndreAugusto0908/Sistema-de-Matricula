package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.repository.VantagemRepository;
import br.com.pucminas.moedaestudantil.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.VantagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vantagem")
public class VantagemController {

    private final VantagemRepository vantagemRepository;
    private final VantagemService vantagemService;

    public VantagemController(VantagemRepository vantagemRepository, VantagemService vantagemService) {
        this.vantagemRepository = vantagemRepository;
        this.vantagemService = vantagemService;
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarVantagem(@RequestBody Vantagem vantagem) {
        try {
            return ResponseEntity.ok(vantagemService.criarVantagem(vantagem));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao criar vantagem", "erro"));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVantagem(@RequestBody Vantagem vantagem) {
        try {
            return ResponseEntity.ok(vantagemService.atualizarVantagem(vantagem));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao atualizar vantagem", "erro"));
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarVantagem(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(vantagemService.deletarVantagem(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao deletar vantagem", "erro"));
        }
    }

    @GetMapping("/obter")
    public ResponseEntity<?> obterVantagem(@RequestParam("id") Long id) {
        Optional<Vantagem> vantagem = vantagemRepository.findById(id);
        if (vantagem.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse("Vantagem não encontrada", "erro"));
        }
        return ResponseEntity.ok(vantagem.get());
    }
}
