package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Conta;
import br.com.pucminas.moedaestudantil.model.ProprietarioConta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Conta getContaByProprietario(ProprietarioConta proprietario);
    Conta getContasByProprietario_Id(Long proprietarioId);
}
