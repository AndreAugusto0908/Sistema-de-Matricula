package br.com.pucminas.matriculador2000.Services;

import br.com.pucminas.matriculador2000.DTO.CurriculoDTO;
import br.com.pucminas.matriculador2000.Models.Disciplina;

import br.com.pucminas.matriculador2000.Repositories.IDisciplinaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import java.util.List;


@Service
public class CursoService {

    IDisciplinaRepository disciplinaRepository;
    public CursoService(IDisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
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
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return dto;
    }
}
