package br.com.pucminas.matriculador2.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Professor {
    @Id
    @Column(unique=true)
    private Integer cpf;
    @Column()
    private String nome;
    @Column()
    private String senha;
}
