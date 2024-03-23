package br.edu.iff.jogoforca.texto;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.texto.LetraTextoFactory;
import br.edu.iff.jogoforca.ElementoGraficoFactory;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.texto.BonecoTextoFactory;

public class ElementoGraficoTextoFactory implements ElementoGraficoFactory {

	private static ElementoGraficoTextoFactory soleInstance;

	
	public ElementoGraficoTextoFactory getSoleInstance() {
		if(soleInstance == null) {
			soleInstance = new ElementoGraficoTextoFactory();
			return soleInstance;
		}
		else {
			return soleInstance;
		}
	}
	
	private ElementoGraficoTextoFactory() {
	}
	
	@Override
	public Boneco getBoneco() {
		return BonecoTextoFactory.getSoleInstance().getBoneco();
	}

	@Override
	public Letra getLetra(char codigo) {
		return LetraTextoFactory.getSoleInstace().getLetra(codigo);
	}

	@Override
	public Letra getLetraEncobert() {
		 return LetraTextoFactory.getSoleInstace().getLetraEncobert();
	}

}
