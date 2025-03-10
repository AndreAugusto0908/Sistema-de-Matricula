package br.com.pucminas.matriculador2000.Services;

import br.com.pucminas.matriculador2000.DTO.GenericResponse;
import br.com.pucminas.matriculador2000.DTO.UsuarioDTO;
import br.com.pucminas.matriculador2000.Models.PerfilEnum;
import br.com.pucminas.matriculador2000.Models.Professor;
import br.com.pucminas.matriculador2000.Repositories.IProfessorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
    private final IProfessorRepository professorRepository;
    public ProfessorService(IProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public GenericResponse upsertProfessor(UsuarioDTO professor) {
        GenericResponse res = new GenericResponse();
        try {
            Professor professorAux = new Professor();
            res.setMensagem("Professor criado com sucesso.");

            professorAux.setPerfil(PerfilEnum.PROFESSOR);

            if(professor.getId() != null){
                res.setMensagem("Professor atualizado com sucesso.");
                professorAux.setId(professor.getId());
            }
            if(professor.getNome() != null){
                professorAux.setNome(professor.getNome());
            }
            if(professor.getEmail() != null){
                professorAux.setEmail(professor.getEmail());
            }
            if(professor.getSenha() != null){
                professorAux.setSenha(professor.getSenha());
            }
            professorRepository.save(professorAux);
        } catch (Exception e) {
            res.setMensagem("Erro manipular registro de professor");
        }
        return res;
    }
}
