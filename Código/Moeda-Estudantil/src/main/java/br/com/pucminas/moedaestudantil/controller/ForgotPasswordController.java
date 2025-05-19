package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.MailBodyDTO;
import br.com.pucminas.moedaestudantil.model.UsuarioConta;
import br.com.pucminas.moedaestudantil.repository.UsuarioContaRepository;
import br.com.pucminas.moedaestudantil.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/esqueceuSenha")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/verificarEmail/{email}")
    public ResponseEntity<String> verificarEmail(@PathVariable String email){
        String message = forgotPasswordService.verificarEmail(email);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/verificarOtp/{otp}/{email}")
    public ResponseEntity<String> verificarOtp(@PathVariable String otp, @PathVariable String email){

    }


}
