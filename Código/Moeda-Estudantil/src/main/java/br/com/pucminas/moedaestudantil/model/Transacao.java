package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transacao {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "quantia")
    private double quantidadeMoeadas;
    @ManyToOne
    private Conta origem;
    @ManyToOne
    private Conta destino;
    @Column(name = "data")
    private LocalDate data;
    @Column(name = "mensagem")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "vantagem_id")
    private Vantagem vantagem;
}
