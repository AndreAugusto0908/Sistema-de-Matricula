package br.com.pucminas.matriculador2000.Repositories;

import br.com.pucminas.matriculador2000.Models.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISecretariaRepository extends JpaRepository<Secretaria, Long> {
    Secretaria findSecretariaByEmailAndSenha(String email, String senha);

}
