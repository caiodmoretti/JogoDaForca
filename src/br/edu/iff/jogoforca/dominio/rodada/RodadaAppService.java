package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class RodadaAppService {
	

	private RodadaRepository rodadaRepository;
	private JogadorRepository jogadorRepository;
	private RodadaFactory rodadaFactory;
	private static RodadaAppService soleInstance;
	
	
	private RodadaAppService(RodadaRepository rodadaRepository, JogadorRepository jogadorRepository, RodadaFactory rodadaFactory) {
		if(rodadaFactory == null || jogadorRepository == null || rodadaFactory == null) {
			throw new RuntimeException("Nenhum parâmetro pode ser nulo.");
		}
		this.rodadaRepository = rodadaRepository;
		this.jogadorRepository = jogadorRepository;
		this.rodadaFactory = rodadaFactory;
	}

	public static void createSoleInstance(RodadaRepository rodadaRepository, JogadorRepository jogadorRepository, RodadaFactory rodadaFactory) {
		if(soleInstance==null) {
			soleInstance = new RodadaAppService(rodadaRepository, jogadorRepository, rodadaFactory);
		}
	}
	
	public static RodadaAppService getSoleInstance() {
		if(soleInstance==null) {
			throw new RuntimeException("Precisa chamar o createSoleInstance primeiro.");
		}
		return soleInstance;
	}
	//Foram implementas 3 formas de criar uma nova rodada: por idJogador, nomeJogador,  e Jogador
	public Rodada novaRodada(long idJogador) {
		if(this.jogadorRepository.getPorId(idJogador) == null) {
			throw new RuntimeException("O id informado tem que corresponder a um Jogador pré-existente no repositório de Jogador.");
		}
		return this.rodadaFactory.getRodada(this.jogadorRepository.getPorId(idJogador));
	}
	public Rodada novaRodada(String nomeJogador)  throws JogadorNaoEncontradoException {
		if(this.jogadorRepository.getPorNome(nomeJogador) == null) {
			throw new JogadorNaoEncontradoException(nomeJogador);
		}
		return this.rodadaFactory.getRodada(this.jogadorRepository.getPorNome(nomeJogador)[0]);
	}
	public Rodada novaRodada(Jogador jogador) {
		return this.rodadaFactory.getRodada(jogador);
	}
	
	public boolean salvarRodada(Rodada rodada) {
		try {
			this.rodadaRepository.inserir(rodada);
			return true;
		}catch(RepositoryException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
