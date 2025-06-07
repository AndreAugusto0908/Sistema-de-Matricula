package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Conta {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "saldo")
    private double saldo;

    /**
     * Debita o valor informado do saldo atual da conta.
     *
     * @param valor valor a ser debitado
     * @return o novo saldo após o débito
     */
    public Double realizarCompra(Double valor) {
        this.saldo -= valor;
        return saldo;
    }


    @OneToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @OneToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;



}
