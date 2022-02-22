package br.inf.ids.educacao.excecoes;

public class NotaDaAvaliacaoInvalida extends RuntimeException{

    public NotaDaAvaliacaoInvalida(Object nota){
        super("a nota passada como parametro está incorreta: " + nota);
    }
}
