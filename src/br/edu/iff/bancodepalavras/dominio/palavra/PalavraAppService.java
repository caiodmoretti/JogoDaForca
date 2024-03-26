package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

public class PalavraAppService {

    private static PalavraAppService soleInstance;

    /* private */ PalavraRepository palavraRepository;
    TemaRepository temaRepository;
    PalavraFactory palavraFactory;

    private PalavraAppService(TemaRepository temaRepository, PalavraRepository palavraRepository,
            PalavraFactory palavraFactory) {
        this.temaRepository = temaRepository;
        this.palavraRepository = palavraRepository;
        this.palavraFactory = palavraFactory;
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

    public boolean novaPalavra(String palavra, long idTema) {
        if (temaRepository.getPorId(idTema) == null) {
            throw new IllegalArgumentException("Tema precisa ser pré-existente");
        }

        if (palavraRepository.getPalavra(palavra) != null) {
            return true;
        } else {
            try {
                palavraRepository.inserir(palavraFactory.getPalavra(palavra, temaRepository.getPorId(idTema)));
                return true;
            } catch (RepositoryException e) {
                System.out.println("Não foi possível inserir nova palavra!");
                return false;
            }
        }
    }
}
