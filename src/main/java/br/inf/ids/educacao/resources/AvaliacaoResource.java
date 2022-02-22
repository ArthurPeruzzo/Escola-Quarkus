package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.excecoes.Exceptions;
import br.inf.ids.educacao.excecoes.NotaDaAvaliacaoInvalida;
import br.inf.ids.educacao.models.Avaliacao;
import br.inf.ids.educacao.models.TipoAvaliacao;
import org.jboss.resteasy.annotations.Status;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
public class AvaliacaoResource {

    @Inject
    EntityManager entityManager;

    public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao){
        if(avaliacao.getNotaAvaliacao() > 10.0 || avaliacao.getNotaAvaliacao() < 0){
            throw new NotaDaAvaliacaoInvalida(avaliacao.getNotaAvaliacao());
        }
        entityManager.persist(avaliacao);
        entityManager.flush();
        return avaliacao;
    }

    public Avaliacao buscarAvaliacao(Long id) {
        Avaliacao avaliacao;
        try{
        avaliacao = entityManager.find(Avaliacao.class, id);
        }catch (RuntimeException e) {
            throw new Exceptions(id);
        }
        return avaliacao;
    }

    public Avaliacao updateAvaliacao(Avaliacao avaliacao) {
        if(avaliacao.getNotaAvaliacao() > 10.0 || avaliacao.getNotaAvaliacao() < 0){
            throw new NotaDaAvaliacaoInvalida(avaliacao.getNotaAvaliacao());
        }
        entityManager.merge(avaliacao);
        entityManager.flush();
        return avaliacao;
    }

    public void deleteAvaliacao(Long id) {
        try{
           Avaliacao avaliacao = buscarAvaliacao(id);
            entityManager.remove(avaliacao);
            entityManager.flush();
        }catch (IllegalArgumentException e){
            throw new Exceptions(id);
        }

    }

    public List<Avaliacao> listarAvaliacaoPorNota(){
        String queryJPQL = "SELECT s FROM Avaliacao s ORDER BY notaAvaliacao DESC";
        return entityManager.createQuery(queryJPQL, Avaliacao.class)
                .setMaxResults(20)
                .getResultList();
    }
}
