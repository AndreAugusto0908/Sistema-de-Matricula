package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Empresa;
import br.com.pucminas.moedaestudantil.model.Vantagem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VantagemRepository extends JpaRepository<Vantagem, Long> {
    List<Vantagem> findByEmpresa(Empresa id);
}
