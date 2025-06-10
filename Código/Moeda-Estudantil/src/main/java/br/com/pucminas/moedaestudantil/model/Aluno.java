package br.com.pucminas.moedaestudantil.model;

import br.com.pucminas.moedaestudantil.DTO.RequestResgatarVantagem;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CPF;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.RG;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
public class Aluno extends UsuarioConta {

    @Column(name = "rg")
    @RG
    private String rg;

    @Column(name = "endereco")
    @NotBlank(message = "O endereço é obrigatório.")
    @Size(min = 5, max = 255, message = "O endereço deve ter entre {min} e {max} caracteres.")
    private String endereco;

    public Aluno(){
        this.role = "ROLE_ALUNO";
    }


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
