package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.excecoes.BimestreInvalido;
import br.inf.ids.educacao.excecoes.Exceptions;
import br.inf.ids.educacao.models.Bimestre;
import br.inf.ids.educacao.models.DTOS.notaDasAvaliacoesPorBimestreDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequestScoped
public class BimestreResource {

    @Inject
    EntityManager entityManager;

    Bimestre bimestre;

    public Bimestre cadastrarBimestre(Bimestre bimestre){
        entityManager.persist(bimestre);
        entityManager.flush();
        return bimestre;
    }

    public Bimestre buscarBimestre(Long id) {
        Bimestre bimestre;
        try{
        bimestre= entityManager.find(Bimestre.class, id);
        }catch (RuntimeException e) {
            throw new Exceptions(id);
        }
        return bimestre;
    }

    public Bimestre updateBimestre(Bimestre bimestre) {
        if(bimestre.getInicioBimestre().isAfter(bimestre.getFimBimestre())){
            throw new BimestreInvalido(bimestre);
        }
        entityManager.merge(bimestre);
        entityManager.flush();
        return bimestre;
    }

    public void deleteBimestre(Long id) {
        try{
            Bimestre bimestre = buscarBimestre(id);
            entityManager.remove(bimestre);
            entityManager.flush();
        }catch (IllegalArgumentException e){
            throw new Exceptions(id);
        }
    }

    public List<Bimestre> listarBimestresPorData(){
        String queryJPQL = "SELECT s FROM Bimestre s ORDER BY s.inicioBimestre";
        return entityManager.createQuery(queryJPQL, Bimestre.class).getResultList();
    }

    public Long contarBimestres(){
        String queryJPQL = " select count(*) from Bimestre ";
        return entityManager.createQuery(queryJPQL, Long.class).getSingleResult();
    }

    public long totalDeDiasLetivos(){
        long totalDeDiasLetivos = 0l;
        List<Bimestre> bimestres = listarBimestresPorData();
        for(Bimestre bimestre : bimestres){
            totalDeDiasLetivos += ChronoUnit.DAYS.between(bimestre.getInicioBimestre(), bimestre.getFimBimestre());
        }
        return  totalDeDiasLetivos;
    }

    public long diasLetivosBimestre(Long id){
    String queryJPQL = " SELECT s FROM Bimestre s where s.id =:id ";
      Query q = entityManager.createQuery(queryJPQL, Bimestre.class)
              .setParameter("id", id);
        Bimestre bimestre = (Bimestre) q.getSingleResult();

        return ChronoUnit.DAYS.between(bimestre.getInicioBimestre(), bimestre.getFimBimestre());
    }

    public List<notaDasAvaliacoesPorBimestreDTO> notaDasAvaliacoesPorBimestre(Long matricula, Long bimestre) {
        String sql =" select aluno.matricula, aluno.nome, bimestre.id as bimestre, avaliacao.notaavaliacao as nota_da_avaliacao, tipo_avaliacao.nomeavaliacao as tipo_da_avaliacao "
                +" from tb_aluno aluno "
                +"inner join tb_alunobimestre aluno_bimestre "
                +" on aluno.matricula = aluno_bimestre.aluno_id "
                +" inner join tb_bimestre bimestre "
                +" on aluno_bimestre.bimestre_id = bimestre.id "
                +" inner join tb_avaliacao avaliacao "
                +" on 	bimestre.id = avaliacao.bimestre_id "
                +" and avaliacao.aluno_id = aluno.matricula "
                +" inner join tb_tipoavaliacao tipo_avaliacao "
                +" on avaliacao.tipoavaliacao_id = tipo_avaliacao.id "
                +" where aluno.matricula = :matricula and bimestre.id = :bimestre "
                +" order by bimestre.id ";
        Query q = entityManager.createNativeQuery(sql, "notaDasAvaliacoesPorBimestreDTO")
                .setParameter("matricula", matricula)
                .setParameter("bimestre", bimestre);

        return q.getResultList();
    }
}