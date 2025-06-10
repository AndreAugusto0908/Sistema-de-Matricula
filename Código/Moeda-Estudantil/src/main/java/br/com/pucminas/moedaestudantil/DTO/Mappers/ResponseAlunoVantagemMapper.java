package br.com.pucminas.moedaestudantil.DTO.Mappers;

import br.com.pucminas.moedaestudantil.DTO.responses.ResponseAlunoVantagem;
import br.com.pucminas.moedaestudantil.model.Aluno;
import br.com.pucminas.moedaestudantil.model.Empresa;
import br.com.pucminas.moedaestudantil.model.Vantagem;
import br.com.pucminas.moedaestudantil.model.VantagemAluno;
import java.util.Random;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseAlunoVantagemMapper {


    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITOS = "0123456789";
    private static final Random random = new Random();

    public static List<ResponseAlunoVantagem> mapFrom(List<VantagemAluno> vantagemAlunos) {
        return vantagemAlunos.stream()
                .map(vantagemAluno -> {
                    Aluno aluno = vantagemAluno.getAluno();
                    Vantagem vantagem = vantagemAluno.getVantagem();
                    Empresa empresa = vantagem.getEmpresa();

                    return new ResponseAlunoVantagem(
                            empresa != null ? empresa.getNome() : null,
                            vantagem.getDescricao(),
                            vantagem.getValorMoedas(),
                            aluno.getNome(),
                            gerarCodigo()

                    );
                })
                .collect(Collectors.toList());
    }


    public static String gerarCodigo() {
        StringBuilder codigo = new StringBuilder();

        // Exemplo: 3 letras + 2 letras + 4 dígitos + 3 letras + 2 dígitos
        codigo.append(gerarLetras(3));   // CUP
        codigo.append(gerarLetras(2));   // XP
        codigo.append(gerarDigitos(4));  // 2315
        codigo.append(gerarLetras(3));   // ASD
        codigo.append(gerarDigitos(2));  // 20

        return codigo.toString();
    }

    private static String gerarLetras(int tamanho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            int idx = random.nextInt(LETRAS.length());
            sb.append(LETRAS.charAt(idx));
        }
        return sb.toString();
    }

    private static String gerarDigitos(int tamanho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            int idx = random.nextInt(DIGITOS.length());
            sb.append(DIGITOS.charAt(idx));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(gerarCodigo());
    }

}
