package br.com.pucminas.matriculador2000.Services;

import br.com.pucminas.matriculador2000.Models.Aluno;
import br.com.pucminas.matriculador2000.Models.Matricula;
import br.com.pucminas.matriculador2000.Models.Turma;
import br.com.pucminas.matriculador2000.Models.Turma_Matricula;
import br.com.pucminas.matriculador2000.Repositories.IAlunoRepository;
import br.com.pucminas.matriculador2000.Repositories.IMatriculaRepository;
import br.com.pucminas.matriculador2000.Repositories.ITurmaMatriculaRepository;
import br.com.pucminas.matriculador2000.Repositories.ITurmaRepository;
import org.springframework.stereotype.Service;

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

    public String realizarMatricula(String email, String senha, long turmaId) {
        Optional<Turma> turmaOpt = this.turmaRepository.findById(turmaId);
        if(turmaOpt.isEmpty()) {
            return "Turma não encontrada";
        }

        Turma turma = turmaOpt.get();
        Aluno aluno = this.alunoRepository.findAlunoByEmailAndSenha(email, senha);
        List<Turma_Matricula> turmas_matriculas = turmaMatriculaRepository.findTurma_MatriculasByMatricula(aluno.getMatricula());
        if(turmas_matriculas.size() < 6) {
            int opcionais = 0;
            int obrigatorias = 0;
            for(Turma_Matricula tm : turmas_matriculas) {
                if(tm.getTurma().getDisciplina().isOpcional()) {
                    opcionais++;
                } else {
                    obrigatorias++;
                }
            }

            if((turma.getDisciplina().isOpcional() && obrigatorias < 4) || (!turma.getDisciplina().isOpcional() && opcionais < 2)) {
                Turma_Matricula tm = new Turma_Matricula();
                tm.setTurma(turma);
                tm.setMatricula(aluno.getMatricula());
                turmaMatriculaRepository.save(tm);
                return "Matricula adicionada com sucesso";
            } else {
                return "Infelizmente você alcançou o limite de disciplinas matriculadas.";
            }
        }
        return "Infelizmente você alcançou o limite de disciplinas matriculadas.";
    }

    public String cancelarMatricula(String email, String senha, long turmaId) {
        Optional<Turma> turmaOpt = this.turmaRepository.findById(turmaId);
        if(turmaOpt.isEmpty()) {
            return "Turma não encontrada";
        }

        Turma turma = turmaOpt.get();
        Aluno aluno = this.alunoRepository.findAlunoByEmailAndSenha(email, senha);
        List<Turma_Matricula> turmas_matriculas = turmaMatriculaRepository.findTurma_MatriculasByMatriculaAndTurma(aluno.getMatricula(), turma);
        if(!turmas_matriculas.isEmpty()) {
            turmaMatriculaRepository.delete(turmas_matriculas.get(0));
            return "Matricula removida com sucesso";
        }
        return "Turma não encontrada";
    }
}
