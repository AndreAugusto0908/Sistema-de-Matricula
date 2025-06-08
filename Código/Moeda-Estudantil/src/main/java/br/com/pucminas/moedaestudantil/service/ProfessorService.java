package br.com.pucminas.moedaestudantil.service;

import br.com.pucminas.moedaestudantil.DTO.EnviarMoedasDTO;
import br.com.pucminas.moedaestudantil.DTO.TransacaoResponseDTO;
import br.com.pucminas.moedaestudantil.model.*;
import br.com.pucminas.moedaestudantil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public TransacaoResponseDTO enviarMoedas(String documentoProfessor, EnviarMoedasDTO dto) {
        Professor professor = professorRepository.getProfessorByDocumento(documentoProfessor);
        Aluno aluno = alunoRepository.getAlunoByDocumento(dto.getDocumentoRecebedor());

        if (professor == null || aluno == null) {
            throw new RuntimeException("Professor ou aluno não encontrado");
        }

        Conta contaProfessor = professor.getConta();
        Conta contaAluno = aluno.getConta();

        if (contaProfessor.getSaldo() < dto.getValor()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Atualiza os saldos
        contaProfessor.setSaldo(contaProfessor.getSaldo() - dto.getValor());
        contaAluno.setSaldo(contaAluno.getSaldo() + dto.getValor());

        // Salva as contas atualizadas
        contaRepository.save(contaProfessor);
        contaRepository.save(contaAluno);

        // Cria e salva a transação
        Transacao transacao = new Transacao();
        transacao.setData(LocalDate.now());
        transacao.setOrigem(contaProfessor);
        transacao.setDestino(contaAluno);
        transacao.setQuantidadeMoeadas(dto.getValor());
        transacao.setMensagem(dto.getObservacao());

        Transacao savedTransacao = transacaoRepository.save(transacao);

        return new TransacaoResponseDTO(
            savedTransacao.getId(),
            savedTransacao.getData(),
            savedTransacao.getQuantidadeMoeadas(),
            professor.getNome(),
            aluno.getNome(),
            savedTransacao.getMensagem()
        );
    }

    public List<TransacaoResponseDTO> listarTransacoes(String documentoProfessor) {
        Professor professor = professorRepository.getProfessorByDocumento(documentoProfessor);
        if (professor == null) {
            throw new RuntimeException("Professor não encontrado");
        }

        List<Transacao> transacoes = transacaoRepository.findByOrigem_UsuarioConta(professor.getConta());
        
        return transacoes.stream()
            .map(t -> {
                Aluno aluno = alunoRepository.findByConta(t.getDestino());
                return new TransacaoResponseDTO(
                    t.getId(),
                    t.getData(),
                    t.getQuantidadeMoeadas(),
                    professor.getNome(),
                    aluno != null ? aluno.getNome() : "Usuário não encontrado",
                    t.getMensagem()
                );
            })
            .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 1 1,7 *") // Executa à meia-noite do primeiro dia de janeiro e julho
    public void adicionarBonusSemestral() {
        List<Professor> professores = professorRepository.findAll();
        for (Professor professor : professores) {
            Conta conta = professor.getConta();
            conta.setSaldo(conta.getSaldo() + 1000.0);
            contaRepository.save(conta);
        }
    }

    public Double getSaldoProfessor(String documentoProfessor) {
        Professor professor = professorRepository.getProfessorByDocumento(documentoProfessor);
        if (professor == null) {
            throw new RuntimeException("Professor não encontrado");
        }
        return professor.getConta().getSaldo();
    }
} 