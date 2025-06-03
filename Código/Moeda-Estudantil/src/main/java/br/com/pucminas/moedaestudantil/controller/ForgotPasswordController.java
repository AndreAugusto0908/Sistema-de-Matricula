package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.RequestAlterarSenha;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Email;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.exceptions.handlers.EmailInvalidoException;
import br.com.pucminas.moedaestudantil.service.ForgotPasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esqueceuSenha")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/enviarEmail/{email}")
    public ResponseEntity<?> enviarEmail(@PathVariable String email){
        try {
            String message = forgotPasswordService.verificarEmail(email);
            return ResponseEntity.ok(message);
        } catch (EmailInvalidoException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse(e.getMessage(), "erro"));
        }
    }

    @PostMapping("/verificarOtp/{otp}/{email}")
    public ResponseEntity<?> verificarOtp(@PathVariable Integer otp, @PathVariable String email){
        return forgotPasswordService.verificandoOtp(otp, email);
    }

    @PostMapping("/alterarsenha/{email}")
    public ResponseEntity<?> alterarsenha(
            @RequestBody @Valid RequestAlterarSenha alterarSenha,
            @PathVariable String email){
        return forgotPasswordService.alterarSenha(email, alterarSenha);
    }


}
