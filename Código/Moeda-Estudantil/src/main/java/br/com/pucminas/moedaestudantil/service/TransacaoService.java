package br.com.pucminas.moedaestudantil.service;
import br.com.pucminas.moedaestudantil.DTO.CriarTransacaoDTO;
import br.com.pucminas.moedaestudantil.DTO.ExtratoDTO;
import br.com.pucminas.moedaestudantil.DTO.responses.GenericResponse;
import br.com.pucminas.moedaestudantil.model.*;
import br.com.pucminas.moedaestudantil.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {


    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final UsuarioContaRepository usuarioContaRepository;


    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository, UsuarioContaRepository usuarioContaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.usuarioContaRepository = usuarioContaRepository;
    }

    public ResponseEntity<?> realizarTransacao(CriarTransacaoDTO dto) {
        String docOrigem = dto.getDocumentoOrigem();
        String docReceb = dto.getDocumentoRecebedor();
        docOrigem = docOrigem.replaceAll("[^0-9]", "");
        docReceb = docReceb.replaceAll("[^0-9]", "");
        Conta origem;
        Conta recebedor;

        UsuarioConta userOrigem = usuarioContaRepository.getUsuarioContaByDocumento(docOrigem);
        UsuarioConta userDestino = usuarioContaRepository.getUsuarioContaByDocumento(docReceb);
        String tipoOrigem = userOrigem.getTipo();
        String tipoDestino = userDestino.getTipo();
        if (!tipoOrigem.equals(tipoDestino) &&
                ((tipoOrigem.equals("PROFESSOR") && tipoDestino.equals("ALUNO")) ||
                        ((tipoOrigem.equals("ALUNO") && tipoDestino.equals("EMPRESA"))))) {
            origem = userOrigem.getConta();
            recebedor = userOrigem.getConta();
            origem.setSaldo(origem.getSaldo() - dto.getValor());
            recebedor.setSaldo(recebedor.getSaldo() + dto.getValor());
        } else {
            return ResponseEntity.badRequest().body(new GenericResponse("Esse tipo de transação não é possível", "Erro"));
        }

        Transacao transacao = new Transacao();
        transacao.setData(LocalDate.now());
        transacao.setQuantidadeMoeadas(dto.getValor());
        transacao.setOrigem(origem);
        transacao.setDestino(recebedor);
        transacaoRepository.save(transacao);
        return ResponseEntity.ok(transacao);
    }

    public ExtratoDTO obterExtrato(String documento) {
        List<Transacao> destino = transacaoRepository.getByContaDestinoDocument(documento);
        List<Transacao> origem = transacaoRepository.getByContaOrigemDocument(documento);
        ExtratoDTO dtoResponse = new ExtratoDTO(destino, origem);
        return dtoResponse;
    }
}
