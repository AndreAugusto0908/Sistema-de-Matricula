package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Professor extends Usuario {
    @Column(name = "departamento")
    private String departamento;

    @Override
    public List<Transacao> consultarExtrato() {
        return List.of();
    }

    @Override
    public double consultarSaldo() {
        return 0;
    }

    @Override
    public Transacao gerarTransacao() {
        return null;
    }
}
