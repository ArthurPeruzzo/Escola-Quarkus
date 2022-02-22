package br.inf.ids.educacao.models.DTOS;

public class TotalDeFaltasDTO {

    private Long matricula;
    private String nome;
    private Integer totalDeFaltas;

    public TotalDeFaltasDTO() {
    }

    public TotalDeFaltasDTO(Long matricula, String nome, Integer totalDeFaltas) {
        this.matricula = matricula;
        this.nome = nome;
        this.totalDeFaltas = totalDeFaltas;
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

    public Integer getTotalDeFaltas() {
        return totalDeFaltas;
    }

    public void setTotalDeFaltas(Integer totalDeFaltas) {
        this.totalDeFaltas = totalDeFaltas;
    }
}
