package br.inf.ids.educacao.excecoes;

public class Exceptions extends RuntimeException{
    public Exceptions(Object id){
        super("id nao encontrado: " + id);
    }
}
