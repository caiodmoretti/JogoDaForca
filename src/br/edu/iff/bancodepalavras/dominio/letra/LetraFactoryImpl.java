package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class LetraFactoryImpl implements LetraFactory  {
	
	public Letra[] pool = new Letra[26];
	public Letra encoberta;
	
	protected LetraFactoryImpl() {

	}

	public abstract Letra getLetra(char codigo);
	
	public final Letra getLetraEncoberta() {
		return this.encoberta;
	}
	protected abstract Letra criarLetra(char codigo);
}
