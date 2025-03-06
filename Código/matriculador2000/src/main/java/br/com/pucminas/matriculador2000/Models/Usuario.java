package br.com.pucminas.matriculador2000.Models;

import br.com.pucminas.matriculador2000.Security.JwtUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Data
public abstract class Usuario {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @Column(name = "nome")
    protected String nome;
    @Column(name="email")
    protected String email;
    @Column(name = "senha")
    protected String senha;
    @Column(name = "perfil")
    protected PerfilEnum perfil;

    public String logIn() {
        return JwtUtil.generateToken(this.email, this.perfil.name());
    }


}
