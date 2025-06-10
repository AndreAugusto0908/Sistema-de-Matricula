package br.com.pucminas.moedaestudantil.model;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Dinheiro;
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
    @Dinheiro
    private Double valorMoedas;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "foto")
    private String foto;
    @ManyToOne
    private Empresa empresa;


}
