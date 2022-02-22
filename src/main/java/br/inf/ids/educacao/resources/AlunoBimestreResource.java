package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.models.AlunoBimestre;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@RequestScoped
public class AlunoBimestreResource {

    @Inject
    EntityManager entityManager;

    public AlunoBimestre cadastrarAlunoBimestre(AlunoBimestre alunoBimestre){
        entityManager.persist(alunoBimestre);
        entityManager.flush();
        return alunoBimestre;
    }

    public List<AlunoBimestre> buscarAlunoBimestre() {

       String query = "select s from AlunoBimestre s";

       return entityManager.createQuery(query, AlunoBimestre.class).getResultList();
    }
}