package br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl {
	
	private  boolean[] posicoesDescobertas;
	private String palavraArriscada = null;
	private Palavra palavra;
	private Object posicoes;


	//Criar e reconstruir precisam da implementação da camada de persistência
	static Item criar(long id, Palavra palavra) {
		return null;
		
	}
	public static Item reconstruir(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
		return null;
		
	}
	
	private Item(long id, Palavra palavra) {
		super(id);
		this.setPalavra(palavra);
	}
	private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
		super(id);
		this.setPalavra(palavra);
		this.setPosicoesDescobertas(posicoesDescobertas); 
		this.setPalavraArriscada(palavraArriscada);
	}
	
	private void setPalavra(Palavra palavra) {
		if(palavra == null) {
			throw new NullPointerException("A palavra não pode ser nula");
		}
		this.palavra = palavra;
	}
	
	private void setPosicoesDescobertas(int[] posicoesDescobertasInt) {
		if(posicoesDescobertasInt == null) {
			throw new NullPointerException("O vetor com as posições descobertas é nulo");
		}
		for(int i = 0; i <= posicoesDescobertasInt.length-1 ; i++) {
			this.posicoesDescobertas[posicoesDescobertasInt[i]] = true;
		}
	}
		
	private void setPalavraArriscada(String palavraArriscada) {
		if(palavraArriscada == null) {
			throw new NullPointerException("A palavra arriscada não pode ser nula");
		}
		this.palavraArriscada = palavraArriscada;
		
	}
	public Palavra getPalavra() {
		return this.palavra;
	}
	public Letra[] getLetrasDescobertas() {
		ArrayList<Letra> letrasDescobertasLista = new ArrayList<Letra>();
		for(int i = 0; i <= this.palavra.getTamanho(); i++) {
			if(this.posicoesDescobertas[i] == true) {
				letrasDescobertasLista.add(this.palavra.getLetra(i));
			}
		}
		return letrasDescobertasLista.toArray(new Letra[letrasDescobertasLista.size()]);
	}
	
	public int getLetrasEncobertas() {
		int quantidade = 0;
		for(int i = 0; i <= this.palavra.getTamanho(); i++) {
			if(this.posicoesDescobertas[i] == false) {
				quantidade++;
			}
		}
		return quantidade;
	}
	
	public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
		return this.getLetrasEncobertas() * valorPorLetraEncoberta;
	}
	
	public boolean descobriu() {
		return this.acertou() || (this.getLetrasEncobertas() == 0);
	}
	
	public boolean acertou() {
		return this.palavra.comparar(this.palavraArriscada);
	}	
	public void exibir(Object contexto) {
		this.palavra.exibir(palavra, this.posicoesDescobertas);
		
	}
	boolean tentar(char codigo) {
		int[] posicoes = palavra.tentar(codigo);
        for (int i = 0; i <= palavra.tentar(codigo).length-1; i++ ) {
        	if(posicoes[i] == 1) {
                this.posicoesDescobertas[i] = true;      		
        	}

        }
		if(posicoes.length > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean arriscou() {
		if(this.palavraArriscada == null) {
			return false;
		}
		else {
			return true;
		}
	}
	public boolean[] getPosicoesDescobertas() {
		boolean[] temp = new boolean[posicoesDescobertas.length];
		System.arraycopy(posicoesDescobertas, 0, temp, 0, posicoesDescobertas.length);
		return temp;
	}

}

