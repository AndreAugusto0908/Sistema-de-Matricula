package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer>{
}
