package br.com.pucminas.matriculador2.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    private Matricula matricula;

    public void efetuarMatricula(Disciplina disciplina) {
        List<Aluno> disciplinas = new ArrayList<>();
        disciplinas = disciplina.getListaAlunos();

        if (disciplinas.size() < 4) {
            matricula.adicionarDisciplina(disciplina);
        } else if (disciplinas.size() < 2) {
            matricula.adicionarDisciplina(disciplina);
        } else {
            System.out.println("Número máximo de disciplinas atingido.");
        }
    }

    public void cancelarMatricula(Disciplina disciplina) {
        List<Aluno> disciplinas = new ArrayList<>();
        disciplinas = disciplina.getListaAlunos();
        
        if (disciplinas.contains(disciplina)) {
            matricula.removerDisciplina(disciplina);
        } else if (disciplinas.contains(disciplina)) {
            matricula.removerDisciplina(disciplina);
        }
    }
}

