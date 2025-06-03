package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.CriarVantagemDTO;
import br.com.pucminas.moedaestudantil.Infra.Security.SecurityConfigurations;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.repository.VantagemRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.VantagemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vantagem")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class VantagemController {

    private final VantagemRepository vantagemRepository;
    private final VantagemService vantagemService;

    public VantagemController(VantagemRepository vantagemRepository, VantagemService vantagemService) {
        this.vantagemRepository = vantagemRepository;
        this.vantagemService = vantagemService;
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarVantagem(@RequestBody CriarVantagemDTO vantagem) {
            return ResponseEntity.ok(vantagemService.criarVantagem(vantagem));
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

    @GetMapping("/obter/{id}")
    public ResponseEntity<?> obterVantagem(@RequestParam("id") Long id) {
        Optional<Vantagem> vantagem = vantagemRepository.findById(id);
        if (vantagem.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse("Vantagem não encontrada", "erro"));
        }
        return ResponseEntity.ok(vantagem.get());
    }

    @GetMapping("/obter")
    public ResponseEntity<?> obterTodasVantagens() {
        try {
            return ResponseEntity.ok(vantagemRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao listar vantagens", "erro"));
        }
    }

    @GetMapping("/obterPorEmpresa")
    public ResponseEntity<?> obterPorEmpresaVantagens(@RequestParam(name = "empresa") String empresa) {
        try {
            return ResponseEntity.ok(vantagemRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao listar vantagens", "erro"));
        }
    }

}
