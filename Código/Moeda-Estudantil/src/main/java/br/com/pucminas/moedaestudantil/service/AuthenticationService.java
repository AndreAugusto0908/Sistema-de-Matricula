package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.repository.UsuarioContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UsuarioContaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        return repository.findUserByDocumento(documento);
    }

}
