package Testes;

import java.util.Scanner;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.texto.LetraTextoFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.palavra.emmemoria.MemoriaPalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.ElementoGraficoFactory;
import br.edu.iff.jogoforca.RepositoryFactory;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.jogoforca.emmemoria.MemoriaRepositoryFactory;
import br.edu.iff.jogoforca.texto.ElementoGraficoTextoFactory;
import br.edu.iff.repository.RepositoryException;

public class TesteGenerico {
    static Scanner scanner = new Scanner(System.in);
    static Aplicacao aplicacao = Aplicacao.getSoleInstance();

    public static void main(String[] args) {
        aplicacao.configurar();

        PalavraAppService palavraAppService = PalavraAppService.getSoleInstance();

        TemaRepository temaRepository = aplicacao.getRepositoryFactory().getTemaRepository();
        TemaFactory temaFactory = aplicacao.getTemaFactory();

        try {
            temaRepository.inserir(temaFactory.getTema("Carro"));
            temaRepository.inserir(temaFactory.getTema("Nome"));
            temaRepository.inserir(temaFactory.getTema("Casa"));
            temaRepository.inserir(temaFactory.getTema("Jogos"));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }	

        palavraAppService.novaPalavra("fusca",(long) 1);
        palavraAppService.novaPalavra("palio", (long) 1);
        palavraAppService.novaPalavra("corsa", (long) 1);
      //  palavraAppService.novaPalavra("fiat", 1);

        palavraAppService.novaPalavra("felipe", (long) 2);
        palavraAppService.novaPalavra("ana", (long) 2);
        palavraAppService.novaPalavra("jorge", (long) 2);
        //palavraAppService.novaPalavra("jose", 2);

        
        palavraAppService.novaPalavra("sofa", (long) 3);
        palavraAppService.novaPalavra("fogao", (long) 3);
        palavraAppService.novaPalavra("armario", (long) 3);
        //palavraAppService.novaPalavra("cama", 3);

        palavraAppService.novaPalavra("fifa", (long) 4);
        palavraAppService.novaPalavra("mario", (long) 4);
        palavraAppService.novaPalavra("sonic", (long) 4);
        //palavraAppService.novaPalavra("pes", 4);


        System.out.println("Digite seu nome: ");
        String nomeJogador = scanner.next();

        Jogador jogador = aplicacao.getJogadorFactory().getJogador(nomeJogador);
        System.out.println(jogador.getNome());
        try {
            aplicacao.getRepositoryFactory().getJogadorRepository().inserir(jogador);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
        System.out.println("!");
        jogarRodada(jogador);
        System.out.println("!");
    }
    
    private static void jogarRodada(Jogador jogador) {
        RodadaAppService rodadaAppService = RodadaAppService.getSoleInstance();

        Rodada rodada = rodadaAppService.novaRodada(jogador);
        System.out.println("Tema das palavras: " + rodada.getTema().getNome());

        do {
            System.out.println("Tentativas anteriores: ");
            for (Letra tentativa : rodada.getTentativas()) {
                tentativa.exibir(null);
                System.out.print(" ");
            }
            System.out.println();

            System.out.println("Palavras:");
            rodada.exibirItens(null);
            System.out.println();
            System.out.println("Corpo: ");
            rodada.exibirBoneco(null);
            System.out.println();


            System.out.println("(1) Tentar letra");
            System.out.println("(2) Arriscar");
            String escolha = scanner.next();
            switch (escolha){
                case "1":
                    System.out.print("Digite a letra: ");
                    rodada.tentar(scanner.next().charAt(0));
                    break;
                case "2":
                    String[] palavrasArriscadas = new String[rodada.getNumPalavras()];
                    for (int i = 0; i < palavrasArriscadas.length; i++) {
                        System.out.print("Chute a palavra " + (i + 1)  + " :");
                        palavrasArriscadas[i] = scanner.next();
                    }
                    rodada.arriscar(palavrasArriscadas);
                    break;
            }

            if (rodada.descobriu()) System.out.println("Descobriu");
            System.out.println(rodada.calcularPontos());


        }while (!rodada.encerrou());
    }
}