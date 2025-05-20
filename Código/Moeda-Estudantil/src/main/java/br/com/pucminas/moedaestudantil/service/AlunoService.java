package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.AlunoDTO;
import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.model.Conta;
import br.com.pucminas.moedaestudantil.model.Transacao;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import br.com.pucminas.moedaestudantil.repository.AlunoRepository;
import br.com.pucminas.moedaestudantil.repository.ContaRepository;
import br.com.pucminas.moedaestudantil.repository.TransacaoRepository;
import br.com.pucminas.moedaestudantil.repository.VantagemAlunoRepository;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final VantagemAlunoRepository vantagemAlunoRepository;

    public AlunoService(AlunoRepository alunoRepository, ContaRepository contaRepository, TransacaoRepository transacaoRepository, VantagemAlunoRepository vantagemAlunoRepository) {
        this.alunoRepository = alunoRepository;
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.vantagemAlunoRepository = vantagemAlunoRepository;
    }

    public GenericResponse criarAluno(AlunoDTO aluno) {
        List<Aluno> alunoEmailIgual = this.alunoRepository.getAlunoByEmail(aluno.getEmail());
        Aluno alunoCpfIgual = this.alunoRepository.getAlunoByDocumento(aluno.getDocumento());
        Conta conta = new Conta();

        if(alunoEmailIgual.isEmpty() && alunoCpfIgual == null) {
            Aluno alunoEntity = new Aluno();
            alunoEntity.setNome(aluno.getNome());
            alunoEntity.setEmail(aluno.getEmail());
            alunoEntity.setSenha(new BCryptPasswordEncoder().encode(aluno.getSenha()));
            alunoEntity.setCurso(aluno.getCurso());
            alunoEntity.setConta(conta);
            alunoEntity.setRg(aluno.getRg());
            alunoEntity.setInstituicao("PUC Minas");
            alunoEntity.setEndereco(aluno.getEndereco());
            alunoEntity.setDocumento(aluno.getDocumento());
            contaRepository.save(conta);
            alunoRepository.save(alunoEntity);

            return new GenericResponse("Aluno criado com sucesso", "sucesso");
        } else {
            return  new GenericResponse("Já existe um aluno com essas dados no sistema", "erro");
        }
    }

    public GenericResponse atualizarAluno(AlunoDTO aluno) {
        if(aluno.getId() == null) {
            return new GenericResponse("É necessário enviar o Id do aluno para fazer a atualização", "erro");
        }
        Optional<Aluno> alunoSistemaOptional = this.alunoRepository.findById(aluno.getId());
        if (alunoSistemaOptional.isEmpty()) {
            return new GenericResponse("Não há um aluno com esses dados no sistema", "erro");
        }

        Aluno alunoEncontrado = alunoSistemaOptional.get();
        if(aluno.getNome() != null && !aluno.getNome().isEmpty()) {
            alunoEncontrado.setNome(aluno.getNome());
        }

        if(aluno.getEmail() != null && !aluno.getEmail().isEmpty()) {
            alunoEncontrado.setEmail(aluno.getEmail());
        }

        if (aluno.getDocumento() != null && !aluno.getDocumento().isEmpty()) {
            alunoEncontrado.setDocumento(aluno.getDocumento());
        }

        if (aluno.getCurso() != null && !aluno.getCurso().isEmpty()) {
            alunoEncontrado.setCurso(aluno.getCurso());
        }

        if (aluno.getSenha() != null && !aluno.getSenha().isEmpty()) {
            alunoEncontrado.setSenha(aluno.getSenha());
        }

        if (aluno.getEndereco() != null && !aluno.getEndereco().isEmpty()) {
            alunoEncontrado.setEndereco(aluno.getEndereco());
        }

        if (aluno.getRg() != null && !aluno.getRg().isEmpty()) {
            alunoEncontrado.setRg(aluno.getRg());
        }

        alunoRepository.save(alunoEncontrado);
        return new GenericResponse("Aluno atualizado com sucesso", "sucesso");

    }

    public GenericResponse deletarAluno(Long id) {
        Aluno aluno = this.alunoRepository.findById(id).get();
        List<VantagemAluno> vantagemAlunos = vantagemAlunoRepository.getByAluno(aluno);
        List<Transacao> transacoes = transacaoRepository.getByOrigemOrDestino(aluno.getConta(), aluno.getConta());
        vantagemAlunoRepository.deleteAll(vantagemAlunos);
        transacaoRepository.deleteAll(transacoes);
        contaRepository.delete(aluno.getConta());
        alunoRepository.deleteById(id);
        return new GenericResponse("Aluno deletado com sucesso", "sucesso");
    }
}
