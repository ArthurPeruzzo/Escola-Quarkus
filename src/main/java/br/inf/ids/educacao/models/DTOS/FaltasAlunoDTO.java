package br.inf.ids.educacao.models.DTOS;

public class FaltasAlunoDTO {

    private Long bimestre;
    private Long matricula;
    private String nome;
    private Integer numeroDeFaltas;

    public FaltasAlunoDTO() {
    }

    public FaltasAlunoDTO(Long bimestre, Long matricula, String nome, Integer numeroDeFaltas) {
        this.bimestre = bimestre;
        this.matricula = matricula;
        this.nome = nome;
        this.numeroDeFaltas = numeroDeFaltas;
    }

    public Long getBimestre() {
        return bimestre;
    }

    public void setBimestre(Long bimestre) {
        this.bimestre = bimestre;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroDeFaltas() {
        return numeroDeFaltas;
    }

    public void setNumeroDeFaltas(Integer numeroDeFaltas) {
        this.numeroDeFaltas = numeroDeFaltas;
    }
}
