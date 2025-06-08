package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.VantagemDTO;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.repository.EmpresaRepository;
import br.com.pucminas.moedaestudantil.repository.VantagemRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VantagemService {

    private final VantagemRepository vantagemRepository;
    private final EmpresaRepository empresaRepository;

    public VantagemService(VantagemRepository vantagemRepository, EmpresaRepository empresaRepository) {
        this.vantagemRepository = vantagemRepository;
        this.empresaRepository = empresaRepository;
    }

    public GenericResponse criarVantagem(VantagemDTO vantagem) {
        Vantagem va = new Vantagem();
        va.setDescricao(vantagem.getDescricao());
        va.setValorMoedas(vantagem.getValorMoedas());
        va.setFoto(vantagem.getFoto());
        va.setEmpresa(this.empresaRepository.findById(vantagem.getEmpresa()).get());
        vantagemRepository.save(va);
        return new GenericResponse("Vantagem criada com sucesso", "sucesso");
    }

    public GenericResponse atualizarVantagem(VantagemDTO vantagem) {
        if (vantagem.getId() == null) {
            return new GenericResponse("ID da vantagem não informado", "erro");
        }

        Optional<Vantagem> vantagemExistente = vantagemRepository.findById(vantagem.getId());
        if (vantagemExistente.isEmpty()) {
            return new GenericResponse("Vantagem não encontrada", "erro");
        }

        Vantagem v = vantagemExistente.get();
        if (vantagem.getDescricao() != null) v.setDescricao(vantagem.getDescricao());
        if (vantagem.getFoto() != null) v.setFoto(vantagem.getFoto());
        if (vantagem.getValorMoedas() != null) v.setValorMoedas(vantagem.getValorMoedas());
        vantagemRepository.save(v);

        return new GenericResponse("Vantagem atualizada com sucesso", "sucesso");
    }

    public GenericResponse deletarVantagem(Long id) {
        if (!vantagemRepository.existsById(id)) {
            return new GenericResponse("Vantagem não encontrada", "erro");
        }

        vantagemRepository.deleteById(id);
        return new GenericResponse("Vantagem deletada com sucesso", "sucesso");
    }
}
