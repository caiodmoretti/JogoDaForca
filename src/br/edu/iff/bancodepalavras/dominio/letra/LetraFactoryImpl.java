package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class LetraFactoryImpl implements LetraFactory  {
	//Flyweight
	public Letra[] pool = new Letra[26];
	public Letra encoberta;
	
	protected LetraFactoryImpl() {

	}

	public final Letra getLetra(char codigo) {
		return this.criarLetra(codigo);
	};
	//Template Method
	public final Letra getLetraEncoberta() {
		return this.encoberta;
	}
	//Factory Method
	protected abstract Letra criarLetra(char codigo);
}
