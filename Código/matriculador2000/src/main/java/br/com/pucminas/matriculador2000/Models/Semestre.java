package br.com.pucminas.matriculador2000.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate inicioPeriodoMatricula;
    @Column
    private LocalDate fimPeriodoMatricula;
    @ManyToOne
    private Curso curso;

}
