package br.com.pucminas.matriculador2000.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Entity
@Data
public class Secretaria extends Usuario {


    public Secretaria() {}


}
