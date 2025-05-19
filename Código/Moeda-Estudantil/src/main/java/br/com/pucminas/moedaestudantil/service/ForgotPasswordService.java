package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.MailBodyDTO;
import br.com.pucminas.moedaestudantil.model.ForgotPassword;
import br.com.pucminas.moedaestudantil.model.UsuarioConta;
import br.com.pucminas.moedaestudantil.repository.ForgotPasswordRepository;
import br.com.pucminas.moedaestudantil.repository.UsuarioContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class ForgotPasswordService {

    @Autowired
    private UsuarioContaRepository usuarioContaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    public Integer otpGenerator() {
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }

    public String verificarEmail(String email) {
        UsuarioConta usuario = usuarioContaRepository.findByEmail(email);


        int otp = otpGenerator();
        MailBodyDTO mailBody = MailBodyDTO.builder()
                .to(email)
                .text("Olá!\n\nSeu código de verificação é: " + otp + "\n\nUse este código para completar seu processo. Caso não tenha solicitado, ignore este e-mail.")
                .subject("Seu código de verificação" + otp)
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(usuario)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);

        return "Email enviado para Verificação";
    }

}