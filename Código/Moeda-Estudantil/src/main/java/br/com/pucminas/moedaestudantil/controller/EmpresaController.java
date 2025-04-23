package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.EmpresaDTO;
import br.com.pucminas.moedaestudantil.model.Empresa;
import br.com.pucminas.moedaestudantil.repository.EmpresaRepository;
import br.com.pucminas.moedaestudantil.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.EmpresaService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaRepository empresaRepository;
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaRepository empresaRepository, EmpresaService empresaService) {
        this.empresaRepository = empresaRepository;
        this.empresaService = empresaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEmpresa(@RequestBody EmpresaDTO empresa) {
        try {
            return ResponseEntity.ok(empresaService.criarEmpresa(empresa));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao registrar empresa", "erro"));
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa empresa) {
        try {
            return ResponseEntity.ok(empresaService.atualizarEmpresa(empresa));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao atualizar empresa", "erro"));
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarEmpresa(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(empresaService.deletarEmpresa(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao deletar empresa", "erro"));
        }
    }

    @GetMapping("/obter")
    public ResponseEntity<?> obterEmpresa(@RequestParam("id") Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse("Empresa não encontrada", "erro"));
        }
        return ResponseEntity.ok(empresa.get());
    }

    @GetMapping()
    public ResponseEntity<?> listarEmpresas() {
        try {
            return ResponseEntity.ok(empresaRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao listar empresas", "erro"));
        }
    }
}
