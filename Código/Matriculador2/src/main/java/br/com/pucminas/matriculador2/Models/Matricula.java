package br.com.pucminas.matriculador2.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Matricula {
    @Id
    public Integer id;

    private List<Disciplina> disciplinas = new ArrayList<>();

    private Curso curso;


    public void adicionarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public void removerDisciplina(Disciplina disciplina) {
        disciplinas.remove(disciplina);
    }
    
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
}
