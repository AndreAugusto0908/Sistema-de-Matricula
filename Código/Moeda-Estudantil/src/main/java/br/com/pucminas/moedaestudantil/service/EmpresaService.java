package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.EmpresaDTO;
import br.com.pucminas.moedaestudantil.model.Empresa;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.repository.EmpresaRepository;
import br.com.pucminas.moedaestudantil.repository.VantagemRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final VantagemRepository vantagemRepository;

    public EmpresaService(EmpresaRepository empresaRepository, VantagemRepository vantagemRepository) {
        this.empresaRepository = empresaRepository;
        this.vantagemRepository = vantagemRepository;
    }

    public GenericResponse criarEmpresa(EmpresaDTO empresa) {

        Empresa empresaentity = new Empresa();
        empresaentity.setDocumento(empresa.getDocumento());
        empresaentity.setNome(empresa.getNome());

        empresaRepository.save(empresaentity);
        return new GenericResponse("Empresa cadastrada com sucesso", "sucesso");
    }

    public GenericResponse atualizarEmpresa(Empresa empresa) {
        if (empresa.getId() == null) {
            return new GenericResponse("É necessário enviar o ID da empresa", "erro");
        }

        Optional<Empresa> empresaEncontrada = empresaRepository.findById(empresa.getId());
        if (empresaEncontrada.isEmpty()) {
            return new GenericResponse("Empresa não encontrada", "erro");
        }

        Empresa e = empresaEncontrada.get();
        if (empresa.getNome() != null) e.setNome(empresa.getNome());
        if (empresa.getDocumento() != null) e.setDocumento(empresa.getDocumento());
        empresaRepository.save(e);

        return new GenericResponse("Empresa atualizada com sucesso", "sucesso");
    }

    public GenericResponse deletarEmpresa(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isEmpty()) {
            return new GenericResponse("Empresa não encontrada", "erro");
        }

        List<Vantagem> vantagens = vantagemRepository.findByEmpresa(empresa.get());
        if(vantagens != null){
            vantagemRepository.deleteAll(vantagens);
            empresaRepository.deleteById(id);
        }

        return new GenericResponse("Empresa deletada com sucesso", "sucesso");
    }
}
