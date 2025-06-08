package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.HistoricoExtratoDTO;
import br.com.pucminas.moedaestudantil.DTO.RequestResgatarVantagem;
import br.com.pucminas.moedaestudantil.DTO.responses.ResponseAlunoVantagem;
import br.com.pucminas.moedaestudantil.DTO.responses.ResponseVantagens;
import br.com.pucminas.moedaestudantil.Infra.Security.SecurityConfigurations;
import br.com.pucminas.moedaestudantil.repository.VantagemAlunoRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.service.HistoricoService;
import br.com.pucminas.moedaestudantil.service.VantagemAlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/vantagem-aluno")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class VantagemAlunoController {

    private final VantagemAlunoRepository vantagemAlunoRepository;
    private final VantagemAlunoService vantagemAlunoService;

    private final HistoricoService historicoService;
    public VantagemAlunoController(VantagemAlunoRepository vantagemAlunoRepository, VantagemAlunoService vantagemAlunoService,HistoricoService historicoService) {
        this.vantagemAlunoRepository = vantagemAlunoRepository;
        this.vantagemAlunoService = vantagemAlunoService;
        this.historicoService = historicoService;
    }

    @PostMapping("/resgatar")
    @Operation(
            summary = "Resgatar uma vantagem",
            description = "Permite que um aluno resgate uma vantagem específica, debitando o valor correspondente do seu saldo e registrando o resgate."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vantagem resgatada com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno ao tentar resgatar a vantagem",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponse.class)))
    })
    public ResponseEntity<?> resgatarVantagem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para o resgate da vantagem",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RequestResgatarVantagem.class))
            )
            @RequestBody @Valid RequestResgatarVantagem vantagemAluno) {
        try {
            vantagemAlunoService.resgatarVantagem(vantagemAluno);
            return ResponseEntity.ok().body("Sucesso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponse(e.getMessage(), "erro"));
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

    @GetMapping("/obterPorAluno")
    @Operation(summary = "Obter vantagens resgatadas por um aluno",
            description = "Retorna uma lista de vantagens associadas ao aluno com o ID fornecido. Caso o aluno não exista ou não tenha vantagens, retorna um erro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vantagens encontradas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAlunoVantagem.class))),
            @ApiResponse(responseCode = "400", description = "Aluno não encontrado ou sem vantagens",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponse.class)))
    })
    public ResponseEntity<?> obterResgate(
            @Parameter(description = "ID do aluno a ser consultado", required = true, example = "1")
            @RequestParam("id") Long id) {
        List<ResponseAlunoVantagem> vantagensAluno = vantagemAlunoService.getVantagemPorAluno(id);
        if (vantagensAluno.isEmpty()) {
            return ResponseEntity.badRequest().body(new GenericResponse("Resgate não encontrado", "erro"));
        }
        return ResponseEntity.ok(vantagensAluno);
    }

    @GetMapping("/obterVantagensDiponiveisPorAluno")
    public ResponseEntity<List<ResponseVantagens>> vantagensDisponiveisPorAluno(@RequestParam Long id){
        List<ResponseVantagens> vantagensDisponiveis = vantagemAlunoService.vantagensDisponiveisPorAluno(id);
        return ResponseEntity.ok(vantagensDisponiveis);
    }
  
    @GetMapping("/empresa")
    public ResponseEntity<List<HistoricoExtratoDTO>> buscarHistoricoPorEmpresa(
            @RequestParam Long idEmpresa
    ) {
        List<HistoricoExtratoDTO> historico = historicoService.buscarHistoricoPorNomeEmpresa(idEmpresa);
        return ResponseEntity.ok(historico);
    }
}
