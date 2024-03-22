package br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;

public class Rodada extends ObjetoDominioImpl {


	private static int maxPalavras = 3;
	private static int maxErros = 10;
	private static int pontosQuandoDescobreTodasAsPalavras = 100;
	private static int pontosPorLetraEncoberta = 15;

	private Jogador jogador;
	//precisa da implementação de BonecoFactory private static BonecoFactory bonecoFactory;
	private Boneco boneco;
	private Item[] itens;
	private List<Letra> letrasErradas;

	public Rodada(long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public static int getMaxPalavras() {
		return maxPalavras;
	}

	public static void setMaxPalavras(int maxPalavras) {
		Rodada.maxPalavras = maxPalavras;
	}

	public static int getMaxErros() {
		return maxErros;
	}

	public static void setMaxErros(int maxErros) {
		Rodada.maxErros = maxErros;
	}

	public static int getPontosQuandoDescobreTodasAsPalavras() {
		return pontosQuandoDescobreTodasAsPalavras;
	}

	public static void setPontosQuandoDescobreTodasAsPalavras(int pontosQuandoDescobreTodasAsPalavras) {
		Rodada.pontosQuandoDescobreTodasAsPalavras = pontosQuandoDescobreTodasAsPalavras;
	}

	public static int getPontosPorLetraEncoberta() {
		return pontosPorLetraEncoberta;
	}

	public static void setPontosPorLetraEncoberta(int pontosPorLetraEncoberta) {
		Rodada.pontosPorLetraEncoberta = pontosPorLetraEncoberta;
	}
	public List<Letra> getLetrasErradas(){
		ArrayList<Letra> temp = new ArrayList<>(this.letrasErradas);
		return temp;
	}
/*
	public static void setBonecoFactory(BonecoFactory bonecoFactory) {
		Rodada.bonecoFactory = bonecoFactory;
	}

	public static BonecoFactory getBonecoFactory() {
		return bonecoFactory;
	}
	*/
	public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
		if(bonecoFactory==null) {
			throw new RuntimeException("O bonecoFactory deve ser inicializado antes de criar a Rodada.");
		}
		return new Rodada(id, palavras, jogador);
	}

	public static Rodada reconstruir(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
		if(bonecoFactory==null) {
			throw new RuntimeException("O bonecoFactory deve ser inicializado antes de reconstruir a Rodada.");
		}
		return new Rodada(id, itens, erradas, jogador);
	}

	private Rodada(long id, Palavra[] palavras, Jogador jogador) {
		super(id);
		this.itens = new Item[palavras.length];
		for(int i = 0; i < palavras.length; i++) {
			this.itens[i] = Item.criar(i,palavras[i]);
		}
		Tema temaTeste = this.itens[0].getPalavra().getTema();
		for(int i = 0; i<= this.itens.length-1; i++) {
			if(itens[i].getPalavra().getTema() != temaTeste) {
				throw new RuntimeException("Todas as palavras devem ter o mesmo tema");
			}
		}
		this.jogador=jogador;
		this.letrasErradas = new ArrayList<Letra>();
		//this.boneco = bonecoFactory.getBoneco();
	}
	private Rodada(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
		super(id);
		this.itens = itens;
		Tema temaTeste = this.itens[0].getPalavra().getTema();
		for(int i = 0; i<= this.itens.length-1; i++) {
			if(itens[i].getPalavra().getTema() != temaTeste) {
				throw new RuntimeException("Todas as palavras devem ter o mesmo tema");
			}
		}
		this.letrasErradas = Arrays.asList(erradas);
		this.jogador=jogador;
		//this.boneco = bonecoFactory.getBoneco();
	}
	public Jogador getJogador() {
		return this.jogador;
	}

	public Tema getTema() {
		if (this.getNumeroPalavras() == 0) {
			throw new RuntimeException("A rodada deve ter pelo menos um item");
		}
		return this.itens[0].getPalavra().getTema();
	}
	
	public int getNumeroPalavras() {
		return this.itens.length;
	}
	//Resgata o número de palavras da rodada a partir de seus itens
	public Palavra[] getPalavras() {
		ArrayList<Palavra>  palavrasDaRoada = new ArrayList<>();
		int j = 0;
		for(int i = 0; i<= this.itens.length-1; i++) {
			palavrasDaRoada.add(itens[i].getPalavra());
			j++;
		}
		return palavrasDaRoada.toArray(new Palavra[j]);
	}
	
