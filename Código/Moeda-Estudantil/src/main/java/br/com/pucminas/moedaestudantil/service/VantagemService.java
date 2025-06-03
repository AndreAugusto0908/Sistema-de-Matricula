package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.CriarVantagemDTO;
import br.com.pucminas.moedaestudantil.model.Empresa;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.repository.EmpresaRepository;
import br.com.pucminas.moedaestudantil.repository.VantagemRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VantagemService {

    private final VantagemRepository vantagemRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public VantagemService(VantagemRepository vantagemRepository) {
        this.vantagemRepository = vantagemRepository;
    }

    public GenericResponse criarVantagem(CriarVantagemDTO data) {

        Empresa empresa = empresaRepository.findById(data.idEmpresa()).get();

        Vantagem vantagem = new Vantagem();

        vantagem.setEmpresa(empresa);
        vantagem.setDescricao(data.descricao());
        vantagem.setValorMoedas(data.valorMoedas());
        vantagem.setFoto(data.foto());

        vantagemRepository.save(vantagem);
        return new GenericResponse("Vantagem criada com sucesso", "sucesso");
    }

    public GenericResponse atualizarVantagem(Vantagem vantagem) {
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
