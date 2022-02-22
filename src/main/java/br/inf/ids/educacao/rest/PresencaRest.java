package br.inf.ids.educacao.rest;

import br.inf.ids.educacao.models.DTOS.FaltasAlunoDTO;
import br.inf.ids.educacao.models.DTOS.TotalDeFaltasDTO;
import br.inf.ids.educacao.models.Presenca;
import br.inf.ids.educacao.resources.PresencaResource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/presenca")
public class PresencaRest {

    @Inject
    PresencaResource presencaResource;

    @POST
    @Path("/cadastroPresenca")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Presenca cadastrarPresenca(Presenca presenca){
        return presencaResource.cadastrarPresenca(presenca);
    }

    @GET
    @Path("/buscarPresenca/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Presenca buscarPresenca(@PathParam("id") Long id){
        return presencaResource.buscarPresenca(id);
    }

    @DELETE
    @Path("/deletarPresenca/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletarPresenca(@PathParam("id") Long id){
        presencaResource.deletePresenca(id);
    }

    @PUT
    @Path("/atualizarPresenca")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void uptadePresenca(Presenca presenca){
        presencaResource.updatePresenca(presenca);
    }

    @GET
    @Path("/faltasDoAluno/{matricula}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON) //ok
    public List<FaltasAlunoDTO> faltasAluno
            (@PathParam("matricula") Long matricula){
        return presencaResource.faltasPorBimestreDeUmAluno(matricula);
    }

    @GET
    @Path("/totalDefaltasDoAluno/{matricula}")//ok
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public TotalDeFaltasDTO totalDefaltasAluno
            (@PathParam("matricula") Long matricula){
        return presencaResource.totalDeFaltasDeUmAluno(matricula);
    }
}
