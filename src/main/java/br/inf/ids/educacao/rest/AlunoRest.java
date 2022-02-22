package br.inf.ids.educacao.rest;

import br.inf.ids.educacao.models.Aluno;
import br.inf.ids.educacao.models.DTOS.*;
import br.inf.ids.educacao.resources.AlunoResource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/alunos")
public class AlunoRest {

    @Inject
    AlunoResource alunoResource;

    @POST
    @Path("/cadastroAluno")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON) //ok
    public Aluno cadastrarAluno(Aluno aluno){
        return alunoResource.cadastrarAluno(aluno);
    }

    @GET
    @Path("/buscarAlunoPorMatricula/{matricula}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON) //ok
    public Aluno buscarAluno(@PathParam("matricula") Long matricula){
        return alunoResource.buscar(matricula);
    }

    @GET
    @Path("/buscarTodosAlunos")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON) //ok
    public List<Aluno> buscarTodosOsAlunos(){
        return alunoResource.buscarTodosOsAlunos();
    }

    @GET
    @Path("/pesquisarAlunosPorCaractere/{caractere}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON) //ok
    public List<Aluno> pesquisarAlunos(@PathParam("caractere") String caractere){
        List<Aluno> alunos = alunoResource.pesquisarAlunos(caractere);
        if(alunos.isEmpty()){
             Response.noContent();
        }
            return alunos;
    }

    @DELETE
    @Path("/deletarAluno/{matricula}") //ok
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletarAluno(@PathParam("matricula") Long matricula){
        alunoResource.delete(matricula);
    }

    @PUT
    @Path("/atualizarDadosAluno") //ok
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Aluno uptadeAluno(Aluno aluno){
       return alunoResource.update(aluno);
    }

    @GET
    @Path("/mediaFinalAluno/{matricula}") //ok
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public AlunoDTO mediaFinalDoAluno(@PathParam("matricula") Long matricula){
        return alunoResource.mediaFinalDoAluno(matricula);
    }

    @GET
    @Path("/mediaFinalDeUmAlunoEmCadaBimestre/{matricula}") //ok
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public  List<NotaAlunoPorBimestreDTO> mediaFinalDeUmAlunoEmCadaBimestre
            (@PathParam("matricula") Long matricula){
        return alunoResource.mediaFinalDeUmAlunoEmCadaBimestre(matricula);
    }

    @GET
    @Path("/situacaoFinalDoAluno/{matricula}") //ok
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public AlunoSituacaoFinalDTO situacaoFinalDoAluno
            (@PathParam("matricula") Long matricula){
        return alunoResource.situacaoFinalDoAluno(matricula);
    }
}