	public int getNumPalavras() {
		return this.itens.length;
	}
	//
	public void tentar(char codigo) {
		if(this.encerrou() == true) {
		//	this.jogador.setPontuacao(this.jogador.getPontuacao()+this.calcularPontos());
			throw new RuntimeException("Não pode tentar resolver a rodada depois que o jogo encerrou");
		}
		if (this.getNumPalavras() == 0) {
			throw new RuntimeException("A rodada deve ter ter pelo menos um item para você tentar encontrar uma resposta");
		}
		boolean encontrou = false;
		for(int i = 0; i <= this.itens.length-1; i++) {
			if(itens[i].tentar(codigo) == true && encontrou == false) {
				encontrou = true;
			}
		}
		//Se a tentativa foi errada, pega um item qualquer e instancie uma letra para ser adicionada em letrasErradas
		if(encontrou == false){				
		//	this.letrasErradas.add(this.itens[0].getPalavra().getLetraFactory().getLetra(codigo));
		}
	}
	
	public boolean encerrou() {
		return this.arriscou() || this.descobriu() || (this.getQuantidadeDeTentativasRestantes()==0);
	}
	
	private int getQuantidadeDeTentativasRestantes() {

		return maxErros - this.getQuantidadeDeErros();
	}
	public int getQuantidadeDeErros() {
		return this.getLetrasErradas().size();
	}

	public boolean arriscou() {
	    for (int i = 0; i < this.itens.length; i++) {
	        Item item = this.itens[i];
	        if (item.arriscou() == false) {
	            return false;
	        }
	    }
	    return true;
	}
	public boolean descobriu() {
		for(int i = 0; i <= this.itens.length-1; i++) {
			if(itens[i].descobriu() == false ) {
				return false;
			}
		}
		return true;
	}
	
	public void exibirItens(Object contexto) {
		for(int i = 0; i < this.itens.length; i++) {
			this.itens[i].exibir(contexto);
		}
	}
	public void exibirBoneco(Object contexto) {
		//this.boneco.exibir(contexto, this.letrasErradas.size());
	}
	
	public void exibirPalavras(Object contexto) {
		for(int i = 0; i <= this.itens.length-1; i++) {
			itens[i].getPalavra().exibir(contexto, itens[i].getPosicoesDescobertas());
			System.out.println();
		}
	}
	
	public void exibirLetrasErradas(Object contexto) {
		String temp = "";
		System.out.print("Letras erradas: ");
		for(int i = 0; i <= this.letrasErradas.size()-1; i++) {
			temp += this.letrasErradas.get(i);
		}
		System.out.print(temp);
	}
	public Letra[] getTentativas() {
		Letra[] tentativas = new Letra[this.getCertas().length + this.getLetrasErradas().size()];
		//Adicionando as letras certas
		for(int i = 0; i<= this.getCertas().length-1; i++) {
				tentativas[i] = this.getCertas()[i];
		}
		//Adicionando as letras erradas	
		for(int i = this.getCertas().length; i <= tentativas.length-1; i++) {
			tentativas[i] = this.getLetrasErradas().get(i);
		}
		return tentativas;
	}
	
	public Letra[] getCertas() {
		ArrayList<Letra> acertos = new ArrayList<Letra>();
		for(int i = 0; i<= this.itens.length-1; i++) {
			for(int j = 0; j <= this.itens[i].getLetrasDescobertas().length-1; i ++) {
				acertos.add(this.itens[i].getLetrasDescobertas()[j]);
			}
		}
		return acertos.toArray(new Letra[acertos.size()]);
	}
	public int calcularPontos() {
		if(this.descobriu() == true) {
			int pontosTotaisPorLetrasEncobertas = 0;
			for(Item item : this.itens) {
				pontosTotaisPorLetrasEncobertas += item.calcularPontosLetrasEncobertas(pontosPorLetraEncoberta);
			}
			return pontosQuandoDescobreTodasAsPalavras + pontosTotaisPorLetrasEncobertas;
		}else {
			return 0;
		}
	}
	
	public int getQuantidadeDeAcertos() {
		return this.getCertas().length;
	}
	
	public int getQtdeTentativas() {
		return this.getQuantidadeDeAcertos() + this.getQuantidadeDeErros();
	}
	
}


