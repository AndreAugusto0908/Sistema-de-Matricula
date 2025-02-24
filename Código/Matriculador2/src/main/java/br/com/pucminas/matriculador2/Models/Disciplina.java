package br.com.pucminas.matriculador2.Models;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Disciplina {
    @Id
    public Long id;

    private String nome;

    private String codigo;

    private int creditos;

    private String status;
    
    private int maxAlunos = 60;

    private List<Aluno> listaAlunos = new ArrayList<>();

    public void verificarStatus() {
        if (listaAlunos.size() < 3) {
            status = "Cancelada";
        } else {
            status = "Ativa";
        }
    }

    public void adicionarAluno(Aluno aluno) {
        if (listaAlunos.size() < maxAlunos) {
            listaAlunos.add(aluno);
        } else {
            System.out.println("Número máximo de alunos atingido.");
        }
    }

    public void removerAluno(Aluno aluno) {
        listaAlunos.remove(aluno);
    }

    public List<Aluno> getListaAlunos(){
        return listaAlunos;
    }
}

