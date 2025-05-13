package br.com.pucminas.moedaestudantil.model;

import br.com.pucminas.moedaestudantil.DTO.RequestResgatarVantagem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Aluno extends UsuarioConta {
    @Column(name = "rg")
    private String rg;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "email")
    private String email;

    /**
     * Realiza uma compra debitando o valor do saldo do aluno através de sua conta.
     *
     * @param valor valor da compra a ser debitado
     * @return o saldo restante após a compra
     */
    public Double realizarCompra(Double valor) {
        return this.conta.realizarCompra(valor);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ALUNO"));
    }
}
