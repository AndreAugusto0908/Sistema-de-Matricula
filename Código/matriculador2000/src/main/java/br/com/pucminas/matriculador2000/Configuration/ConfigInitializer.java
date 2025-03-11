package br.com.pucminas.matriculador2000.Configuration;

import br.com.pucminas.matriculador2000.Models.*;
import br.com.pucminas.matriculador2000.Repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ConfigInitializer implements ApplicationRunner {
    private final IAlunoRepository alunoRepositorie;
    private final IProfessorRepository professorRepositorie;
    private final IMatriculaRepository matriculaRepositorie;
    private final IDisciplinaRepository disciplinaRepositorie;
    private final ISecretariaRepository secretariaRepositorie;
    private final ITurmaRepository turmaRepositorie;
    private final ITurmaMatriculaRepository turmaMatriculaRepositorie;
    private final ICursoRepository cursoRepositorie;
    private final ISemestreRepository semestreRepositorie;

    @Autowired
    public ConfigInitializer(
            IAlunoRepository alunoRepositorie,
            IProfessorRepository professorRepositorie,
            IMatriculaRepository matriculaRepositorie,
            IDisciplinaRepository disciplinaRepositorie,
            ISecretariaRepository secretariaRepositorie,
            ITurmaRepository turmaRepositorie,
            ITurmaMatriculaRepository turmaMatriculaRepositorie,
            ICursoRepository cursoRepositorie,
            ISemestreRepository semestreRepositorie
    ) {

        this.alunoRepositorie = alunoRepositorie;
        this.professorRepositorie = professorRepositorie;
        this.matriculaRepositorie = matriculaRepositorie;
        this.disciplinaRepositorie = disciplinaRepositorie;
        this.secretariaRepositorie = secretariaRepositorie;
        this.turmaRepositorie = turmaRepositorie;
        this.turmaMatriculaRepositorie = turmaMatriculaRepositorie;
        this.cursoRepositorie = cursoRepositorie;
        this.semestreRepositorie = semestreRepositorie;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Curso curso = new Curso();
        curso.setDescricao("Curso de Engenaria de Software");
        curso.setNome("Engenaria de Software");
        curso.setInstituicao("PUC Minas");
        cursoRepositorie.save(curso);

        Semestre semestre = new Semestre();
        semestre.setInicioPeriodoMatricula(LocalDate.now());
        semestre.setFimPeriodoMatricula(LocalDate.now().plusDays(30));
        semestre.setCurso(curso);
        semestreRepositorie.save(semestre);


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
        disciplina.setOpcional(true);
        disciplina.setPeriodo(4);
        disciplina.setCurso(curso);
        disciplinaRepositorie.save(disciplina);

        Turma turma = new Turma();
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
        turma.setHorario(LocalTime.of(19, 0));
        turma.setSemestre(semestre);
        turmaRepositorie.save(turma);

        Disciplina disciplina2 = new Disciplina();
        disciplina2.setNome("Arquitetura de Software");
        disciplina2.setDescricao("Arquitetura de Software - Alguma descrição");
        disciplina2.setAtiva(true);
        disciplina2.setOpcional(true);
        disciplina2.setPeriodo(4);
        disciplina2.setCurso(curso);
        disciplinaRepositorie.save(disciplina2);

        Turma turma2 = new Turma();
        turma2.setDisciplina(disciplina2);
        turma2.setProfessor(professor);
        turma2.setHorario(LocalTime.of(20, 40));
        turma2.setSemestre(semestre);
        turmaRepositorie.save(turma2);

        Aluno a = new Aluno();
        a.setNome("Aluno");
        a.setEmail("aluno@email.com");
        a.setSenha("123456");
        a.setPerfil(PerfilEnum.ALUNO);
        a.setMatricula(1L);
        alunoRepositorie.save(a);

        Matricula matricula = new Matricula();
        matricula.setAluno(a);
        matricula.setNumeroMatricula(1L);
        matricula.setValor(1500);
        matricula.setSemestre(semestre);
        matriculaRepositorie.save(matricula);

        Turma_Matricula turmaMatricula = new Turma_Matricula();
        turmaMatricula.setMatricula(matricula);
        turmaMatricula.setTurma(turma);
        turmaMatriculaRepositorie.save(turmaMatricula);

        Secretaria secretaria = new Secretaria();
        secretaria.setNome("Secretaria");
        secretaria.setEmail("secretaria@email.com");
        secretaria.setSenha("1234567");
        secretaria.setPerfil(PerfilEnum.SECRETARIA);
        secretariaRepositorie.save(secretaria);

    }
}
