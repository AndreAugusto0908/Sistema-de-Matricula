package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.EnviarMoedasDTO;
import br.com.pucminas.moedaestudantil.DTO.TransacaoResponseDTO;
import br.com.pucminas.moedaestudantil.Infra.Security.SecurityConfigurations;
import br.com.pucminas.moedaestudantil.service.ProfessorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping("/{documentoProfessor}/enviar-moedas")
    public ResponseEntity<?> enviarMoedas(
            @PathVariable String documentoProfessor,
            @RequestBody EnviarMoedasDTO dto) {
        try {
            TransacaoResponseDTO transacao = professorService.enviarMoedas(documentoProfessor, dto);
            return ResponseEntity.ok(transacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{documentoProfessor}/transacoes")
    public ResponseEntity<?> listarTransacoes(@PathVariable String documentoProfessor) {
        try {
            List<TransacaoResponseDTO> transacoes = professorService.listarTransacoes(documentoProfessor);
            return ResponseEntity.ok(transacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{documentoProfessor}/saldo")
    public ResponseEntity<?> getSaldoProfessor(@PathVariable String documentoProfessor) {
        try {
            Double saldo = professorService.getSaldoProfessor(documentoProfessor);
            return ResponseEntity.ok(saldo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
