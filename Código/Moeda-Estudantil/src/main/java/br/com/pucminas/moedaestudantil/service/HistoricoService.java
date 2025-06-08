package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.HistoricoExtratoDTO;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import br.com.pucminas.moedaestudantil.repository.VantagemAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoService {

    private final VantagemAlunoRepository vantagemAlunoRepository;

    @Autowired
    public HistoricoService(VantagemAlunoRepository vantagemAlunoRepository) {
        this.vantagemAlunoRepository = vantagemAlunoRepository;
    }

    public List<HistoricoExtratoDTO> buscarHistoricoPorNomeEmpresa(Long IdEmpresa) {
        List<VantagemAluno> vantagens = vantagemAlunoRepository.findByVantagem_Empresa_Id(IdEmpresa);

        return vantagens.stream()
                .map(vantagem -> new HistoricoExtratoDTO(
                        vantagem.getAluno().getNome(),
                        vantagem.getVantagem().getValorMoedas(),
                        vantagem.getVantagem().getDescricao()
                ))
                .collect(Collectors.toList());
    }
}
