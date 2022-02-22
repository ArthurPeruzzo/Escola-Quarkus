package br.inf.ids.educacao.models;

import br.inf.ids.educacao.models.DTOS.AlunoDTO;
import br.inf.ids.educacao.models.DTOS.NotaAlunoPorBimestreDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@SqlResultSetMapping(
        name = "mediaFinalAlunoDTO",
        classes = {
                @ConstructorResult(
                        targetClass = AlunoDTO.class,
                        columns = {
                                @ColumnResult(name="matricula", type = Long.class),
                                @ColumnResult(name="nome", type = String.class),
                                @ColumnResult(name="media_final", type = Double.class)
                        }
                )
        }
)
@SqlResultSetMapping(
        name = "mediaFinalAlunoPorBimestreDTO",
        classes = {
                @ConstructorResult(
                        targetClass = NotaAlunoPorBimestreDTO.class,
                        columns = {
                                @ColumnResult(name="bimestre", type = Long.class),
                                @ColumnResult(name="matricula", type = Long.class),
                                @ColumnResult(name="nome", type = String.class),
                                @ColumnResult(name="mediaFinal", type = Double.class)
                        }
                )
        }
)
@Table(name = "tb_aluno")
public class Aluno implements Serializable {

    @Id
    private Long matricula;
    private String nome;
    private String email;
    private String telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "aluno")
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "aluno")
    private List<Presenca> presencas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "aluno")
    private List<AlunoBimestre> alunoBimestre = new ArrayList<>();

   // @JsonIgnore
    //@ManyToMany        //nomeTabela        //nome da chave estrangeira aluno e bimestre
   // @JoinTable(name = "tb_AlunoBimestre", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "bimestre_id"))
   // private List<Bimestre> bimestres = new ArrayList<>();

    public Aluno(){
    }

    public Aluno(Long matricula, String nome, String email, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public List<Presenca> getPresencas() {
        return presencas;
    }

   // public List<Bimestre> getBimestres() {
      //  return bimestres;
   // }
   public List<AlunoBimestre> getAlunoBimestre() {
       return alunoBimestre;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return matricula.equals(aluno.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}