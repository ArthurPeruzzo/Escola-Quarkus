package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.models.Avaliacao;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class AvaliacaoResourceTest {

    @Inject
    AvaliacaoResource avaliacaoResource;

    @Test
    @Transactional
    void cadastrarAvaliacao() {
        Avaliacao avaliacao = new Avaliacao(10.0);
        Assertions.assertEquals(avaliacao, avaliacaoResource.cadastrarAvaliacao(avaliacao));

    }

    @Test
    @Transactional
    void buscarAvaliacao() {
        Avaliacao avaliacao = new Avaliacao(9.0);
        avaliacaoResource.cadastrarAvaliacao(avaliacao);
        Assertions.assertEquals(avaliacao, avaliacaoResource.buscarAvaliacao(avaliacao.getId()));

    }

    @Test
    @Transactional
    void updateAvaliacao() {
        Avaliacao avaliacao = new Avaliacao(9.0);
        avaliacaoResource.cadastrarAvaliacao(avaliacao);
        Avaliacao avaliacaoAtualizada = avaliacaoResource.buscarAvaliacao(avaliacao.getId());
        avaliacaoAtualizada.setNotaAvaliacao(10.0);
        avaliacaoResource.updateAvaliacao(avaliacaoAtualizada);
        Assertions.assertEquals(avaliacaoAtualizada, avaliacaoResource.buscarAvaliacao(avaliacaoAtualizada.getId()));
    }

    @Test
    @Transactional
    void deleteAvaliacao() {
        Avaliacao avaliacao = new Avaliacao(9.0);
        avaliacaoResource.cadastrarAvaliacao(avaliacao);
        avaliacaoResource.deleteAvaliacao(avaliacao.getId());
        Assertions.assertNull(avaliacaoResource.buscarAvaliacao(avaliacao.getId()));
    }

    @Test
    @Transactional
    void listarAvaliacaoPorNota() {
        int cont = 0;

        Avaliacao avaliacao1 = new Avaliacao(5.0);
        Avaliacao avaliacao2 = new Avaliacao(9.0);
        Avaliacao avaliacao3 = new Avaliacao(10.0);

        List<Avaliacao> avaliacoes =  avaliacaoResource.listarAvaliacaoPorNota();
        for(Avaliacao avaliacao : avaliacoes){
            cont++;
        }
        if(cont > 20){
            Assertions.fail("só deve ser mostrado 20 registros por pesquisa");
        }
    }
}