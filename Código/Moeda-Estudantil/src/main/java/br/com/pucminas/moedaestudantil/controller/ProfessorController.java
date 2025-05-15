package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.Infra.Security.SecurityConfigurations;
import br.com.pucminas.moedaestudantil.repository.ProfessorRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professor")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class ProfessorController {
    private final ProfessorRepository professorRepository;
    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    @GetMapping(value = "/obter")
    public ResponseEntity<?> obterProfessor(@RequestParam("documento") String documento){
        return ResponseEntity.ok(professorRepository.getProfessorByDocumento(documento));


    }
}
