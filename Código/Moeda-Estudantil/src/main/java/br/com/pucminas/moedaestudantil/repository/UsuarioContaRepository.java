package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.UsuarioConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioContaRepository extends JpaRepository<UsuarioConta, Long> {

    UserDetails findUserByDocumento(String documento);
    UsuarioConta findByEmail(String email);

}
