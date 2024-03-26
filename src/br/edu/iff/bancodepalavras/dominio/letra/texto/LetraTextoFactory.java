package br.edu.iff.bancodepalavras.dominio.letra.texto;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactoryImpl;

public class LetraTextoFactory extends LetraFactoryImpl {
	
	private static LetraTextoFactory soleInstance;

	public static LetraTextoFactory getSoleInstace() {
		if(soleInstance==null) {
			soleInstance = new LetraTextoFactory();
			return soleInstance;
		}
		else {
			return soleInstance;
		}
	}
	
	private LetraTextoFactory() {
		this.encoberta = new LetraTexto('*');
	}
	
	//97 é o valor decimal da leta 'a' na tabela ascii, 122 é 'z'
	@Override
	protected Letra criarLetra(char codigo) {
		if(codigo < 'a' || codigo > 'z') {
			throw new RuntimeException("O carácter não é válido.");
		}
		if(this.pool[codigo - 97] != null) {
			return this.pool[codigo - 97];			
		}
		else {
			this.pool[codigo - 97] = new LetraTexto(codigo);
			return this.pool[codigo - 97];			
		}
	}


	@Override
	public Letra getLetraEncoberta() {
		return this.encoberta;
	}

}
