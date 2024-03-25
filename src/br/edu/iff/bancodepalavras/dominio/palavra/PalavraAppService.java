package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

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

    public boolean novaPalavra(String palavra, long idTema) {
        Tema tema = temaRepository.getPorId(idTema);
        if (tema == null) {
            throw new IllegalArgumentException("Tema precisa ser pré-existente");
        }

        if (palavraFactory.getPalavra(palavra, tema) != null) {
            return true;
        } else {
            try {
                palavraRepository.inserir(Palavra.criar(palavraRepository.getProximoId(), palavra, tema));
                return true;
            } catch (RepositoryException e) {
                System.out.println("Não foi possível inserir nova palavra!");
                return false;
            }
        }
    }
}
