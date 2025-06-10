package br.com.pucminas.moedaestudantil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.CPFouCNPJ;
import br.com.pucminas.moedaestudantil.DTO.Validators.interfaces.Senha;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class UsuarioConta implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @CPFouCNPJ
    @Column(name = "documento", nullable = false, unique = true)
    protected String documento;

    @Column(name = "nome", nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 255, message = "O nome deve ter entre {min} e {max} caracteres.")
    protected String nome;

    @Column(name = "instituicao")
    @NotBlank(message = "A instituição é obrigatória.")
    @Size(min = 2, max = 255, message = "A instituição deve ter entre {min} e {max} caracteres.")
    protected String instituicao;
    @Column(name = "curso")
    @NotBlank(message = "O curso é obrigatório.")
    @Size(min = 2, max = 255, message = "O curso deve ter entre {min} e {max} caracteres.")
    protected String curso;

    @Column(name = "role")
    protected String role;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String email;


    @Column(name = "senha", nullable = false)
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 20, message = "A senha deve ter entre {min} e {max} caracteres.")
    @Senha
    protected String senha;

    @OneToOne
    protected Conta conta;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return documento;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


}