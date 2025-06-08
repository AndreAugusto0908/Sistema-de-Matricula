package br.com.pucminas.moedaestudantil.model;

import br.com.pucminas.moedaestudantil.exceptions.handlers.SaldoInsuficienteException;
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
        if ((saldo - valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para compra");
        }

        this.saldo -= valor;
        return saldo;
    }





}
