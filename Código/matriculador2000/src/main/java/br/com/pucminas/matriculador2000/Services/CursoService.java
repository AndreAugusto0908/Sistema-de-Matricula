package br.com.pucminas.matriculador2000.Services;

import br.com.pucminas.matriculador2000.DTO.CurriculoDTO;
import br.com.pucminas.matriculador2000.DTO.PeriodoMatriculaDTO;
import br.com.pucminas.matriculador2000.Models.Curso;
import br.com.pucminas.matriculador2000.Models.Disciplina;
import br.com.pucminas.matriculador2000.Models.Turma_Matricula;
import br.com.pucminas.matriculador2000.Repositories.ICursoRepository;
import br.com.pucminas.matriculador2000.Repositories.IDisciplinaRepository;
import br.com.pucminas.matriculador2000.Repositories.ITurmaMatriculaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CursoService {

    ICursoRepository cursoRepository;
    IDisciplinaRepository disciplinaRepository;
    ITurmaMatriculaRepository turmaMatriculaRepository;
    public CursoService(ICursoRepository cursoRepository, IDisciplinaRepository disciplinaRepository, ITurmaMatriculaRepository turmaMatriculaRepository) {
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.turmaMatriculaRepository = turmaMatriculaRepository;
    }

    public void definirPeriodoMatricula(PeriodoMatriculaDTO periodo) {
        List<Curso> cursos = cursoRepository.findAll();
        Curso curso = cursos.get(0);
        if(!periodo.getDataInicio().isBlank()) {
            String[] periodoInicioSplit = periodo.getDataInicio().split("-");
            curso.setInicioPeriodoMatricula(LocalDate.of(Integer.parseInt(periodoInicioSplit[2]), Integer.parseInt(periodoInicioSplit[1]), Integer.parseInt(periodoInicioSplit[0])));
        }

        if(!periodo.getDataInicio().isBlank()) {
            String[] periodoFimSplit = periodo.getDataFim().split("-");
            curso.setFimPeriodoMatricula(LocalDate.of(Integer.parseInt(periodoFimSplit[2]), Integer.parseInt(periodoFimSplit[1]), Integer.parseInt(periodoFimSplit[0])));
        }

        if(curso.getFimPeriodoMatricula() != null && curso.getInicioPeriodoMatricula() != null) {
            LocalDate now = LocalDate.now();
            LocalDate desiredDate = curso.getFimPeriodoMatricula();
            long delay = ChronoUnit.MILLIS.between(desiredDate.atStartOfDay(), now.atStartOfDay());;

            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.schedule(this::desativarDisciplinas, delay, TimeUnit.MILLISECONDS);
        }
        cursoRepository.save(curso);
    }

    private void desativarDisciplinas() {
        List<Turma_Matricula> allTurmasMatriculas = turmaMatriculaRepository.findAll();
        Map<Disciplina, Integer> numeroMatriculasPorTurma = new HashMap<>();
        for(Turma_Matricula turmaMatricula : allTurmasMatriculas) {
            if(numeroMatriculasPorTurma.containsKey(turmaMatricula.getTurma().getDisciplina())) {
                numeroMatriculasPorTurma.put(turmaMatricula.getTurma().getDisciplina(), 0);
            }
            numeroMatriculasPorTurma.merge(turmaMatricula.getTurma().getDisciplina(), 1, Integer::sum);
        }
        List<Disciplina> disciplinas = new ArrayList<>();
        numeroMatriculasPorTurma.forEach((disciplina, numeroMatriculas) -> {
            disciplina.setAtiva(numeroMatriculas >= Disciplina.MIN_ALUNO);
            disciplinas.add(disciplina);
        });
        disciplinaRepository.saveAll(disciplinas);
    }

    public CurriculoDTO gerarCurriculo() throws NoSuchFieldException, IllegalAccessException, IllegalAccessException {
        CurriculoDTO dto = new CurriculoDTO();
        List<Disciplina> todasDiciplinas = disciplinaRepository.findAll();
        for(Disciplina disciplina : todasDiciplinas) {
            if(disciplina.isAtiva()) {
                String semestre = "semestre";
                semestre = semestre + disciplina.getPeriodo();
                try {
                    Field campo = CurriculoDTO.class.getDeclaredField(semestre);
                    campo.setAccessible(true);
                    List<Disciplina> disciplinas = (List<Disciplina>) campo.get(dto);
                    disciplinas.add(disciplina);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return dto;
    }
}
