package br.edu.iff.bancodepalavras.dominio.palavra;

import java.util.Arrays;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl {

	private Tema tema;
	private static LetraFactory letraFactory;
	private Letra encoberta;
	private Letra[] palavra;
	private int tamanhoDaPalavra;

	private Palavra(long id,
			String palavra, Tema tema) {
		super(id);
		this.setTema(tema);
		this.tamanhoDaPalavra = palavra.length();
		this.setLetras(palavra);
	}

	// Criar e reconstruir precisam da implementaÃ¯Â¿Â½Ã¯Â¿Â½o da camada de per
	// istÃ¯Â¿Â½ncia
	public static Palavra criar(long id, String palavra, Tema tema) {
		if (letraFactory == null) {
			throw new RuntimeException("Deve inicializar o letraFactory primeiro");
		}
		return new Palavra(id, palavra, tema);
	}

	public static Palavra reconstruir(long id, String palavra, Tema tema) {
		if (letraFactory == null) {
			throw new RuntimeException("Deve inicializar o letraFactory primeiro");
		}
		return new Palavra(id, palavra, tema);
	}

	private void setTema(Tema tema) {
		if (tema == null) {
			throw new NullPointerException("O Tema não pode ser nulo.");
		}
		this.tema = tema;

	}

	private void setLetras(String palavra) {
		if (palavra == null || palavra.isBlank()) {
			throw new NullPointerException("A palavra não pode ser nula nem vazia.");
		}
		this.palavra = new Letra[this.getTamanho()];
		for (int i = 0; i <= this.tamanhoDaPalavra - 1; i++) {
				this.palavra[i] = letraFactory.getLetra(palavra.charAt(i));
		}

	}

	public Letra[] getLetras() {
		Letra[] letrasTemp = Arrays.copyOf(this.palavra, this.palavra.length);
		return letrasTemp;
	}

	public Letra getLetra(int posicao) {
		if (posicao == 32) {
			throw new NullPointerException("A posição da letra palavra não pode vazia.");
		}
		return this.palavra[posicao];
	}

	public void exibir(Object contexto, boolean[] posicoes) {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra a ser exibida não pode ser nula.");
		}
		for (int i = 0; i < this.getTamanho(); i++) {
			if (posicoes[i] == true) {
				this.palavra[i].exibir(contexto);
			} else {
				this.encoberta.exibir(contexto);
			}
		}
	}

	public int[] tentar(char codigo) {
		if (this.palavra == null) {
			throw new RuntimeException("Para tentar, a palavra deve ser inicializada.");
		}
		if (codigo == 32) {
			throw new NullPointerException("O caracter não pode ser vazio.");
		}
		int[] posicoesEncontrasDaLetra = new int[this.tamanhoDaPalavra];
		for (int i = 0; i <= this.getTamanho(); i++) {
			if (this.palavra[i].getCodigo() == codigo) {
				posicoesEncontrasDaLetra[i] = 1;
			}
		}
		return posicoesEncontrasDaLetra;
	}

	public Tema getTema() {
		return this.tema;
	}

	public boolean comparar(String palavra) {
		if (this.palavra == null) {
			throw new RuntimeException("Para comparar, a palavra deve ser inicializada.");
		}
		if (palavra == null || this.getTamanho() != palavra.length()) {
			return false;
		}
		for (int i = 0; i <= this.tamanhoDaPalavra; i++) {
			if (this.palavra[i].getCodigo() != palavra.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	public int getTamanho() {
		return this.tamanhoDaPalavra;
	}

	@Override
	public String toString() {
		if (this.palavra == null) {
			throw new RuntimeException("Para imprimir, a palavra deve ser inicializada.");
		}
		String palavraImpressao = null;
		for (int i = 0; i <= this.getTamanho(); i++) {
			palavraImpressao = palavraImpressao + this.palavra[i];
		}
		return palavraImpressao;
	}

}
