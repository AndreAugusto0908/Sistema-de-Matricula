package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Conta;
import br.com.pucminas.moedaestudantil.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> getByOrigemOrDestino(Conta origem, Conta destino);

    @Query(value = "SELECT tr FROM Transacao tr JOIN UsuarioConta us ON (us.conta = tr.origem and us.documento =:doc)")
    List<Transacao> getByContaOrigemDocument(@Param(value = "doc") String documento);

    @Query(value = "SELECT tr FROM Transacao tr JOIN UsuarioConta us ON (us.conta = tr.destino and us.documento =:doc)")
    List<Transacao> getByContaDestinoDocument(@Param(value = "doc") String documento);
}
