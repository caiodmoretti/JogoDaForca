package br.edu.iff.bancodepalavras.dominio.palavra;
//importações

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import java.util.ArrayList;

import java.util.List;

public class Palavra extends ObjetoDominioImpl{
		
		private Tema tema;
		private Letra[] letras;
		private Letra letraEncoberta;
		public static LetraFactory factory;
	
		
		
		private Palavra(long id, String palavra, Tema tema) {
	        super(id);
	        if (factory != null){
	            this.tema = tema;
	            this.letraEncoberta = factory.getLetraEncoberta();
	
	            letras = new Letra[palavra.length()];
	            for (int i = 0; i < palavra.length(); i++) {
	                letras[i] = factory.getLetra(palavra.charAt(i)) ;
	            }
	
	        }else{
	            throw new IllegalStateException("LetraFactory precisa ser iniciado para instanciar uma Palavra");
	        }
	    }
			
		public static Palavra criar(long id, String palavra, Tema tema){
			return new Palavra(id, palavra, tema);
			
		}
		
		public Palavra reconstituir(long id, String palavra, Tema tema){
			return new Palavra(id, palavra, tema);
		}
			
		public static void setLetraFactory(LetraFactory letraFactory){
			Palavra.factory = letraFactory;
		}	
		public static LetraFactory getLetraFactory(){
			return Palavra.factory;
		}
		public Letra[] getLetras(){
			return this.letras;
		}
		
		public Letra getLetra(int posicao){
			return this.letras[posicao];
		}
		
		public void exibir(Object contexto){
			for(int i = 0; i<= this.letras.length-1;i++){
				this.letras[i].exibir(contexto);
			}
		}
		
	
	    public void exibir(Object contexto, boolean[] posicoes){
	        for (int i = 0; i < posicoes.length; i++) {
	            if(posicoes[i]==true){
	                letras[i].exibir(contexto);
	            }else{
	                letraEncoberta.exibir(contexto);
	            }
	        }
	    }
	    
	    
	    public int[] tentar(char codigo){
	        List<Integer> listaPosicoes = new ArrayList<>();
	        for (int i = 0; i < this.letras.length; i++) {
	            if (this.letras[i].getCodigo() == codigo){
	                listaPosicoes.add(i);
	            	}
	        	}      
	        // criar um array de inteiros com o mesmo tamanho da lista
	        int[] arrayPosicoes = new int[listaPosicoes.size()];
	
	        // preencher o array com os elementos da lista
	        for (int i = 0; i < listaPosicoes.size(); i++) {
	            arrayPosicoes[i] = listaPosicoes.get(i);
	        }
	        	return  arrayPosicoes;    
	    }
	        
	    public Tema getTema(){
	    	return this.tema;
	    }
	
	    public boolean comparar(String palavra){
			if (palavra == this.toString())
				return true;
			else return false;
		}
	    
	    
	    public int getTamanho(){
	        return letras.length;
	    }
	    
	    
	    
	    public String toString(){
			String palavraToString = "";
			for (int i = 0; i<= this.letras.length-1;i++){	 
				palavraToString = palavraToString + this.letras[i];
				i++;
			}			
			return palavraToString;
		}

}