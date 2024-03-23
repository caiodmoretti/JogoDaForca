package br.edu.iff.jogoforca.dominio.rodada.sorteio;

import java.util.Random;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public class RodadaSorteioFactory extends RodadaFactoryImpl {
	
	private static RodadaSorteioFactory soleInstance;
	
	public static void createSoleInstance(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
		if(soleInstance==null) {
			soleInstance = new RodadaSorteioFactory(repository, temaRepository, palavraRepository);
		}
	}
	
	public static RodadaSorteioFactory getSoleInstance() {
		if(soleInstance==null) {
			throw new RuntimeException("É necessário chamar o método createSoleInstance primeiro.");
		}
		return soleInstance;
	}
		private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
			super(repository, temaRepository, palavraRepository);
		}
		
	public Rodada getRodada(Jogador jogador) {
		Random random = new Random();
		Tema temaEscolhido = this.getTemaRepository().getTodos()[random.nextInt(this.getTemaRepository().getTodos().length-1)];
		int quantidadePalavrasSorteadas =  random.nextInt(3) + 1; // nextInt(3) gera números entre 0 (inclusive) e 3 (exclusivo). 
		Palavra[] palavrasTema = this.getPalavraRepository().getPorTema(temaEscolhido);
        if(palavrasTema.length < quantidadePalavrasSorteadas) {
        	throw new RuntimeException("Não há palavras sufucientes no tema sorteado.");
        }
        Palavra[] palavrasEscolhidas = new Palavra[quantidadePalavrasSorteadas];
        for(int i = 0 ; i <= palavrasEscolhidas.length-1; i++) {
        	palavrasEscolhidas[i] = palavrasTema[random.nextInt(palavrasTema.length-1)];
        }
		return Rodada.criar(this.getRodadaRepository().getProximoId(), palavrasEscolhidas, jogador);		
	}

}
