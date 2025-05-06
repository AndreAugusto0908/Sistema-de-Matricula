package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.CriarTransacaoDTO;
import br.com.pucminas.moedaestudantil.model.*;
import br.com.pucminas.moedaestudantil.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final EmpresaRepository empresaRepository;

    public TransacaoController(TransacaoRepository transacaoRepository, ContaRepository contaRepository, AlunoRepository alunoRepository, ProfessorRepository professorRepository, EmpresaRepository empresaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.empresaRepository = empresaRepository;
    }

    @PostMapping
    public ResponseEntity<?> realizarTransacao(@RequestBody CriarTransacaoDTO dto){
        String docOrigem = dto.getDocumentoOrigem();
        String docReceb = dto.getDocumentoRecebedor();
        docOrigem = docOrigem.replaceAll("[^0-9]", "");
        docReceb = docReceb.replaceAll("[^0-9]", "");
        Conta origem = new Conta();
        Conta recebedor = new Conta();
        if(docReceb.length() > 11) {
            Aluno aluno = alunoRepository.getAlunoByDocumento(docOrigem);
            Empresa emp = empresaRepository.getEmpresaByDocumento(docReceb);

            if(aluno == null || emp == null) { return ResponseEntity.internalServerError().build(); }
            origem = contaRepository.getContasByProprietario_Id(aluno.getId());
            recebedor = contaRepository.getContasByProprietario_Id(emp.getId());

            origem.setSaldo(origem.getSaldo() - dto.getValor());
            recebedor.setSaldo(recebedor.getSaldo() - dto.getValor());
            contaRepository.save(recebedor);
            contaRepository.save(origem);
        } else {
            Aluno aluno = alunoRepository.getAlunoByDocumento(docReceb);
            Professor professor = professorRepository.getProfessorByDocumento(docOrigem);
            if(aluno == null || professor == null) { return ResponseEntity.internalServerError().build(); }
            origem = contaRepository.getContasByProprietario_Id(professor.getId());
            recebedor = contaRepository.getContasByProprietario_Id(aluno.getId());

            origem.setSaldo(origem.getSaldo() - dto.getValor());
            recebedor.setSaldo(recebedor.getSaldo() + dto.getValor());
            contaRepository.save(recebedor);
            contaRepository.save(origem);

        }

        Transacao transacao = new Transacao();
        transacao.setData(LocalDate.now());
        transacao.setQuantidadeMoeadas(dto.getValor());
        transacao.setOrigem(origem);
        transacao.setDestino(recebedor);
        transacaoRepository.save(transacao);
        return ResponseEntity.ok(transacao);
    }
}
