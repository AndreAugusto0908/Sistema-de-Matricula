package br.com.pucminas.matriculador2000.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "Turma")
@AllArgsConstructor
@NoArgsConstructor
public class Turma {

    public static final int MAX_ALUNOS = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    private Disciplina disciplina;
    @ManyToOne
    private Semestre semestre;
    @ManyToOne
    private Professor professor;
    @Column
    private LocalTime horario;
}
