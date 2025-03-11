package br.com.pucminas.matriculador2000.Controller;

import br.com.pucminas.matriculador2000.DTO.AlunoDTO;
import br.com.pucminas.matriculador2000.DTO.PeriodoMatriculaDTO;
import br.com.pucminas.matriculador2000.DTO.UsuarioDTO;
import br.com.pucminas.matriculador2000.Services.AlunoService;
import br.com.pucminas.matriculador2000.Services.CursoService;
import br.com.pucminas.matriculador2000.Services.ProfessorService;
import br.com.pucminas.matriculador2000.Services.SemestreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secretaria")
public class SecretariaController {

    private final CursoService cursoService;
    private final SemestreService semestreService;

    private final AlunoService alunoService;
    private final ProfessorService professorService;


    public SecretariaController(CursoService cursoService, ProfessorService professorService, AlunoService alunoService, SemestreService semestreService) {
        this.cursoService = cursoService;
        this.professorService = professorService;
        this.alunoService = alunoService;
        this.semestreService = semestreService;
    }

    @PostMapping("/gerarCurriculo")
    public ResponseEntity<?> gerarCurriculo() throws NoSuchFieldException, IllegalAccessException {
        return ResponseEntity.ok(cursoService.gerarCurriculo());
    }

    @PutMapping("/definirPeriodoMatricula")
    public ResponseEntity<?> definirPeriodoMatricula(@RequestBody PeriodoMatriculaDTO periodo) {
        this.semestreService.definirPeriodoMatricula(periodo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/professor")
    public ResponseEntity<?> professor(@RequestBody UsuarioDTO professor) {
        try {
            return ResponseEntity.ok(professorService.upsertProfessor(professor));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/aluno")
    public ResponseEntity<?> aluno(@RequestBody AlunoDTO aluno) {
        try {
            return ResponseEntity.ok(alunoService.upsertAluno(aluno));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UsuarioDTO dto) {
        if(dto.getPerfil().name() == "PROFESSOR") {
            professorService.delete(dto.getId());
        } else {
            alunoService.delete(dto.getId());
        }
        return ResponseEntity.ok().build();
    }
}
