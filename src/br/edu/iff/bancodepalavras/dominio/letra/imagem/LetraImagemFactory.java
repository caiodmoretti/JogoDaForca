package br.edu.iff.bancodepalavras.dominio.letra.imagem;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactoryImpl;

public class LetraImagemFactory extends LetraFactoryImpl {
	private static LetraImagemFactory soleInstance;
	

	
	public static LetraImagemFactory getSoleInstace() {
		if(soleInstance==null) {
			soleInstance = new LetraImagemFactory();
			return soleInstance;
		}
		else {
			return soleInstance;
		}
	}
	
	private LetraImagemFactory() {
		
	}
	
	@Override
	protected Letra criarLetra(char codigo) {
		return null;
	}

	@Override
	public Letra getLetraEncoberta() {

		return null;
	}

}
