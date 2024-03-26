package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

public class PalavraAppService {

	private PalavraRepository palavraRepository;
	private TemaRepository temaRepository;
	private PalavraFactory palavraFactory;
	
	private static PalavraAppService soleInstance;
	
	public static void createSoleInstance(PalavraRepository palavraRepository, TemaRepository temaRepository, PalavraFactory palavraFactory) {
		if(soleInstance==null) {
			soleInstance = new PalavraAppService(palavraRepository, temaRepository, palavraFactory);
		}
	}
	
	public static PalavraAppService getSoleInstance() {
		if(soleInstance==null) {
			throw new RuntimeException("Precisa chamar o createSoleInstance primeiro.");
		}
		return soleInstance;
	}
	
	private PalavraAppService(PalavraRepository palavraRepository, TemaRepository temaRepository, PalavraFactory palavraFactory) {
		this.palavraRepository = palavraRepository;
		this.temaRepository = temaRepository;
		this.palavraFactory = palavraFactory;
	}
	
	public boolean novaPalavra(String palavra, long idTema) {
		if(this.temaRepository.getPorId(idTema)==null) {
			throw new RuntimeException("O idTema tem que ser de um Tema pré-existente no repositório de Tema.");
		}
		if(this.palavraRepository.getPalavra(palavra)!=null) {
			return true;
		}else {
			try {
				this.palavraRepository.inserir(this.palavraFactory.getPalavra(palavra, this.temaRepository.getPorId(idTema)));
				return true;
			}catch(RepositoryException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
	}
	
}
