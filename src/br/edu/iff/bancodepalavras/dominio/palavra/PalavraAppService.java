package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;

public class PalavraAppService {

    private static PalavraAppService soleInstance;

    private PalavraRepository palavraRepository;
    private TemaRepository temaRepository;
    private PalavraFactory palavraFactory;

    private PalavraAppService(TemaRepository temaRepository, PalavraRepository palavraRepository,
            PalavraFactory palavraFactory) {

    }

    public static void createSoleInstance(TemaRepository temaRepository, PalavraRepository palavraRepository,
            PalavraFactory palavraFactory) {
        if (soleInstance == null) {
            soleInstance = new PalavraAppService(temaRepository, palavraRepository, palavraFactory);
        }
    }

    public static PalavraAppService getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException();
        }
        return soleInstance;
    }

    // pré-condição: idTema tem que ser de um Tema pré-existente no repositório de
    // Tema.
    // novaPalavra():
    // verifica se a palavra (string) existe no repositório. Se exisitir não faz
    // nada e retorna true. Se não
    // existir, acessa o repositório de Tema para obter o Tema a partir de idTema,
    // cria a palavra e insere a
    // palavra no repositório. Retorna true se a palavra foi inserida com sucesso no
    // repositório ou false se
    // houve alguma RepositoryException.
    // public boolean novaPalavra(String palavra, long idTema){
    // Tema t = temaRepository.getPorId(idTema);
    // if(t == null){
    // throw new IllegalArgumentException();
    // }
    // Palavra p = palavraFactory.getPalavra(palavra, t);
    // if(this.palavraRepository.getPalavra(palavra)!=null) {
    // return true;
    // } else {
    // Palavra p = palavraFactory.getPalavra(palavra, t);
    // boolean sucesso = palavraRepository.inserir(p);

    // }

    // }
}
