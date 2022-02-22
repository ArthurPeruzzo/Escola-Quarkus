package br.inf.ids.educacao.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "tb_alunobimestre")
public class AlunoBimestre implements Serializable {

    @Id
    @Column(name = "alunobimestreid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alunobimestre")
    @SequenceGenerator(name = "seq_alunobimestre", sequenceName = "seq_alunobimestre", allocationSize = 1)
    private Long alunoBimestreId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bimestre_id")
    private Bimestre bimestre;

    public AlunoBimestre() {
    }

    public AlunoBimestre( Aluno aluno, Bimestre bimestre) {
        this.aluno = aluno;
        this.bimestre = bimestre;
    }

    public Long getAlunoBimestreId() {
        return alunoBimestreId;
    }

    public void setAlunoBimestreId(Long alunoBimestreId) {
        this.alunoBimestreId = alunoBimestreId;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Bimestre getBimestre() {
        return bimestre;
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre;
    }
}
