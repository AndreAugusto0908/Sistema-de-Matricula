package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Conta;
import br.com.pucminas.moedaestudantil.model.Professor;
import br.com.pucminas.moedaestudantil.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> getByOrigemOrDestino(Conta origem, Conta destino);
    
    @Query("SELECT t FROM Transacao t WHERE t.origem = :conta")
    List<Transacao> findByOrigem_UsuarioConta(@Param("conta") Conta conta);
}
