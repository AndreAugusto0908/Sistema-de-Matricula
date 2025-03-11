package br.com.pucminas.matriculador2000.Services;

import br.com.pucminas.matriculador2000.DTO.PeriodoMatriculaDTO;
import br.com.pucminas.matriculador2000.Models.Disciplina;
import br.com.pucminas.matriculador2000.Models.Semestre;
import br.com.pucminas.matriculador2000.Models.Turma_Matricula;
import br.com.pucminas.matriculador2000.Repositories.IDisciplinaRepository;
import br.com.pucminas.matriculador2000.Repositories.ISemestreRepository;
import br.com.pucminas.matriculador2000.Repositories.ITurmaMatriculaRepository;
import org.springframework.stereotype.Service;

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
public class SemestreService {
    
    private ISemestreRepository semestreRepository;
    private ITurmaMatriculaRepository turmaMatriculaRepository;
    private IDisciplinaRepository disciplinaRepository;

    
    public SemestreService(ISemestreRepository semestreRepository, IDisciplinaRepository disciplinaRepository, ITurmaMatriculaRepository turmaMatriculaRepository) {
        this.semestreRepository = semestreRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.turmaMatriculaRepository = turmaMatriculaRepository;
    }
    
    public void definirPeriodoMatricula(PeriodoMatriculaDTO periodo) {
        List<Semestre> semestres = semestreRepository.findAll();
        Semestre semestre = semestres.get(0);
        if(!periodo.getDataInicio().isBlank()) {
            String[] periodoInicioSplit = periodo.getDataInicio().split("-");
            semestre.setInicioPeriodoMatricula(LocalDate.of(Integer.parseInt(periodoInicioSplit[2]), Integer.parseInt(periodoInicioSplit[1]), Integer.parseInt(periodoInicioSplit[0])));
        }

        if(!periodo.getDataInicio().isBlank()) {
            String[] periodoFimSplit = periodo.getDataFim().split("-");
            semestre.setFimPeriodoMatricula(LocalDate.of(Integer.parseInt(periodoFimSplit[2]), Integer.parseInt(periodoFimSplit[1]), Integer.parseInt(periodoFimSplit[0])));
        }

        if(semestre.getFimPeriodoMatricula() != null && semestre.getInicioPeriodoMatricula() != null) {
            LocalDate now = LocalDate.now();
            LocalDate desiredDate = semestre.getFimPeriodoMatricula();
            long delay = ChronoUnit.MILLIS.between(desiredDate.atStartOfDay(), now.atStartOfDay());;

            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.schedule(this::desativarDisciplinas, delay, TimeUnit.MILLISECONDS);
        }
        semestreRepository.save(semestre);
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
}
