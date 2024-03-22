package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.repository.Repository;

public interface TemaRepository extends Repository {

    public Tema getPorId(long id);

    public Tema[] getPorNome(String nome);

    public Tema[] getTodos();

    public void inserir(Tema tema); // falta criar RepositoryException para implementar o throws

    public void atualizar(Tema tema); // falta criar RepositoryException para implementar o throws

    public void remover(Tema tema); // falta criar RepositoryException para implementar o throws
}
