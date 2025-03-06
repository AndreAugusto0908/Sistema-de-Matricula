package br.com.pucminas.matriculador2000.Configuration;

import br.com.pucminas.matriculador2000.Models.*;
import br.com.pucminas.matriculador2000.Repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConfigInitializer implements ApplicationRunner {
    private final IAlunoRepository alunoRepositorie;
    private final IProfessorRepository professorRepositorie;
    private final IMatriculaRepository matriculaRepositorie;
    private final IDisciplinaRepository disciplinaRepositorie;
    private final ISecretariaRepository secretariaRepositorie;
    private final ITurmaRepository turmaRepositorie;
    private final ITurmaMatriculaRepository turmaMatriculaRepositorie;

    @Autowired
    public ConfigInitializer(
            IAlunoRepository alunoRepositorie,
            IProfessorRepository professorRepositorie,
            IMatriculaRepository matriculaRepositorie,
            IDisciplinaRepository disciplinaRepositorie,
            ISecretariaRepository secretariaRepositorie,
            ITurmaRepository turmaRepositorie,
            ITurmaMatriculaRepository turmaMatriculaRepositorie
    ) {

        this.alunoRepositorie = alunoRepositorie;
        this.professorRepositorie = professorRepositorie;
        this.matriculaRepositorie = matriculaRepositorie;
        this.disciplinaRepositorie = disciplinaRepositorie;
        this.secretariaRepositorie = secretariaRepositorie;
        this.turmaRepositorie = turmaRepositorie;
        this.turmaMatriculaRepositorie = turmaMatriculaRepositorie;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Matricula matricula = new Matricula();
        matricula.setDataFim(LocalDate.of(2025, 6, 30));
        matricula.setDataInicio(LocalDate.of(2025, 1, 30));
        matriculaRepositorie.save(matricula);

        Professor professor = new Professor();
        professor.setNome("Rodrigo Professor");
        professor.setEmail("professor@gmail.com");
        professor.setPerfil(PerfilEnum.PROFESSOR);
        professor.setSenha("123456");
        professorRepositorie.save(professor);

        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Projeto de Software");
        disciplina.setDescricao("Projeto de Software - Alguma descrição");
        disciplina.setAtiva(true);
        disciplinaRepositorie.save(disciplina);

        Turma turma = new Turma();
        turma.setLocal("Unidade Praça da Liberdade");
        turma.setDataFim(LocalDate.of(2025, 6, 30));
        turma.setDataInicio(LocalDate.of(2025, 1, 30));
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
        turmaRepositorie.save(turma);

        Aluno a = new Aluno();
        a.setNome("Aluno");
        a.setEmail("aluno@email.com");
        a.setSenha("123456");
        a.setPerfil(PerfilEnum.ALUNO);
        a.setMatricula(matricula);
        alunoRepositorie.save(a);

        Turma_Matricula turmaMatricula = new Turma_Matricula();
        turmaMatricula.setMatricula(matricula);
        turmaMatricula.setTurma(turma);
        turmaMatriculaRepositorie.save(turmaMatricula);

        Secretaria secretaria = new Secretaria();
        secretaria.setNome("Secretaria");
        secretaria.setEmail("secretaria@email.com");
        secretaria.setSenha("123456");
        secretaria.setPerfil(PerfilEnum.SECRETARIA);
        secretariaRepositorie.save(secretaria);

    }
}
