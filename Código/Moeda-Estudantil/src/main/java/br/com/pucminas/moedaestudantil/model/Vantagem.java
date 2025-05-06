package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vantagem {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "valor_moedas")
    private Double valorMoedas;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "foto")
    private String foto;
    @ManyToOne
    private Empresa empresa;
}
