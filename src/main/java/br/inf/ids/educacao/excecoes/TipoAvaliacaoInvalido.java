package br.inf.ids.educacao.excecoes;

public class TipoAvaliacaoInvalido extends RuntimeException{

    public TipoAvaliacaoInvalido(Object tipoAvaliacao){
        super("O tipo de avaliação não é valido: " + tipoAvaliacao);
    }
}
