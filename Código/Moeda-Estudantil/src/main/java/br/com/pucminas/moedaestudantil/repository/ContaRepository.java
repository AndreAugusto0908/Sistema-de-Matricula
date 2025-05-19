package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Conta;
import br.com.pucminas.moedaestudantil.model.UsuarioConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
