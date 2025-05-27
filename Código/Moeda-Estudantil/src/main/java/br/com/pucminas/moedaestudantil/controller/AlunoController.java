package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.AlunoDTO;
import br.com.pucminas.moedaestudantil.Infra.Security.SecurityConfigurations;
import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.repository.AlunoRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class AlunoController {

    private final AlunoRepository alunoRepository;
    private final AlunoService service;

    public AlunoController(AlunoRepository alunoRepository, AlunoService service) {
        this.alunoRepository = alunoRepository;
        this.service = service;
    }

    @Operation(summary = "Registrar um novo aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao criar aluno",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PostMapping(value = "/registrar")
    public ResponseEntity<?> gerarAluno(@RequestBody @Valid AlunoDTO aluno) {
        try {
            GenericResponse response = this.service.criarAluno(aluno);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao criar aluno", "erro"));
        }
    }

    @Operation(summary = "Atualizar dados de um aluno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar aluno",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PutMapping(value = "/atualizar")
    public ResponseEntity<?> atualizarAluno(@RequestBody AlunoDTO aluno) {
        try {
            GenericResponse response = this.service.atualizarAluno(aluno);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao atualizar aluno", "erro"));
        }
    }

    @Operation(summary = "Deletar um aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno deletado com sucesso",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar aluno",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @DeleteMapping(value = "/deletar")
    public ResponseEntity<?> deletarAluno(@RequestBody AlunoDTO aluno) {
        try {
            GenericResponse response = this.service.deletarAluno(aluno.getId());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse("Erro ao deletar aluno", "erro"));
        }


    }
    @Operation(summary = "Obter um aluno pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido ou aluno não encontrado",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @GetMapping(value = "/obter")
    public ResponseEntity<?> obterAluno(@RequestParam("id") Long id) {
            List<Aluno> aluno = alunoRepository.getAlunoById(id);
            if(aluno.isEmpty()) {
                return ResponseEntity.badRequest().body(new GenericResponse("O id que enviou está incorreto ou não existe", "erro"));
            }
            return ResponseEntity.ok().body(aluno.get(0));

    }

    @Operation(summary = "Obter todos os alunos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Nenhum aluno encontrado",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @GetMapping()
    public ResponseEntity<?> obterTodosAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();

        if (alunos.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse("Nenhum aluno encontrado", "erro"));
        }

        return ResponseEntity.ok().body(alunos);
    }
}
