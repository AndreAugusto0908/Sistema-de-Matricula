package br.com.pucminas.matriculador2000.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Disciplina")
@AllArgsConstructor
@NoArgsConstructor
public class Disciplina {
    public static final long MIN_ALUNO = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "ativa")
    private boolean ativa;
    @Column
    private boolean opcional;
    @Column
    private int periodo;
    @ManyToOne
    private Curso curso;
}
