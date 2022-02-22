package br.inf.ids.educacao.rest;

import br.inf.ids.educacao.models.AlunoBimestre;
import br.inf.ids.educacao.resources.AlunoBimestreResource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/alunoBimestre")
public class AlunoBimestreRest {

    @Inject
    AlunoBimestreResource alunoBimestreResource;

    @POST
    @Path("/cadastroAlunoBimestre")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON) //ok
    public AlunoBimestre cadastrarAluno(AlunoBimestre alunoBimestre){
        return alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre);
    }

    @GET
    @Path("/buscarAlunoBimestre")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON) //ok
    public List<AlunoBimestre> buscarAluno(){
        return alunoBimestreResource.buscarAlunoBimestre();
    }

}
