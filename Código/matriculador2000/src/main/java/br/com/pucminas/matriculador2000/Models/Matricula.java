package br.com.pucminas.matriculador2000.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Data
@Table(name = "Matricula")
@AllArgsConstructor
@NoArgsConstructor
public class Matricula {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long numeroMatricula;
    @ManyToOne
    private Semestre semestre;
    @Column
    private double valor;
    @ManyToOne
    private Aluno aluno;
}
