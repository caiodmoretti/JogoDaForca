package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;

public interface RodadaFactory {
	
	public abstract Rodada getRodada(Jogador jogador);

}
