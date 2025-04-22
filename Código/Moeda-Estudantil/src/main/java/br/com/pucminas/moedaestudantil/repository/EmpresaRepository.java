package br.com.pucminas.moedaestudantil.repository;

import br.com.pucminas.moedaestudantil.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
