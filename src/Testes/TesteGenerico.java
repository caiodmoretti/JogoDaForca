package Testes;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.texto.LetraTextoFactory;

public class TesteGenerico {

	public static void main(String[] args) {
		Letra letra = LetraTextoFactory.getSoleInstace().getLetra('a');
		
		System.out.println(letra);

	}

}
