package br.com.pucminas.moedaestudantil.Infra.configs;

import br.com.pucminas.moedaestudantil.model.*;
import br.com.pucminas.moedaestudantil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SetupDataMock implements ApplicationRunner {
    private final AlunoRepository alunoRepository;
    private final ContaRepository contaRepository;
    private final ProfessorRepository professorRepository;
    private final EmpresaRepository empresaRepository;
    private final TransacaoRepository transacaoRepository;
    private final VantagemRepository vantagemRepository;
    private final VantagemAlunoRepository vantagemAlunoRepository;

    @Autowired
    public SetupDataMock
            (
            AlunoRepository alunoRepository,
            ContaRepository contaRepository,
            ProfessorRepository professorRepository,
            EmpresaRepository empresaRepository,
            TransacaoRepository transacaoRepository,
            VantagemRepository vantagemRepository,
            VantagemAlunoRepository vantagemAlunoRepository)
    {

        this.alunoRepository = alunoRepository;
        this.contaRepository = contaRepository;
        this.professorRepository = professorRepository;
        this.empresaRepository = empresaRepository;
        this.transacaoRepository = transacaoRepository;
        this.vantagemRepository = vantagemRepository;
        this.vantagemAlunoRepository = vantagemAlunoRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Professor professor = new Professor();
        professor.setNome("Rodrigo");
        professor.setDepartamento("Departamento X");
        professor.setCurso("Engenharia de Software");
        professor.setDocumento("14434357670");
        professor.setEmail("renatamarcosandre@outlook.com");
        professor.setSenha(new BCryptPasswordEncoder().encode("Teste123*"));
        professor.setInstituicao("PUC Minas");

        Conta contaProfessor = new Conta();
        contaProfessor.setSaldo(500.0);

        this.contaRepository.save(contaProfessor);
        professor.setConta(contaProfessor);

        this.professorRepository.save(professor);

        Empresa empresa = new Empresa();
        empresa.setNome("Praçaí");
        empresa.setDocumento("34967550000104");
        empresa.setEmail("dede@gmail.com");
        empresa.setSenha(new BCryptPasswordEncoder().encode("Teste123*"));

        Conta contaEmpresa = new Conta();
        contaEmpresa.setSaldo(5000.0);
        this.contaRepository.save(contaEmpresa);
        empresa.setConta(contaEmpresa);
        this.empresaRepository.save(empresa);


        Aluno aluno = new Aluno();
        aluno.setNome("Pedro C");
        aluno.setCurso("Engenharia de Software");
        aluno.setDocumento("97945153615");
        aluno.setSenha(new BCryptPasswordEncoder().encode("Teste123*"));
        aluno.setInstituicao("PUC Minas");
        aluno.setEndereco("Rua José Onésimo de Abreu 20");
        aluno.setEmail("andreaugustosilvacarvalho@gmail.com");
        aluno.setRg("MG-12345678-9");

        Conta contaAluno = new Conta();
        contaAluno.setSaldo(500);
        this.contaRepository.save(contaAluno);

        aluno.setConta(contaAluno);

        this.alunoRepository.save(aluno);


        Vantagem vantagem = new Vantagem();
        vantagem.setDescricao("Desconto de 10% no açai");
        vantagem.setFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKmLK-8XddoZZedNO2qdad5KwfVGzyM8ISaA&s");
        vantagem.setEmpresa(empresa);
        vantagem.setValorMoedas(20.0);
        this.vantagemRepository.save(vantagem);

        Vantagem vantagem2 = new Vantagem();
        vantagem2.setDescricao("Uma semana de açai de graça");
        vantagem2.setFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDSqrnuzruFFmsja8bQH0go-EFdTrDlFFy-w&s");
        vantagem2.setEmpresa(empresa);
        vantagem2.setValorMoedas(550.0);
        this.vantagemRepository.save(vantagem2);

        Vantagem vantagem3 = new Vantagem();
        vantagem3.setDescricao("Vantagem Boladona");
        vantagem3.setFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDSqrnuzruFFmsja8bQH0go-EFdTrDlFFy-w&s");
        vantagem3.setEmpresa(empresa);
        vantagem3.setValorMoedas(50.0);
        this.vantagemRepository.save(vantagem3);

        Transacao transacao = new Transacao();
        transacao.setData(LocalDate.now().minusDays(2));
        transacao.setOrigem(contaAluno);
        transacao.setDestino(contaEmpresa);
        transacao.setMensagem("Desconto de 10% no açai");
        transacao.setQuantidadeMoeadas(20.0);
        this.transacaoRepository.save(transacao);

        VantagemAluno vantagemAluno = new VantagemAluno();
        vantagemAluno.setAluno(aluno);
        vantagemAluno.setVantagem(vantagem);
        vantagemAlunoRepository.save(vantagemAluno);
    }

}
