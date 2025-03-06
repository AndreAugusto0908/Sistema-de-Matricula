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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @OneToOne
    private Disciplina disciplina;
    @Column
    private LocalDate dataInicio;
    @Column
    private LocalDate dataFim;
    @OneToOne
    private Professor professor;
    @Column
    private LocalTime horario;
    @Column
    private String local;
}
