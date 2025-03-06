package br.com.pucminas.matriculador2000.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aluno")
@Data
@NoArgsConstructor
public class Aluno extends Usuario {
    @OneToOne
    private Matricula matricula;
}
