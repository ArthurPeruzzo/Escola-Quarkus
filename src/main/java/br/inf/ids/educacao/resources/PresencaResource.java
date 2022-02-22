package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.excecoes.CadastroDePresencaInvalido;
import br.inf.ids.educacao.excecoes.Exceptions;
import br.inf.ids.educacao.excecoes.NotaDaAvaliacaoInvalida;
import br.inf.ids.educacao.models.DTOS.FaltasAlunoDTO;
import br.inf.ids.educacao.models.DTOS.TotalDeFaltasDTO;
import br.inf.ids.educacao.models.Presenca;
import br.inf.ids.educacao.models.TipoAvaliacao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequestScoped
public class PresencaResource {

    @Inject
    EntityManager entityManager;

    @Inject
    AlunoResource alunoResource;

    @Inject
    BimestreResource bimestreResource;

    public Presenca cadastrarPresenca(Presenca presenca){
        if(presenca.getNumeroDeFaltas() > 40){
            throw new CadastroDePresencaInvalido(presenca.getNumeroDeFaltas());
        }
        entityManager.persist(presenca);
        entityManager.flush();
        return presenca;
    }

    public Presenca buscarPresenca(Long id) {
        Presenca presenca;
        try{
        presenca= entityManager.find(Presenca.class, id);
        }catch (RuntimeException e) {
            throw new Exceptions(id);
        }
        return presenca;
    }

    public void updatePresenca(Presenca presenca) {
        if(presenca.getNumeroDeFaltas() > 40){
            throw new CadastroDePresencaInvalido(presenca.getNumeroDeFaltas());
        }
        entityManager.merge(presenca);
        entityManager.flush();
    }

    public void deletePresenca(Long id) {
        try{
            Presenca presenca = buscarPresenca(id);
            entityManager.remove(presenca);
            entityManager.flush();
        }catch (IllegalArgumentException e){
            throw new Exceptions(id);
        }

    }

    public List<FaltasAlunoDTO> faltasPorBimestreDeUmAluno(Long matricula){
        alunoResource.buscar(matricula);
        String sqlNativo = " select bimestre.id as bimestre, aluno.matricula, aluno.nome, presenca.numerodefaltas "
                +" from tb_aluno aluno"
                +" inner join tb_presenca presenca "
                +" on aluno.matricula = presenca.id_aluno "
                +" inner join tb_bimestre bimestre "
                +" on presenca.id_bimestre = bimestre.id "
                +" where aluno.matricula = :matricula ";
        Query query = entityManager.createNativeQuery(sqlNativo,"faltasAlunoDTO")
                .setParameter("matricula", matricula);
        return query.getResultList();
    }

    public TotalDeFaltasDTO totalDeFaltasDeUmAluno(Long matricula){
        String sqlNativo = " select aluno.matricula, aluno.nome, sum(presenca.numerodefaltas) as totalDeFaltas "
                +" from tb_aluno aluno "
                +" inner join tb_presenca presenca "
                +" on aluno.matricula = presenca.id_aluno "
                +"inner join tb_bimestre bimestre "
                +" on presenca.id_bimestre = bimestre.id "
                +"where aluno.matricula = :matricula "
                +"group by aluno.matricula, aluno.nome ";

        Query query = entityManager.createNativeQuery(sqlNativo, "TotalDeFaltasDTO")
                .setParameter("matricula", matricula);
        return (TotalDeFaltasDTO) query.getSingleResult();
    }
}
