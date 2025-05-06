package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Empresa extends ProprietarioConta {

    @Column(name = "documento")
    protected String documento;
    @Column(name = "nome")
    protected String nome;

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
