package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.enums.SituacaoEnum;
import br.inf.ids.educacao.excecoes.Exceptions;
import br.inf.ids.educacao.models.Aluno;
import br.inf.ids.educacao.models.DTOS.AlunoDTO;
import br.inf.ids.educacao.models.DTOS.AlunoSituacaoFinalDTO;
import br.inf.ids.educacao.models.DTOS.NotaAlunoPorBimestreDTO;
import br.inf.ids.educacao.models.DTOS.TotalDeFaltasDTO;
import org.flywaydb.core.Flyway;
import org.jboss.resteasy.annotations.ResponseObject;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.text.DecimalFormat;
import java.util.List;

@RequestScoped
public class AlunoResource {

    private static final DecimalFormat f = new DecimalFormat("#.##");

    @Inject
    EntityManager entityManager;

    @Inject
    BimestreResource bimestreResource;

    @Inject
    PresencaResource presencaResource;

    Aluno aluno;

    public Aluno cadastrarAluno(Aluno aluno){
        entityManager.persist(aluno);
        entityManager.flush();
        return aluno;
    }

    public Aluno buscar(Long matricula) {
        Aluno aluno;
        aluno = entityManager.find(Aluno.class, matricula);
        return aluno;
    }

    public Aluno update(Aluno aluno) {
        entityManager.merge(aluno);
        entityManager.flush();
        return aluno;
    }

    public void delete(Long matricula) {
        try{
            Aluno aluno = buscar(matricula);
            entityManager.remove(aluno);
            entityManager.flush();
        }catch (IllegalArgumentException e){
            throw new Exceptions(matricula);
        }
    }
    public List<Aluno> buscarTodosOsAlunos(){
        String queryJPQL = "SELECT s FROM Aluno s";
        return entityManager.createQuery(queryJPQL, Aluno.class)
                .setMaxResults(3)
                .getResultList();
    }

    public List<Aluno> pesquisarAlunos(String caractere){
        String queryJPQL = "SELECT s FROM Aluno s WHERE UPPER(s.nome) LIKE :caractere";
       List<Aluno> alunos = entityManager.createQuery(queryJPQL, Aluno.class)
                .setMaxResults(3)
                .setParameter("caractere", "%" + caractere.toUpperCase() + "%")
                .getResultList();
        return alunos;
    }

    public AlunoDTO mediaFinalDoAluno(Long matricula) {
        String sql = " select aluno.matricula, aluno.nome, sum(avaliacao.notaavaliacao * tipo_avaliacao.pesoavaliacao / 10)/4 as media_final "
        +" from tb_aluno aluno"
        +" inner join tb_alunobimestre aluno_bimestre "
        +" on aluno.matricula = aluno_bimestre.aluno_id "
        +" inner join tb_bimestre bimestre "
        +" on aluno_bimestre.bimestre_id = bimestre.id "
        +" inner join tb_avaliacao avaliacao "
        +" on 	bimestre.id = avaliacao.bimestre_id "
        +" and avaliacao.aluno_id = aluno.matricula "
        +" inner join tb_tipoavaliacao tipo_avaliacao "
        +" on avaliacao.tipoavaliacao_id = tipo_avaliacao.id "
        +" where aluno.matricula = :matricula "
        +" GROUP BY aluno.matricula, aluno.nome ";
    Query q = entityManager.createNativeQuery(sql, "mediaFinalAlunoDTO").setParameter("matricula", matricula);
        return (AlunoDTO) q.getSingleResult();
    }

    public List<NotaAlunoPorBimestreDTO> mediaFinalDeUmAlunoEmCadaBimestre(Long matricula){
        String sql = " select bimestre.id as bimestre, aluno.matricula, aluno.nome, sum(avaliacao.notaavaliacao * tipo_avaliacao.pesoavaliacao / 10) as mediaFinal "
                +"from tb_aluno aluno "
                +"inner join tb_alunobimestre aluno_bimestre "
                +" on aluno.matricula = aluno_bimestre.aluno_id "
                +" inner join tb_bimestre bimestre "
                +" on aluno_bimestre.bimestre_id = bimestre.id "
                +" inner join tb_avaliacao avaliacao "
                +" on bimestre.id = avaliacao.bimestre_id "
                +" and avaliacao.aluno_id = aluno.matricula "
                +" inner join tb_tipoavaliacao tipo_avaliacao "
                +" on avaliacao.tipoavaliacao_id = tipo_avaliacao.id "
                +" where aluno.matricula = :matricula "
                +" group by bimestre.id, aluno.matricula, aluno.nome; ";
        Query query = entityManager.createNativeQuery(sql, "mediaFinalAlunoPorBimestreDTO")
                .setParameter("matricula", matricula);
        return query.getResultList();
    }

    public AlunoSituacaoFinalDTO situacaoFinalDoAluno(Long matricula){
        int codigo = 0;
        Double porcentagemPresenca = 0.0;
        long totalDeDiasLetivos = bimestreResource.totalDeDiasLetivos();
        AlunoDTO aluno = mediaFinalDoAluno(matricula);
        TotalDeFaltasDTO totalDeFaltas = presencaResource.totalDeFaltasDeUmAluno(matricula);

        porcentagemPresenca = 100.0 - ((totalDeFaltas.getTotalDeFaltas() * 100.0) / totalDeDiasLetivos);


        if(porcentagemPresenca < 75.0 || aluno.getMediaFinal() < 5.0){
            codigo = 2;
        }else if(porcentagemPresenca >= 75.0 && aluno.getMediaFinal() >= 6.0){
            codigo = 1;
        }else {
            codigo = 3;
        }
        AlunoSituacaoFinalDTO alunoSituacaoFinal = new AlunoSituacaoFinalDTO(matricula, aluno.getNome(), aluno.getMediaFinal(), SituacaoEnum.valor(codigo));
        return alunoSituacaoFinal;
    }
}
