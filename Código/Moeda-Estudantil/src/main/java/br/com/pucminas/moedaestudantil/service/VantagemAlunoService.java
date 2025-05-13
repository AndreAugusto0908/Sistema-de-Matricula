package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.Mappers.ResponseAlunoVantagemMapper;
import br.com.pucminas.moedaestudantil.DTO.RequestResgatarVantagem;
import br.com.pucminas.moedaestudantil.DTO.responses.ResponseAlunoVantagem;
import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import br.com.pucminas.moedaestudantil.repository.AlunoRepository;
import br.com.pucminas.moedaestudantil.repository.VantagemAlunoRepository;
import br.com.pucminas.moedaestudantil.repository.VantagemRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VantagemAlunoService {

    @Autowired
    private VantagemAlunoRepository vantagemAlunoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    /**
     * Realiza o resgate de uma vantagem por um aluno específico.
     *
     * <p>Este método busca o aluno e a vantagem pelo ID, realiza o débito do valor da vantagem no saldo do aluno,
     * associa a vantagem ao aluno e persiste a operação no banco de dados.</p>
     *
     * @param vantagemAluno objeto contendo os IDs do aluno e da vantagem a serem processados
     * @return {@link GenericResponse} contendo a mensagem de sucesso e o saldo restante
     *
     */
    public GenericResponse resgatarVantagem(RequestResgatarVantagem vantagemAluno) {
        Aluno aluno = alunoRepository.findById(vantagemAluno.idAluno()).get();
        Vantagem vantagem = vantagemRepository.findById(vantagemAluno.idVantagem()).get();

        Double saldo = aluno.realizarCompra(vantagem.getValorMoedas());

        VantagemAluno newVantagemAluno = new VantagemAluno();
        newVantagemAluno.setAluno(aluno);
        newVantagemAluno.setVantagem(vantagem);

        vantagemAlunoRepository.save(newVantagemAluno);
        alunoRepository.save(aluno);
        return new GenericResponse("Vantagem resgatada com sucesso | Saldo" + saldo, "sucesso");
    }

    public GenericResponse deletarResgate(Long id) {
        Optional<VantagemAluno> va = vantagemAlunoRepository.findById(id);
        if (va.isEmpty()) {
            return new GenericResponse("Resgate não encontrado", "erro");
        }

        vantagemAlunoRepository.deleteById(id);
        return new GenericResponse("Resgate deletado com sucesso", "sucesso");
    }

    /**
     * Recupera todas as vantagens associadas a um aluno a partir do seu ID.
     *
     * <p>Se o aluno com o ID fornecido não for encontrado no banco de dados,
     * uma lista vazia será retornada.</p>
     *
     * @param idAluno o identificador único do aluno
     * @return uma lista de {@link ResponseAlunoVantagem} contendo os dados das vantagens do aluno,
     *         ou uma lista vazia se o aluno não existir ou não possuir vantagens registradas
     */
    public List<ResponseAlunoVantagem> getVantagemPorAluno(Long idAluno){
        Optional<Aluno> alunoOpt = alunoRepository.findById(idAluno);
        if (alunoOpt.isEmpty()) {return Collections.emptyList();}

        Aluno aluno = alunoOpt.get();
        List<VantagemAluno> vantagensAluno = vantagemAlunoRepository.getByAluno(aluno);
        return ResponseAlunoVantagemMapper.mapFrom(vantagensAluno);
    }
}
