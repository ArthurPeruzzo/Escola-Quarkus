package br.inf.ids.educacao.models.DTOS;

import java.text.DecimalFormat;

public class AlunoDTO {
    private static final DecimalFormat f = new DecimalFormat("#.##");

    private Long matricula;
    private String nome;
    private Double mediaFinal;

    public AlunoDTO(){
    }

    public AlunoDTO(Long matricula, String nome, Double mediaFinal) {
        this.matricula = matricula;
        this.nome = nome;
        Double mediafinalFormatada = Double.parseDouble(f.format(mediaFinal).replace(",", "."));
        this.mediaFinal = mediafinalFormatada;
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

    public Double getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(Double mediaFinal) {
        this.mediaFinal = mediaFinal;
    }
}
