package br.edu.iff.jogoforca.dominio.rodada.sorteio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
			throw new RuntimeException("� necess�rio chamar o m�todo createSoleInstance primeiro.");
		}
		return soleInstance;
	}
		private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
			super(repository, temaRepository, palavraRepository);
		}
		
	public Rodada getRodada(Jogador jogador) {
		Random random = new Random();
		Tema temaEscolhido = this.getTemaRepository().getTodos()[random.nextInt(this.getTemaRepository().getTodos().length-1)];
		int quantidadePalavrasSorteadas =  random.nextInt(3) + 1; // nextInt(3) gera n�meros entre 0 (inclusive) e 3 (exclusivo). 
		Palavra[] palavrasTema = this.getPalavraRepository().getPorTema(temaEscolhido);
        if(palavrasTema.length < quantidadePalavrasSorteadas) {
        	throw new RuntimeException("N�o h� palavras sufucientes no tema sorteado.");
        }
        //Sortear palavras aleat�rias e garantir que novas palavras n�o seja repetidas       
        Palavra[] palavrasEscolhidas = new Palavra[quantidadePalavrasSorteadas];
        Set<Palavra> conjuntoPalavras = new HashSet<>(Arrays.asList(palavrasEscolhidas));
		List<Palavra> listaTemporaria = new ArrayList<>(conjuntoPalavras);
        Collections.shuffle(listaTemporaria);
        for (int i = 0; i <= palavrasEscolhidas.length-1 && i < listaTemporaria.size() ; i++) {
            palavrasEscolhidas[i] = listaTemporaria.get(i);
        }
		return Rodada.criar(this.getRodadaRepository().getProximoId(), palavrasEscolhidas, jogador);		
	}

}
