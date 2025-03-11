package br.com.pucminas.matriculador2000.Services;

import br.com.pucminas.matriculador2000.DTO.AlunoDTO;
import br.com.pucminas.matriculador2000.DTO.GenericResponse;
import br.com.pucminas.matriculador2000.Models.*;
import br.com.pucminas.matriculador2000.Repositories.IAlunoRepository;
import br.com.pucminas.matriculador2000.Repositories.IMatriculaRepository;
import br.com.pucminas.matriculador2000.Repositories.ITurmaMatriculaRepository;
import br.com.pucminas.matriculador2000.Repositories.ITurmaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final IAlunoRepository alunoRepository;
    private final IMatriculaRepository matriculaRepository;
    private final ITurmaMatriculaRepository turmaMatriculaRepository;
    private final ITurmaRepository turmaRepository;

    public AlunoService(IAlunoRepository alunoRepository, IMatriculaRepository matriculaRepository, ITurmaRepository turmaRepository, ITurmaMatriculaRepository turmaMatricula) {
        this.alunoRepository = alunoRepository;
        this.matriculaRepository = matriculaRepository;
        this.turmaRepository = turmaRepository;
        this.turmaMatriculaRepository = turmaMatricula;
    }

    public String realizarMatricula(String email, String senha, Long turmaId) {
        Optional<Turma> turmaOpt = this.turmaRepository.findById(turmaId);
        if(turmaOpt.isEmpty()) {
            return "Turma não encontrada";
        }

        Turma turma = turmaOpt.get();
        int turmas_matriculas_numMax = turmaMatriculaRepository.findTurma_MatriculasByTurma(turma).size();
        if(turmas_matriculas_numMax >= Turma.MAX_ALUNOS){
            return "Infelizmente essa turma já alcançou a capacidade máxima de alunos";
        }

        Aluno aluno = this.alunoRepository.findAlunoByEmailAndSenha(email, senha);
        Matricula matricula = matriculaRepository.findMatriculaByAluno(aluno);
        List<Turma_Matricula> turmas_matriculas = turmaMatriculaRepository.findTurma_MatriculasByMatricula(matricula);
        if(turmas_matriculas.size() < 6) {
            int opcionais = 0;
            int obrigatorias = 0;
            for(Turma_Matricula tm : turmas_matriculas) {
                if(tm.getTurma().equals(turma)) {
                    return "Você já está matriculado nessa turma!";
                }
                if(tm.getTurma().getDisciplina().isOpcional()) {
                    opcionais++;
                } else {
                    obrigatorias++;
                }
            }

            if((turma.getDisciplina().isOpcional() && obrigatorias < 4) || (!turma.getDisciplina().isOpcional() && opcionais < 2)) {
                Turma_Matricula tm = new Turma_Matricula();
                tm.setTurma(turma);
                tm.setMatricula(matricula);
                try {
                    turmaMatriculaRepository.save(tm);

                }catch (Exception e) {
                    e.printStackTrace();
                }
                return "Matricula adicionada com sucesso";
            } else {
                return "Infelizmente você alcançou o limite de disciplinas matriculadas.";
            }
        }
        return "Infelizmente você alcançou o limite de disciplinas matriculadas.";
    }

    public String cancelarMatricula(String email, String senha, Long turmaId) {
        Optional<Turma> turmaOpt = this.turmaRepository.findById(turmaId);
        if(turmaOpt.isEmpty()) {
            return "Turma não encontrada";
        }

        Turma turma = turmaOpt.get();
        Aluno aluno = this.alunoRepository.findAlunoByEmailAndSenha(email, senha);
        Matricula matriculaPorAluno = this.matriculaRepository.findMatriculaByAluno(aluno);
        List<Turma_Matricula> turmas_matriculas = turmaMatriculaRepository.findTurma_MatriculasByMatriculaAndTurma(matriculaPorAluno, turma);
        if(!turmas_matriculas.isEmpty()) {
            turmaMatriculaRepository.delete(turmas_matriculas.get(0));
            return "Matricula removida com sucesso";
        }
        return "Turma não encontrada";
    }

    public List<Turma> findTurmasMatriculadas(Long matriculaId) {
        List<Matricula> matricula = matriculaRepository.findMatriculaById(matriculaId);
        List<Turma_Matricula> turmasMatriculadas =  turmaMatriculaRepository.findTurma_MatriculasByMatricula(matricula.get(0));
        List<Turma> turmas = new ArrayList<Turma>();
        for(Turma_Matricula tm : turmasMatriculadas) {
            turmas.add(tm.getTurma());
        }
        return turmas;
    }

    public GenericResponse upsertAluno(AlunoDTO alunoDTO) {
        GenericResponse res = new GenericResponse();

        try {
            res.setMensagem("Aluno criado com sucesso");
            Aluno a = new Aluno();
            a.setPerfil(PerfilEnum.ALUNO);
            if(alunoDTO.getId() != null) {
                res.setMensagem("Aluno atualizado com sucesso");
                a.setId(alunoDTO.getId());
            }
            if(alunoDTO.getNome() != null) {
                a.setNome(alunoDTO.getNome());
            }
            if(alunoDTO.getEmail() != null) {
                a.setEmail(alunoDTO.getEmail());
            }
            if(alunoDTO.getSenha() != null) {
                a.setSenha(alunoDTO.getSenha());
            }
            if(alunoDTO.getIdMatricula() != null) {
                Matricula mat = matriculaRepository.findMatriculaById(alunoDTO.getIdMatricula()).get(0);
                a.setMatricula(mat.getNumeroMatricula());
            }
            alunoRepository.save(a);
        } catch (Exception e) {
            res.setMensagem("Erro manipular registro de aluno");
        }
        return res;
    }
}
