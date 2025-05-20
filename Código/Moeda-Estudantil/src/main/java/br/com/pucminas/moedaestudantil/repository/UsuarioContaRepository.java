package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.UsuarioConta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioContaRepository extends JpaRepository<UsuarioConta, Long> {

    UserDetails findUserByDocumento(String documento);
    Optional<UsuarioConta> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update UsuarioConta u set u.senha = ?2 where u.email = ?1")
    void alterarSenha(String email, String senha);


}
