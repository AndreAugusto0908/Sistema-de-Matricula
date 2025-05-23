package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.ForgotPassword;
import br.com.pucminas.moedaestudantil.model.UsuarioConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer>{

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and  fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, UsuarioConta user);
}
