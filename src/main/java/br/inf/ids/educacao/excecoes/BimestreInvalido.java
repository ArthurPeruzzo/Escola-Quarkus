package br.inf.ids.educacao.excecoes;

public class BimestreInvalido extends RuntimeException{

    public BimestreInvalido(Object bimestre){
        super("Erro ao tentar cadastrar/atualizar bimestre: " + bimestre);
    }

}
