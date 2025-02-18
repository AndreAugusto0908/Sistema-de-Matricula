package br.com.pucminas.matriculador2.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Disciplina {
    @Id
    public Long id;
}
