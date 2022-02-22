package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.models.TipoAvaliacao;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class TipoAvaliacaoResourceTest {

    @Inject
    TipoAvaliacaoResource tipoAvaliacaoResource;

    @Test
    @Transactional
    void cadastrarTipoAvaliacao() {
        TipoAvaliacao tipoAvaliacao = new TipoAvaliacao("Participação", 2.0);
        Assertions.assertEquals(tipoAvaliacao, tipoAvaliacaoResource.cadastrarTipoAvaliacao(tipoAvaliacao));

    }

    @Test
    @Transactional
    void buscarTipoAvaliacao() {
        TipoAvaliacao tipoAvaliacao = new TipoAvaliacao("Participação", 2.0);
        Assertions.assertEquals(tipoAvaliacao, tipoAvaliacaoResource.cadastrarTipoAvaliacao(tipoAvaliacao));
        Assertions.assertEquals(tipoAvaliacao, tipoAvaliacaoResource.buscarTipoAvaliacao(tipoAvaliacao.getId()));

    }

    @Test
    @Transactional
    void updateTipoAvaliacao() {
        TipoAvaliacao tipoAvaliacao = new TipoAvaliacao("Participação", 2.0);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(tipoAvaliacao);
        TipoAvaliacao tipoAvaliacaoAtualizada = tipoAvaliacaoResource.buscarTipoAvaliacao(tipoAvaliacao.getId());
        tipoAvaliacaoAtualizada.setPesoAvaliacao(5.0);
        tipoAvaliacaoResource.updateTipoAvaliacao(tipoAvaliacaoAtualizada);

        Assertions.assertEquals(tipoAvaliacaoAtualizada, tipoAvaliacaoResource.buscarTipoAvaliacao(tipoAvaliacaoAtualizada.getId()));
    }

    @Test
    @Transactional
    void deleteTipoAvaliacao() {
        TipoAvaliacao tipoAvaliacao = new TipoAvaliacao("Participação", 2.0);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(tipoAvaliacao);
        tipoAvaliacaoResource.deleteTipoAvaliacao(tipoAvaliacao.getId());
        Assertions.assertNull(tipoAvaliacaoResource.buscarTipoAvaliacao(tipoAvaliacao.getId()));
    }
}