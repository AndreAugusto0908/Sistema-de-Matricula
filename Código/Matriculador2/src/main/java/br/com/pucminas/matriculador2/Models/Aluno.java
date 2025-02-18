package br.com.pucminas.matriculador2.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {
    @Id
    @Column(name = "cpf")
    private Integer cpf;
    @Column(name = "nome")
    private String nome;
    @Column(name = "senha")
    private String senha;
    @Column
    private String email;
}
