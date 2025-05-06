package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Data
public abstract class Usuario extends ProprietarioConta {
    @Column(name = "documento")
    protected String documento;
    @Column(name = "nome")
    protected String nome;
    @Column(name = "instituicao")
    protected String instituicao;
    @Column(name = "curso")
    protected String curso;
    @Column(name = "senha")
    protected String senha;

}