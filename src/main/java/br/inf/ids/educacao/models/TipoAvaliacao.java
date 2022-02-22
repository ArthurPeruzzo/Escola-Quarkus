package br.inf.ids.educacao.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_tipoavaliacao")
public class TipoAvaliacao implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipoavaliacao")
    @SequenceGenerator(name = "seq_tipoavaliacao", sequenceName = "seq_tipoavaliacao", allocationSize = 1)
    private Long id;
    private String nomeAvaliacao;
    private Double pesoAvaliacao;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoAvaliacao")
    List<Avaliacao> avaliacoes = new ArrayList<>();

    public TipoAvaliacao(){
    }

    public TipoAvaliacao(String nomeAvaliacao, Double pesoAvaliacao) {
        this.nomeAvaliacao = nomeAvaliacao;
        this.pesoAvaliacao = pesoAvaliacao;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAvaliacao() {
        return nomeAvaliacao;
    }

    public void setNomeAvaliacao(String nomeAvaliacao) {
        this.nomeAvaliacao = nomeAvaliacao;
    }

    public Double getPesoAvaliacao() {
        return pesoAvaliacao;
    }

    public void setPesoAvaliacao(Double pesoAvaliacao) {
        this.pesoAvaliacao = pesoAvaliacao;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAvaliacao that = (TipoAvaliacao) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
