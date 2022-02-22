package br.inf.ids.educacao.excecoes;

public class CadastroDePresencaInvalido extends RuntimeException{
    public CadastroDePresencaInvalido(Object numeroDeFaltas){
        super("numero de faltas não é válido: " + numeroDeFaltas);
    }
}
