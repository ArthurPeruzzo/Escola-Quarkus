package br.inf.ids.educacao.models;

import br.inf.ids.educacao.models.DTOS.FaltasAlunoDTO;
import br.inf.ids.educacao.models.DTOS.TotalDeFaltasDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@SqlResultSetMapping(
        name = "faltasAlunoDTO",
        classes = {
                @ConstructorResult(
                        targetClass = FaltasAlunoDTO.class,
                        columns = {
                                @ColumnResult(name="bimestre", type = Long.class),
                                @ColumnResult(name="matricula", type = Long.class),
                                @ColumnResult(name="nome", type = String.class),
                                @ColumnResult(name="numeroDeFaltas", type = Integer.class),
                        }
                )
        }
)
@SqlResultSetMapping(
        name = "TotalDeFaltasDTO",
        classes = {
                @ConstructorResult(
                        targetClass = TotalDeFaltasDTO.class,
                        columns = {
                                @ColumnResult(name="matricula", type = Long.class),
                                @ColumnResult(name="nome", type = String.class),
                                @ColumnResult(name="totalDeFaltas", type = Integer.class)
                        }
                )
        }
)
@Table(name = "tb_presenca")
public class Presenca implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_presenca")
    @SequenceGenerator(name = "seq_presenca", sequenceName = "seq_presenca", allocationSize = 1)
    private Long id;
    private Integer numeroDeFaltas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_bimestre") //nome da chave estrangeira
    private Bimestre bimestre;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    public Presenca() {
    }

    public Presenca( Integer numeroDeFaltas){
        this.numeroDeFaltas = numeroDeFaltas;
    }

    public Presenca( Integer numeroDeFaltas, Bimestre bimestre, Aluno aluno) {
       // if(numeroDeFaltas > bimestreResource.diasLetivosBimestre(bimestre.getId()) || numeroDeFaltas< 0){
         //  throw new CadastroDePresencaInvalido(numeroDeFaltas);
        //}
        this.numeroDeFaltas = numeroDeFaltas;
        this.bimestre = bimestre;
        this.aluno = aluno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDeFaltas() {
        return numeroDeFaltas;
    }

    public void setNumeroDeFaltas(Integer numeroDeFaltas) {
        this.numeroDeFaltas = numeroDeFaltas;
    }

    public Bimestre getBimestre() {
        return bimestre;
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presenca presenca = (Presenca) o;
        return id.equals(presenca.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

  }
