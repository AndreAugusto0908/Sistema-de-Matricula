package br.com.pucminas.matriculador2.Models;

import java.util.ArrayList;
import java.util.List;

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

    private String matricula;
    
    private List<Disciplina> listaDisciplinas = new ArrayList<>();

    public void acessarListaAlunos(Disciplina disciplina) {
        for (Aluno aluno : disciplina.getListaAlunos()) {
            System.out.println(aluno.getNome());
        }
    }
}
