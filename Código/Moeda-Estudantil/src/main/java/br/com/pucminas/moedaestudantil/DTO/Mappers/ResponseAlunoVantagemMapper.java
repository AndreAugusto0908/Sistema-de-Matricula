package br.com.pucminas.moedaestudantil.DTO.Mappers;

import br.com.pucminas.moedaestudantil.DTO.responses.ResponseAlunoVantagem;
import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.model.Empresa;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseAlunoVantagemMapper {

    public static List<ResponseAlunoVantagem> mapFrom(List<VantagemAluno> vantagemAlunos) {
        return vantagemAlunos.stream()
                .map(vantagemAluno -> {
                    Aluno aluno = vantagemAluno.getAluno();
                    Vantagem vantagem = vantagemAluno.getVantagem();
                    Empresa empresa = vantagem.getEmpresa();

                    return new ResponseAlunoVantagem(
                            empresa != null ? empresa.getNome() : null,
                            vantagem.getDescricao(),
                            vantagem.getValorMoedas(),
                            aluno.getNome()
                    );
                })
                .collect(Collectors.toList());
    }

}
