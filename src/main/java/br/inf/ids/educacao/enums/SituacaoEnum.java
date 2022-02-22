package br.inf.ids.educacao.enums;

public enum SituacaoEnum {
    APROVADO(1),
    REPROVADO(2),
    RECUPERACAO(3);

    private int codigo; //codigo do tipo enumerado

    SituacaoEnum(int codigo){
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static SituacaoEnum valor(int codigo) { //metodo que retorna a SituacaoEnum a partir do codigo;

        for(SituacaoEnum valor : SituacaoEnum.values()) {//percorre todos os valores
            if(valor.getCodigo() == codigo) {
                return valor;
            }
        }
        throw new IllegalArgumentException("valor do codigo invalido!");
    }
}
