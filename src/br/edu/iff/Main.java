package br.edu.iff;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Aplicacao aplicacao = Aplicacao.getSoleInstance();

    public static void main(String[] args) {
        aplicacao.configurar();

        PalavraAppService palavraAppService = PalavraAppService.getSoleInstance();

        TemaRepository temaRepository = aplicacao.getRepositoryFactory().getTemaRepository();
        TemaFactory temaFactory = aplicacao.getTemaFactory();

        try {
            temaRepository.inserir(temaFactory.getTema("Países"));
            temaRepository.inserir(temaFactory.getTema("Nomes"));
            temaRepository.inserir(temaFactory.getTema("Cores"));
            temaRepository.inserir(temaFactory.getTema("Comida"));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }	

        palavraAppService.novaPalavra("brasil", 1);
        palavraAppService.novaPalavra("canada", 1);
        palavraAppService.novaPalavra("chile", 1);
      

        palavraAppService.novaPalavra("maria", 2);
        palavraAppService.novaPalavra("ruan", 2);
        palavraAppService.novaPalavra("caio", 2);
        

        
        palavraAppService.novaPalavra("amarelo", 3);
        palavraAppService.novaPalavra("vermelho", 3);
        palavraAppService.novaPalavra("lilas", 3);
        

        palavraAppService.novaPalavra("pizza", 4);
        palavraAppService.novaPalavra("hamburguer", 4);
        palavraAppService.novaPalavra("sushi", 4);
        
        System.out.println("##############################################");
        System.out.println("#                                            #");
        System.out.println("#          Bem-vindo ao Jogo da Forca!       #");
        System.out.println("#                                            #");
        System.out.println("##############################################\n");

        System.out.print("Digite seu nome: ");
        String nomeJogador = scanner.next();
        System.out.println("\nOlá, " + nomeJogador + "! Vamos começar o jogo.\n");


        Jogador jogador = aplicacao.getJogadorFactory().getJogador(nomeJogador);
        
        try {
            aplicacao.getRepositoryFactory().getJogadorRepository().inserir(jogador);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

        jogarRodada(jogador);
    }
    
    private static void jogarRodada(Jogador jogador) {
        RodadaAppService rodadaAppService = RodadaAppService.getSoleInstance();

        Rodada rodada = rodadaAppService.novaRodada(jogador);
        System.out.println("O tema sorteado foi: " + rodada.getTema().getNome());

        do {
            System.out.println("\nTentativas restantes: " + rodada.getQtdeTentativasRestantes());
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

            System.out.println("\nEscolha uma opção:");
            System.out.println("(1) Tentar adivinhar uma letra!");
            System.out.println("(2) Arriscar palavra!");
            System.out.print("Opção: ");
            String escolha = scanner.next();
            switch (escolha){
                case "1":
                    System.out.print("Digite a letra: ");
                    rodada.tentar(scanner.next().charAt(0));
                    break;
                case "2":
                    String[] palavrasArriscadas = new String[rodada.getNumPalavras()];
                    for (int i = 0; i < palavrasArriscadas.length; i++) {
                        System.out.print("Se arrisque e chute a palavra " + (i + 1)  + " :");
                        palavrasArriscadas[i] = scanner.next();
                    }
                    rodada.arriscar(palavrasArriscadas);
                    break;
            }

           if (rodada.encerrou()) {
               if (rodada.descobriu()) { 
                   System.out.println("Parabéns, você descobriu a palavra secreta!");
               } else {
                   System.out.println("Infelizmente você perdeu essa rodada! Jogue novamente!");
               }
               System.out.println("Pontuação final: " + rodada.calcularPontos());
           }
        } while (!rodada.encerrou());
    }
}
