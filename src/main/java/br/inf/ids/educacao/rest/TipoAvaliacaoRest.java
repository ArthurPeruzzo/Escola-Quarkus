package br.inf.ids.educacao.rest;

import br.inf.ids.educacao.models.TipoAvaliacao;
import br.inf.ids.educacao.resources.TipoAvaliacaoResource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/tipoAvaliacao")
public class TipoAvaliacaoRest {

    @Inject
    TipoAvaliacaoResource tipoAvaliacaoResource;

    @POST
    @Path("/cadastroTipoAvaliacao")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public TipoAvaliacao cadastrarTipoAvaliacao(TipoAvaliacao tipoAvaliacao){
        return tipoAvaliacaoResource.cadastrarTipoAvaliacao(tipoAvaliacao);
    }

    @GET
    @Path("/buscarTipoAvaliacao/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoAvaliacao buscarTipoAvaliacao(@PathParam("id") Long id){
        return tipoAvaliacaoResource.buscarTipoAvaliacao(id);
    }

    @DELETE
    @Path("deletarTipoAvaliacao/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletarTipoAvaliacao(@PathParam("id") Long id){
        tipoAvaliacaoResource.deleteTipoAvaliacao(id);
    }

    @PUT
    @Path("atualizarTipoAvaliacao")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoAvaliacao uptadeTipoAvaliacao(TipoAvaliacao tipoAvaliacao){
       return tipoAvaliacaoResource.updateTipoAvaliacao(tipoAvaliacao);
    }
}
