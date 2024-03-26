package br.edu.iff.jogoforca.dominio.rodada;

 


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;


public class Rodada extends ObjetoDominioImpl{

		private static int maxPalavras = 3;
		private static int maxErros = 10;
		private static int pontosQuandoDescobreTodasAsPalavras = 100;
		private static int pontosPorLetraEncoberta = 15;
			
		public static BonecoFactory bonecoFactory;
		private Item[] itens;
		private Letra[] letrasErradas;
		private Jogador jogador;
	    private Boneco boneco;
	
		  private Rodada(long id, Palavra[] palavras, Jogador jogador) {
		        super(id);
		     
		        if(bonecoFactory != null) {
		            Item[] vetorItens = new Item[palavras.length];
	
		            
		            //tratamento de item
		            for (int i = 0; i < palavras.length; i++) {
		                if(palavras[i].getTema().equals(palavras[0].getTema())){
		                    vetorItens[i] = Item.criar(new Random().nextLong(), palavras[i]);
		                }else{
		                    throw new IllegalArgumentException("O tema das palavras precisam ser iguais");
		                }
		            }
	
		            this.itens = vetorItens;
		            this.jogador = jogador;
		            this.boneco = bonecoFactory.getBoneco();
		            this.letrasErradas = new Letra[Rodada.maxErros];
		        }
		        else{
		            throw new IllegalStateException("É necessário inicializar BonecoFactory para instanciar Rodada");
		        }
		    }
	
		    private Rodada(long id, Jogador jogador, Boneco boneco, Item[] itens, Letra[] letrasErradas) {
		        super(id);
		        if(bonecoFactory != null) {
		            this.jogador = jogador;
		            this.boneco = boneco;
		            this.itens = itens;
		            this.letrasErradas = letrasErradas;
		        }else{
		            throw new IllegalStateException("É necessário inicializar BonecoFactory para instanciar Rodada");
		        }
		    }
		
		public static Rodada Criar(long id, Palavra[] palavras, Jogador jogador){
			return new Rodada(id, palavras, jogador);
		}
		
		public static Rodada Reconstituir(long id, Item[] itens, Letra[] letrasErradas, Jogador jogador, Boneco boneco){
			return new Rodada(id, jogador, boneco, itens, letrasErradas);
		}

		public static int getMaxPalavras(){
			return maxPalavras;
		}
		
		public static void setMaxPalavras(int max){
			Rodada.maxPalavras = max;
		}
		
		public static int getMaxErros(){
			return maxErros;
		}
		
		public static void setMaxErros(int max){
			Rodada.maxErros = max;
		}
		
		public static int getPontosQuandoDescobreTodasAsPalavras(){
			return pontosQuandoDescobreTodasAsPalavras;
		}
		
		public static void setPontosQuandoDescobreTodasAsPalavras(int pontos){
			Rodada.pontosQuandoDescobreTodasAsPalavras = pontos;
		}
		
		public static int getPontosPorLetraEncoberta(){
			return pontosPorLetraEncoberta;
		}
		
		public static void setPontosPorLetraEncoberta(int pontos){
			Rodada.pontosPorLetraEncoberta = pontos;
		}
		
		public static void setBonecoFactory(BonecoFactory bonecoFactory){
			Rodada.bonecoFactory = bonecoFactory;
		}
		
		public static BonecoFactory getBonecoFactory(){
			return bonecoFactory;
		}
		
		public Jogador getJogador(){
			return this.jogador;
		}
		
		public Tema getTema(){
			// como o tema é da palavra, devemos puxar o array de itens, pegar a palavra da posição que 
			// temos certeza que estará preenchida, e puxar o getPalavra.getTema
			return this.itens[0].getPalavra().getTema();
		}
		
		public Palavra[] getPalavras(){
			Palavra[] palavras = new Palavra[itens.length];
			for (int i = 0; i < this.itens.length; i++)
			{
				palavras[i] = itens[i].getPalavra();
			}
			return palavras;
		}
		
		
		
		public int getNumPalavras(){
	        return itens.length;
	    }
	
