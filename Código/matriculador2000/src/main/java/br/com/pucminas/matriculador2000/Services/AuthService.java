package br.com.pucminas.matriculador2000.Services;

import br.com.pucminas.matriculador2000.Models.Usuario;
import br.com.pucminas.matriculador2000.Repositories.IAlunoRepository;
import br.com.pucminas.matriculador2000.Repositories.IProfessorRepository;
import br.com.pucminas.matriculador2000.Repositories.ISecretariaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

    IAlunoRepository alunoRepository;
    IProfessorRepository professorRepository;
    ISecretariaRepository secretariaRepository;


    public Usuario getUsuario(String username, String password) {
        Usuario user;
        user = this.alunoRepository.findAlunoByEmailAndSenha(username, password);
        if(user == null) {
            user = this.professorRepository.findProfessorByEmailAndSenha(username, password);
        }
        if(user == null) {
            user = this.secretariaRepository.findSecretariaByEmailAndSenha(username, password);
        }
        return user;
    }
}
