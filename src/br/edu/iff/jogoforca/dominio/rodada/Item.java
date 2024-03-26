package br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;
import java.util.List;

import  br.edu.iff.bancodepalavras.dominio.letra.Letra;
import  br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import  br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl{
		private boolean[] posicoesDescobertas;
		private String palavraArriscada = null;
		public Palavra palavra;

		
		private Item(long id, Palavra palavra){
			super(id);
			this.palavra = palavra;
			this.posicoesDescobertas = new boolean[palavra.getTamanho()];
		}
		
		private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada){
			super(id);
			this.palavra = palavra;
			this.palavraArriscada = palavraArriscada;
			for (int i = 0; i < posicoesDescobertas.length; i++ )
			{
				this.posicoesDescobertas[posicoesDescobertas[i]] = true;
			}
		}
	
		static Item criar(long id, Palavra palavra){
			return new Item(id, palavra);
		}
		
		public static Item reconstituir(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada){
			return new Item(id, palavra, posicoesDescobertas, palavraArriscada);
		}

		public Palavra getPalavra(){
			return this.palavra;
		}
		
		 public Letra[] getLetrasDescobertas(){
		       List<Letra> listaLetras = new ArrayList<>();
	
		        for (int i = 0; i < posicoesDescobertas.length; i++) {
		            if(this.posicoesDescobertas[i]){
		                listaLetras.add(palavra.getLetra(i));
		            }
		        }
		        return  listaLetras.toArray(new Letra[listaLetras.size()] );
	
		 }

		 public Letra[] getLetrasEncobertas(){
			 List<Letra> listaLetras = new ArrayList<>();
			 
			 for (int i = 0; i < posicoesDescobertas.length; i++) {
				 if(!posicoesDescobertas[i]){
					 listaLetras.add(palavra.getLetra(i));
		            }
		        }
		        return listaLetras.toArray(new Letra[listaLetras.size()]);
		    }
		 
		 public int quantidadeLetrasEncobertas(){
			 int qtd = 0;
			 for (int i = 0; i < posicoesDescobertas.length; i++) {
				 if(!posicoesDescobertas[i]){
					 qtd++;
		            }
		        }
			 return qtd;
		 }
		 
		 
		 public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta){
		      return this.quantidadeLetrasEncobertas() * valorPorLetraEncoberta;
		 }
		 
		 
		 public boolean descobriu() {
		     return acertou() || quantidadeLetrasEncobertas() == 0;
		 }
	
		public void exibir(Object contexto){
			 palavra.exibir(contexto, this.posicoesDescobertas);
		 }
		 
	    boolean tentar(char codigo){
	    	
	    	//a funcao de tentar de palavra retorn um array de posicoes acertadas, se vazio retorna false
	        if (palavra.tentar(codigo).length == 0) {
	        	return false;
	        } 
	        
	        // ao verificar que palavra.tentar tem correspondencia, define a posicao acertada como true no vetor posicoesDescobertas;
	        int[] posicoes = palavra.tentar(codigo);
	        for (int i = 0; i < posicoes.length; i++) {
	            this.posicoesDescobertas[posicoes[i]] = true;
	        }

	        return true;
	    }
	    
	    void arriscar(String palavra){
	      	this.palavraArriscada = palavra;
	    }

	    public String getPalavraArriscada(){
	        return palavraArriscada;
	    }
	
	    public boolean arriscou(){
	        return palavraArriscada != null;
	    }
	
	    public boolean acertou(){
	    	
	        return palavra.toString().equals(palavraArriscada);
	    }
}
