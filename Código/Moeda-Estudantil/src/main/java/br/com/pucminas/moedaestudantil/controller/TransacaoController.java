package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.CriarTransacaoDTO;
import br.com.pucminas.moedaestudantil.DTO.ExtratoDTO;
import br.com.pucminas.moedaestudantil.Infra.Security.SecurityConfigurations;
import br.com.pucminas.moedaestudantil.model.*;
import br.com.pucminas.moedaestudantil.repository.*;
import br.com.pucminas.moedaestudantil.service.TransacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/transacao")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class TransacaoController {

    private final TransacaoService SERVICE;

    public TransacaoController(TransacaoService SERVICE) {
        this.SERVICE = SERVICE;
    }

    @PostMapping
    public ResponseEntity<?> realizarTransacao(@RequestBody CriarTransacaoDTO dto){
        return SERVICE.realizarTransacao(dto);
    }

    @GetMapping(value = "/extrato")
    public ResponseEntity<?> obterExtrato(@RequestParam("documento") String documento) {
        try {
            ExtratoDTO dto = SERVICE.obterExtrato(documento);
            return ResponseEntity.ok(dto);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
