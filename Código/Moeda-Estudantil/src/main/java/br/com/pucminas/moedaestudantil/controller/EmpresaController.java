package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.EmpresaDTO;
import br.com.pucminas.moedaestudantil.model.Empresa;
import br.com.pucminas.moedaestudantil.repository.EmpresaRepository;
import br.com.pucminas.moedaestudantil.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.EmpresaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Registrar uma nova empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa registrada com sucesso",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao registrar empresa",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEmpresa(@RequestBody EmpresaDTO empresa) {
        try {
            return ResponseEntity.ok(empresaService.criarEmpresa(empresa));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao registrar empresa", "erro"));
        }
    }

    @Operation(summary = "Atualizar dados de uma empresa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar empresa",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa empresa) {
        try {
            return ResponseEntity.ok(empresaService.atualizarEmpresa(empresa));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao atualizar empresa", "erro"));
        }
    }

    @Operation(summary = "Deletar uma empresa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa deletada com sucesso",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar empresa",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarEmpresa(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(empresaService.deletarEmpresa(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao deletar empresa", "erro"));
        }
    }

    @Operation(summary = "Obter uma empresa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(responseCode = "400", description = "Empresa não encontrada",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @GetMapping("/obter")
    public ResponseEntity<?> obterEmpresa(@RequestParam("id") Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse("Empresa não encontrada", "erro"));
        }
        return ResponseEntity.ok(empresa.get());
    }

    @Operation(summary = "Listar todas as empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empresas recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao listar empresas",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<?> listarEmpresas() {
        try {
            return ResponseEntity.ok(empresaRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao listar empresas", "erro"));
        }
    }
}
