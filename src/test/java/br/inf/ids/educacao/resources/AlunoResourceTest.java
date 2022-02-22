package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.enums.BimestreEnum;
import br.inf.ids.educacao.enums.SituacaoEnum;
import br.inf.ids.educacao.models.*;
import br.inf.ids.educacao.models.DTOS.AlunoDTO;
import br.inf.ids.educacao.models.DTOS.AlunoSituacaoFinalDTO;
import br.inf.ids.educacao.models.DTOS.NotaAlunoPorBimestreDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class AlunoResourceTest {

    @Inject
    AlunoResource alunoResource;

    @Inject
    TipoAvaliacaoResource tipoAvaliacaoResource;

    @Inject
    BimestreResource bimestreResource;

    @Inject
    AvaliacaoResource avaliacaoResource;

    @Inject
    PresencaResource presencaResource;

    @Inject
    AlunoBimestreResource alunoBimestreResource;

    @Test
    @Transactional
    void cadastrarAluno() {
        Aluno aluno = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");
        Assertions.assertEquals(aluno, alunoResource.cadastrarAluno(new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321")));
    }

    @Test
    @Transactional
    void buscar() {
        Aluno aluno = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");
        alunoResource.cadastrarAluno(aluno);
        Assertions.assertEquals(aluno, alunoResource.buscar(88888l));
    }

    @Test
    @Transactional
    void update() {
        Aluno aluno = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");
        alunoResource.cadastrarAluno(aluno);
        Aluno alunoAtualizado = new Aluno(88888l, "Eduardo", "eduardo@gmail.com", "1111111111");
        alunoResource.update(alunoAtualizado);
        Assertions.assertEquals(alunoAtualizado, alunoResource.buscar(88888l));
    }

    @Test
    @Transactional
    void delete() {
        Aluno aluno = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");
        alunoResource.cadastrarAluno(aluno);
        alunoResource.delete(88888l);

        Assertions.assertNull(alunoResource.buscar(88888l));
    }

    @Test
    @Transactional
    void buscarTodosOsAlunos() {
        Aluno aluno1 = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");
        Aluno aluno2 = new Aluno(99999l, "Joao", "joao@gmail.com", "333333333333");

        List<Aluno> todosOsAlunosAux = Arrays.asList(aluno1, aluno2);

        alunoResource.cadastrarAluno(aluno1);
        alunoResource.cadastrarAluno(aluno2);

        List<Aluno> todosOsAlunos = alunoResource.buscarTodosOsAlunos();

        Assertions.assertEquals(todosOsAlunosAux, todosOsAlunos);

    }

    @Test
    @Transactional
    void pesquisarAlunos() {
        Aluno aluno1 = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");
        Aluno aluno2 = new Aluno(99999l, "Joao", "joao@gmail.com", "333333333333");

        List<Aluno> todosOsAlunosAux = Arrays.asList(aluno1, aluno2);

        alunoResource.cadastrarAluno(aluno1);
        alunoResource.cadastrarAluno(aluno2);

        List<Aluno> todosOsAlunos = alunoResource.pesquisarAlunos("l");

        int cont = 0;
        for(int i = 0; i< todosOsAlunos.size(); i++){
            cont++;
        }

        Assertions.assertEquals(1, cont);
    }

    @Test
    @Transactional
    void mediaFinalDoAluno() {
        Aluno Pedro = new Aluno(99332737L, "Pedro", "pedro@gmail.com", "99257629");
        alunoResource.cadastrarAluno(Pedro);

        TipoAvaliacao partSalaDeAula = new TipoAvaliacao( "Participação em sala de aula", 1.5);
        TipoAvaliacao entregaDasTarefas = new TipoAvaliacao("Entrega das tarefas", 2.5);
        TipoAvaliacao trabalhoBimestral = new TipoAvaliacao("Trabalho bimestral", 3.0);
        TipoAvaliacao provaBimestral = new TipoAvaliacao("Prova bimestral", 3.0);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(partSalaDeAula);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(entregaDasTarefas);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(trabalhoBimestral);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(provaBimestral);

        Bimestre bimestre1 = new Bimestre(1l,BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Bimestre bimestre2 = new Bimestre(2l, BimestreEnum.BIMESTRE_2, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 6, 2));
        Bimestre bimestre3 = new Bimestre(3l,BimestreEnum.BIMESTRE_3, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 8, 30));
        Bimestre bimestre4 = new Bimestre(4l, BimestreEnum.BIMESTRE_4, LocalDate.of(2020, 10, 5), LocalDate.of(2020, 11, 14));
        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.cadastrarBimestre(bimestre2);
        bimestreResource.cadastrarBimestre(bimestre3);
        bimestreResource.cadastrarBimestre(bimestre4);

        Avaliacao avaliacao1PedroB1 = new Avaliacao( 6.0, partSalaDeAula, bimestre1, Pedro);
        Avaliacao avaliacao2PedroB1 = new Avaliacao(6.0, entregaDasTarefas, bimestre1, Pedro);
        Avaliacao avaliacao3PedroB1 = new Avaliacao(6.0, trabalhoBimestral, bimestre1, Pedro);
        Avaliacao avaliacao4PedroB1 = new Avaliacao(2.0, provaBimestral, bimestre1, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB1);

        Avaliacao avaliacao1PedroB2 = new Avaliacao(5.0, partSalaDeAula, bimestre2, Pedro);
        Avaliacao avaliacao2PedroB2 = new Avaliacao(6.0, entregaDasTarefas, bimestre2, Pedro);
        Avaliacao avaliacao3PedroB2 = new Avaliacao(6.0, trabalhoBimestral, bimestre2, Pedro);
        Avaliacao avaliacao4PedroB2 = new Avaliacao( 6.0, provaBimestral, bimestre2, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB2);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB2);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB2);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB2);

        Avaliacao avaliacao1PedroB3 = new Avaliacao( 6.0, partSalaDeAula, bimestre3, Pedro);
        Avaliacao avaliacao2PedroB3 = new Avaliacao( 6.0, entregaDasTarefas, bimestre3, Pedro);
        Avaliacao avaliacao3PedroB3 = new Avaliacao( 6.5, trabalhoBimestral, bimestre3, Pedro);
        Avaliacao avaliacao4PedroB3 = new Avaliacao( 4.0, provaBimestral, bimestre3, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB3);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB3);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB3);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB3);

        Avaliacao avaliacao1PedroB4 = new Avaliacao( 6.0, partSalaDeAula, bimestre4, Pedro);
        Avaliacao avaliacao2PedroB4 = new Avaliacao( 6.0, entregaDasTarefas, bimestre4, Pedro);
        Avaliacao avaliacao3PedroB4 = new Avaliacao( 6.5, trabalhoBimestral, bimestre4, Pedro);
        Avaliacao avaliacao4PedroB4 = new Avaliacao( 6.0, provaBimestral, bimestre4, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB4);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB4);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB4);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB4);

        AlunoBimestre alunoBimestre1 = new AlunoBimestre(Pedro, bimestre1);
        AlunoBimestre alunoBimestre2 = new AlunoBimestre(Pedro, bimestre2);
        AlunoBimestre alunoBimestre3 = new AlunoBimestre(Pedro, bimestre3);
        AlunoBimestre alunoBimestre4 = new AlunoBimestre(Pedro, bimestre4);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre1);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre2);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre3);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre4);


        AlunoDTO alunoDTO = alunoResource.mediaFinalDoAluno(Pedro.getMatricula());
        Assertions.assertEquals(5.59, alunoDTO.getMediaFinal());

    }

    @Test
    @Transactional
    void mediaFinalDeUmAlunoEmCadaBimestre() {

        Aluno Pedro = new Aluno(99332737L, "Pedro", "pedro@gmail.com", "99257629");
        alunoResource.cadastrarAluno(Pedro);

        TipoAvaliacao partSalaDeAula = new TipoAvaliacao( "Participação em sala de aula", 1.5);
        TipoAvaliacao entregaDasTarefas = new TipoAvaliacao("Entrega das tarefas", 2.5);
        TipoAvaliacao trabalhoBimestral = new TipoAvaliacao("Trabalho bimestral", 3.0);
        TipoAvaliacao provaBimestral = new TipoAvaliacao("Prova bimestral", 3.0);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(partSalaDeAula);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(entregaDasTarefas);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(trabalhoBimestral);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(provaBimestral);

        Bimestre bimestre1 = new Bimestre(1l,BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        bimestreResource.cadastrarBimestre(bimestre1);

        Avaliacao avaliacao1PedroB1 = new Avaliacao( 6.0, partSalaDeAula, bimestre1, Pedro);
        Avaliacao avaliacao2PedroB1 = new Avaliacao(6.0, entregaDasTarefas, bimestre1, Pedro);
        Avaliacao avaliacao3PedroB1 = new Avaliacao(6.0, trabalhoBimestral, bimestre1, Pedro);
        Avaliacao avaliacao4PedroB1 = new Avaliacao(2.0, provaBimestral, bimestre1, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB1);

        AlunoBimestre alunoBimestre1 = new AlunoBimestre(Pedro, bimestre1);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre1);

        alunoResource.cadastrarAluno(Pedro);

        List<NotaAlunoPorBimestreDTO> notaAlunoPorBimestreDTOS = alunoResource.mediaFinalDeUmAlunoEmCadaBimestre(Pedro.getMatricula());
        NotaAlunoPorBimestreDTO notaAlunoPorBimestreDTO =  notaAlunoPorBimestreDTOS.get(0);
        Assertions.assertEquals(4.8, notaAlunoPorBimestreDTO.getMediaFinal());

    }

    @Test
    @Transactional
    void situacaoFinalDoAluno() {

        Aluno Pedro = new Aluno(96543L, "Pedro", "pedro@gmail.com", "99257629");
        alunoResource.cadastrarAluno(Pedro);

        TipoAvaliacao partSalaDeAula = new TipoAvaliacao( "Participação em sala de aula", 1.5);
        TipoAvaliacao entregaDasTarefas = new TipoAvaliacao("Entrega das tarefas", 2.5);
        TipoAvaliacao trabalhoBimestral = new TipoAvaliacao("Trabalho bimestral", 3.0);
        TipoAvaliacao provaBimestral = new TipoAvaliacao("Prova bimestral", 3.0);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(partSalaDeAula);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(entregaDasTarefas);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(trabalhoBimestral);
        tipoAvaliacaoResource.cadastrarTipoAvaliacao(provaBimestral);

        Bimestre bimestre1 = new Bimestre(1l,BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Bimestre bimestre2 = new Bimestre(2l, BimestreEnum.BIMESTRE_2, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 6, 2));
        Bimestre bimestre3 = new Bimestre(3l,BimestreEnum.BIMESTRE_3, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 8, 30));
        Bimestre bimestre4 = new Bimestre(4l, BimestreEnum.BIMESTRE_4, LocalDate.of(2020, 10, 5), LocalDate.of(2020, 11, 14));
        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.cadastrarBimestre(bimestre2);
        bimestreResource.cadastrarBimestre(bimestre3);
        bimestreResource.cadastrarBimestre(bimestre4);

        Avaliacao avaliacao1PedroB1 = new Avaliacao( 6.0, partSalaDeAula, bimestre1, Pedro);
        Avaliacao avaliacao2PedroB1 = new Avaliacao(6.0, entregaDasTarefas, bimestre1, Pedro);
        Avaliacao avaliacao3PedroB1 = new Avaliacao(6.0, trabalhoBimestral, bimestre1, Pedro);
        Avaliacao avaliacao4PedroB1 = new Avaliacao(2.0, provaBimestral, bimestre1, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB1);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB1);

        Avaliacao avaliacao1PedroB2 = new Avaliacao(5.0, partSalaDeAula, bimestre2, Pedro);
        Avaliacao avaliacao2PedroB2 = new Avaliacao(6.0, entregaDasTarefas, bimestre2, Pedro);
        Avaliacao avaliacao3PedroB2 = new Avaliacao(6.0, trabalhoBimestral, bimestre2, Pedro);
        Avaliacao avaliacao4PedroB2 = new Avaliacao( 6.0, provaBimestral, bimestre2, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB2);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB2);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB2);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB2);

        Avaliacao avaliacao1PedroB3 = new Avaliacao( 6.0, partSalaDeAula, bimestre3, Pedro);
        Avaliacao avaliacao2PedroB3 = new Avaliacao( 6.0, entregaDasTarefas, bimestre3, Pedro);
        Avaliacao avaliacao3PedroB3 = new Avaliacao( 6.5, trabalhoBimestral, bimestre3, Pedro);
        Avaliacao avaliacao4PedroB3 = new Avaliacao( 4.0, provaBimestral, bimestre3, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB3);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB3);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB3);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB3);

        Avaliacao avaliacao1PedroB4 = new Avaliacao( 6.0, partSalaDeAula, bimestre4, Pedro);
        Avaliacao avaliacao2PedroB4 = new Avaliacao( 6.0, entregaDasTarefas, bimestre4, Pedro);
        Avaliacao avaliacao3PedroB4 = new Avaliacao( 6.5, trabalhoBimestral, bimestre4, Pedro);
        Avaliacao avaliacao4PedroB4 = new Avaliacao( 6.0, provaBimestral, bimestre4, Pedro);
        avaliacaoResource.cadastrarAvaliacao(avaliacao1PedroB4);
        avaliacaoResource.cadastrarAvaliacao(avaliacao2PedroB4);
        avaliacaoResource.cadastrarAvaliacao(avaliacao3PedroB4);
        avaliacaoResource.cadastrarAvaliacao(avaliacao4PedroB4);

        Presenca presencaPedroB1 = new Presenca(5, bimestre1, Pedro);
        Presenca presencaPedroB2 = new Presenca(4, bimestre2, Pedro);
        Presenca presencaPedroB3 = new Presenca(3, bimestre3, Pedro);
        Presenca presencaPedroB4 = new Presenca(2, bimestre4, Pedro);
        presencaResource.cadastrarPresenca(presencaPedroB1);
        presencaResource.cadastrarPresenca(presencaPedroB2);
        presencaResource.cadastrarPresenca(presencaPedroB3);
        presencaResource.cadastrarPresenca(presencaPedroB4);

        AlunoBimestre alunoBimestre1 = new AlunoBimestre(Pedro, bimestre1);
        AlunoBimestre alunoBimestre2 = new AlunoBimestre(Pedro, bimestre2);
        AlunoBimestre alunoBimestre3 = new AlunoBimestre(Pedro, bimestre3);
        AlunoBimestre alunoBimestre4 = new AlunoBimestre(Pedro, bimestre4);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre1);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre2);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre3);
        alunoBimestreResource.cadastrarAlunoBimestre(alunoBimestre4);

        alunoResource.cadastrarAluno(Pedro);

        AlunoSituacaoFinalDTO alunoSituacaoFinalDTO = alunoResource.situacaoFinalDoAluno(Pedro.getMatricula());

        Assertions.assertEquals(5.59, alunoSituacaoFinalDTO.getMediaFinal());
        Assertions.assertEquals(SituacaoEnum.RECUPERACAO, alunoSituacaoFinalDTO.getSituacaoFinal());
    }
}
