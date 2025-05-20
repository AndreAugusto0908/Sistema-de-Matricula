package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.RequestAlterarSenha;
import br.com.pucminas.moedaestudantil.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esqueceuSenha")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/enviarEmail/{email}")
    public ResponseEntity<String> enviarEmail(@PathVariable String email){
        String message = forgotPasswordService.verificarEmail(email);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/verificarOtp/{otp}/{email}")
    public ResponseEntity<?> verificarOtp(@PathVariable Integer otp, @PathVariable String email){
        return forgotPasswordService.verificandoOtp(otp, email);
    }

    @PostMapping("/alterarsenha/{email}")
    public ResponseEntity<?> alterarsenha(
            @RequestBody RequestAlterarSenha alterarSenha,
            @PathVariable String email){
        return forgotPasswordService.alterarSenha(email, alterarSenha);
    }


}
