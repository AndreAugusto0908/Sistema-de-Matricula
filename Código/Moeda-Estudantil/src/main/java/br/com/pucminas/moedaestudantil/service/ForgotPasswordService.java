package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.MailBodyDTO;
import br.com.pucminas.moedaestudantil.DTO.RequestAlterarSenha;
import br.com.pucminas.moedaestudantil.model.ForgotPassword;
import br.com.pucminas.moedaestudantil.model.UsuarioConta;
import br.com.pucminas.moedaestudantil.repository.ForgotPasswordRepository;
import br.com.pucminas.moedaestudantil.repository.UsuarioContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class ForgotPasswordService {

    @Autowired
    private UsuarioContaRepository usuarioContaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Integer otpGenerator() {
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }

    public String verificarEmail(String email) {
        UsuarioConta usuario = usuarioContaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Endereço de E-mail invalido"));


        int otp = otpGenerator();
        MailBodyDTO mailBody = new MailBodyDTO(
            email,
            "Seu código de verificação" + otp,
            "Olá!\n\nSeu código de verificação é: " + otp + "\n\nUse este código para completar seu processo. Caso não tenha solicitado, ignore este e-mail."
        );

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(usuario)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);

        return "Email enviado para Verificação";
    }

    public ResponseEntity<?> verificandoOtp(Integer otp, String email) {
        UsuarioConta usuario = usuarioContaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Endereço de E-mail invalido"));;

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, usuario)
                .orElseThrow(() -> new RuntimeException("Codigo de Email Invalido: " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("Codigo Invalido", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("Codigo de Email Valido");

    }

    public ResponseEntity<?> alterarSenha(String email, RequestAlterarSenha alterarSenha) {
        if (!alterarSenha.senha().equals(alterarSenha.confirmarSenha())) {
            return new ResponseEntity<>("Por favor insira a senha novamente", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(alterarSenha.senha());

        usuarioContaRepository.alterarSenha(email, encodedPassword);

        return ResponseEntity.ok("Senha alterada com sucesso");
    }

}