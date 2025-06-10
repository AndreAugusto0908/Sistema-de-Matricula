package br.com.pucminas.moedaestudantil.controller;

import br.com.pucminas.moedaestudantil.DTO.CriarTransacaoDTO;
import br.com.pucminas.moedaestudantil.DTO.ExtratoDTO;
import br.com.pucminas.moedaestudantil.Infra.Security.SecurityConfigurations;
import br.com.pucminas.moedaestudantil.model.*;
import br.com.pucminas.moedaestudantil.repository.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transacao")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class TransacaoController {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioContaRepository usuarioContaRepository;

    public TransacaoController(TransacaoRepository transacaoRepository, ContaRepository contaRepository, AlunoRepository alunoRepository, ProfessorRepository professorRepository, EmpresaRepository empresaRepository, UsuarioContaRepository usuarioContaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.empresaRepository = empresaRepository;
        this.usuarioContaRepository = usuarioContaRepository;
    }

    @PostMapping
    public ResponseEntity<?> realizarTransacao(@RequestBody @Valid CriarTransacaoDTO dto){
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
            origem = aluno.getConta();
            recebedor = emp.getConta();

            origem.setSaldo(origem.getSaldo() - dto.getValor());
            recebedor.setSaldo(recebedor.getSaldo() - dto.getValor());
            contaRepository.save(recebedor);
            contaRepository.save(origem);
        } else {
            Aluno aluno = alunoRepository.getAlunoByDocumento(docReceb);
            Professor professor = professorRepository.getProfessorByDocumento(docOrigem);
            if(aluno == null || professor == null) { return ResponseEntity.internalServerError().build(); }
            origem = professor.getConta();
            recebedor = aluno.getConta();

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

    @GetMapping(value = "/obterExtrato")
    public ResponseEntity<?> obterExtrato(@RequestParam("id") Long id){
        UsuarioConta usuarioConta = this.usuarioContaRepository.findById(id).get();

        List<Transacao> trs =  transacaoRepository.getByOrigemOrDestino(usuarioConta.getConta(), usuarioConta.getConta());
        List<ExtratoDTO> extratoDTOs = new ArrayList<>();
        for(Transacao tr : trs) {
            String nomeOrigem = this.usuarioContaRepository.findById(tr.getOrigem().getId()).get().getNome();
            String nomeDestino = this.usuarioContaRepository.findById(tr.getDestino().getId()).get().getNome();
            extratoDTOs.add(new ExtratoDTO(tr.getQuantidadeMoeadas(), nomeOrigem, nomeDestino, tr.getData().toString()));
        }
        return ResponseEntity.ok(extratoDTOs);
    }
}
