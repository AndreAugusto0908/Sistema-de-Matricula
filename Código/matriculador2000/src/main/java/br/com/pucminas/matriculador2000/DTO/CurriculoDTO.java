package br.com.pucminas.matriculador2000.DTO;

import br.com.pucminas.matriculador2000.Models.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CurriculoDTO {
    private List<Disciplina> semestre1;
    private List<Disciplina> semestre2;
    private List<Disciplina> semestre3;
    private List<Disciplina> semestre4;
    private List<Disciplina> semestre5;
    private List<Disciplina> semestre6;
    private List<Disciplina> semestre7;
    private List<Disciplina> semestre8;

    public CurriculoDTO() {
        this.semestre1 = new ArrayList<>();
        this.semestre2 = new ArrayList<>();
        this.semestre3 = new ArrayList<>();
        this.semestre4 = new ArrayList<>();
        this.semestre5 = new ArrayList<>();
        this.semestre6 = new ArrayList<>();
        this.semestre7 = new ArrayList<>();
        this.semestre8 = new ArrayList<>();
    }
}
