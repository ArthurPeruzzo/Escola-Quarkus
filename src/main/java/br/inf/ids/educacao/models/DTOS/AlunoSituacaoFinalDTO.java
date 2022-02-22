package br.inf.ids.educacao.models.DTOS;

import br.inf.ids.educacao.enums.SituacaoEnum;

import java.text.DecimalFormat;
import java.util.Objects;

public class AlunoSituacaoFinalDTO {

    private static final DecimalFormat f = new DecimalFormat("#.##");

    private Long matricula;
    private String nome;
    private Double mediaFinal;
    private SituacaoEnum situacaoFinal;

    public AlunoSituacaoFinalDTO() {
    }

    public AlunoSituacaoFinalDTO(Long matricula, String nome, Double mediaFinal, SituacaoEnum situacaoFinal) {
        this.matricula = matricula;
        this.nome = nome;
        Double mediafinalFormatada = Double.parseDouble(f.format(mediaFinal).replace(",", "."));
        this.mediaFinal = mediafinalFormatada;
        this.situacaoFinal = situacaoFinal;
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

    public SituacaoEnum getSituacaoFinal() {
        return situacaoFinal;
    }

    public void setSituacaoFinal(SituacaoEnum situacaoFinal) {
        this.situacaoFinal = situacaoFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlunoSituacaoFinalDTO that = (AlunoSituacaoFinalDTO) o;
        return matricula.equals(that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return "AlunoSituacaoFinalDTO{" +
                "matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", mediaFinal=" + mediaFinal +
                ", situacaoFinal=" + situacaoFinal +
                '}';
    }
}
