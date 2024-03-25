package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.factory.EntityFactory;

public class TemaFactoryImpl extends EntityFactory implements TemaFactory {

    private static TemaFactoryImpl soleInstance;

    private TemaFactoryImpl(TemaRepository repository) {
        super(repository);
    }

    public static void createSoleInstance(TemaRepository repository) {
        if (soleInstance == null) {
            soleInstance = new TemaFactoryImpl(repository);
        }
    }

    public static TemaFactoryImpl getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException(); // sem utilizar o create primeiro
        }

        return soleInstance;
    }

    private TemaRepository getTemaRepository() {
        return (TemaRepository) this.getRepository();
    }

    @Override
    public Tema getTema(String nome) {
        return Tema.criar(this.getTemaRepository().getProximoId(), nome);
    }
}
