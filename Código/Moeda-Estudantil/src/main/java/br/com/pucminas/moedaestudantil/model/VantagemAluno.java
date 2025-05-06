package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VantagemAluno {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @OneToOne
    private Aluno aluno;
    @OneToOne
    private Vantagem vantagem;
}
