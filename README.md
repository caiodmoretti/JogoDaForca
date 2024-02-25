# JogoDaForca
Trabalho da disciplina Laboratório de OO

# Jogo da Forca:
O jogo consiste em se tentar acertar as letras de uma ou mais palavras escondidas. Se o jogador errar a letra, surge mais um pedaço de um boneco que poderá ir pra a forca;
As palavras, para cada rodada, são obtidas de um banco de palavras, que o próprio jogador pode cadastrar. Cada palavra pertence a um tema;
A cada rodada, a aplicação sorteia se mostrará uma, duas ou três palavras e escolhe aleatoriamente o tema e as palavras. Todas as palavras são do mesmo tema;
A palavra é exibida escondida e cada letra acertada desvenda sua posição correspondente;
Letras erradas são colocadas num quadro e um dos pedaços do boneco aparece. Pode
errar no máximo 10 letras. No décimo erro, o boneco vai pra forca;
Somente pode arriscar, na tentativa de acertar todas as palavras, uma única vez.
Quando ele acerta as palavras, ganha 100 pontos e para cada letra que ficou encoberta
somam-se mais 15 pontos;
A rodada termina quando arriscou ou descobriu ou a quantidade de letras erradas é
igual a 10. Descobriu significa que arriscou e acertou ou encontrou todas as letras de
todas as palavras;
O nome do jogador é guardado, bem como os pontos obtidos nas suas rodadas para
fazer parte do quadro de maiores escores (pontuações).
Implemente, em Java, o jogo descrito acima, com classes, relacionamentos,
atributos, operações, invariantes (regras de negócio), repositórios, fábricas e
serviços. O modelo de design, a ser implementado, será fornecido pelo professor.