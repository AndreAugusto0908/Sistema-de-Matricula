package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import br.com.pucminas.moedaestudantil.repository.VantagemAlunoRepository;
import br.com.pucminas.moedaestudantil.responses.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VantagemAlunoService {

    private final VantagemAlunoRepository vantagemAlunoRepository;

    public VantagemAlunoService(VantagemAlunoRepository vantagemAlunoRepository) {
        this.vantagemAlunoRepository = vantagemAlunoRepository;
    }

    public GenericResponse resgatarVantagem(VantagemAluno vantagemAluno) {
        vantagemAlunoRepository.save(vantagemAluno);
        return new GenericResponse("Vantagem resgatada com sucesso", "sucesso");
    }

    public GenericResponse deletarResgate(Long id) {
        Optional<VantagemAluno> va = vantagemAlunoRepository.findById(id);
        if (va.isEmpty()) {
            return new GenericResponse("Resgate não encontrado", "erro");
        }

        vantagemAlunoRepository.deleteById(id);
        return new GenericResponse("Resgate deletado com sucesso", "sucesso");
    }
}