	    public void tentar(char codigo)
	    {
	        if(!this.encerrou())
	        {
	            boolean acertou = false;
	            for (Item item : itens) 
	            {
	                if (item.tentar(codigo)) acertou = true;
	            }
	
	            if (!acertou)
	            {
	                for (int i = 0; i < letrasErradas.length; i++)
	                {
	                    if (letrasErradas[i] == null)
	                    {
	                        letrasErradas[i] = Palavra.getLetraFactory().getLetra(codigo);
	                        break;
	                    }
	                }
	            }
	        }
	    }
	
	    public void arriscar(String[] palavras){
	        if(!this.encerrou())
	        {
	            for (int i = 0; i < itens.length; i++) 
	            {
	                itens[i].arriscar(palavras[i]);
	            }
	        }
	    }
	
	    public void exibirItens(Object contexto) {
	        for (int i = 0; i < itens.length; i++) {
	            itens[i].exibir(contexto);
	            System.out.println();
	        }
	    }
	
	    public void exibirBoneco(Object contexto){
	        boneco.exibir(contexto, this.getQtdeErros());
	    }
	
	    public void exibirLetrasErradas(Object contexto){
	    	for (int i = 0; i < this.letrasErradas.length; i++) {
	    	    if (this.letrasErradas[i] != null) {
	    	        this.letrasErradas[i].exibir(contexto);
	    	    }
	    	}

	    }
	
	    public Letra[] getErradas() {
	        List<Letra> letrasErradasLista = new ArrayList<>();
	        for (int i = 0; i < letrasErradas.length; i++) {
	            Letra letra = letrasErradas[i];
	            if (letra != null) {
	                letrasErradasLista.add(letra);
	            }
	        }
	        return letrasErradasLista.toArray(new Letra[0]);
	    }

	
	    public Letra[] getCertas(){
	        Set<Letra> certas = new HashSet<>();
	
	        for (Item item : itens) {
	            for (Letra letraDescoberta : item.getLetrasDescobertas()) 
	            {
	                certas.add(letraDescoberta);
	            }
	        }
	
	        return certas.toArray(new Letra[certas.size()]);
	    }
	
	    public Letra[] getTentativas(){
	    	List<Letra> tentativas = new ArrayList<>();

	    	//adicionando letras erradas
	    	for (int i = 0; i < this.getErradas().length; i++) {
	    	    Letra letra = this.getErradas()[i];
	    	    if (letra != null) {
	    	        tentativas.add(letra);
	    	    }
	    	}
	    	//adicionando letras certas
	    	for (int i = 0; i < this.getCertas().length; i++) {
	    	    Letra letra = this.getCertas()[i];
	    	    if (letra != null) {
	    	        tentativas.add(letra);
	    	    }
	    	}
	    	return tentativas.toArray(new Letra[tentativas.size()]);

	    }
	
	    public int calcularPontos(){
	        int pontos = 0;
	        if (this.descobriu() == true){
	            pontos = 100;
	            for (int i = 0; i < itens.length; i++) {
	                Item item = itens[i];
	                pontos += item.getLetrasEncobertas().length * 15;
	            }	            
	
	        }
	        return pontos;
	    }
	
	    public boolean encerrou(){
	        if(this.arriscou() || this.descobriu() || this.getErradas().length >= maxErros)
	        {
	            return true;
	        }else
	        {
	            return false;
	        }
	    }
	
	    public boolean descobriu(){
	    	boolean descobriu = true;
	    	for (int i = 0; i < itens.length; i++) {
	    	    Item item = itens[i];
	    	    if (item.descobriu()!= true) {
	    	        descobriu = false;
	    	        break; // Se algum item não foi descoberto, não é necessário continuar o loop
	    	    }
	    	}

	        return descobriu;
	    }
	
	    public boolean arriscou(){
	        for (int i = 0; i < itens.length; i++){
	            if (itens[i].arriscou()){
	                return true;
	            }
	        }
	        return false;
	    }
	
	    public int getQtdeTentativasRestantes(){
	        return maxErros - getQtdeErros();
	    }
	
	    public int getQtdeErros(){
	        return this.getErradas().length;
	    }
	
	    public int getQtdeAcertos(){
	        return this.getCertas().length;
	    }
	
	    public int getQtdeTentativas() {
	        return this.getTentativas().length;
	    }
}

	
	
	
	
	

