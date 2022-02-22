package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.enums.BimestreEnum;
import br.inf.ids.educacao.models.Aluno;
import br.inf.ids.educacao.models.Avaliacao;
import br.inf.ids.educacao.models.Bimestre;
import br.inf.ids.educacao.models.DTOS.FaltasAlunoDTO;
import br.inf.ids.educacao.models.DTOS.TotalDeFaltasDTO;
import br.inf.ids.educacao.models.Presenca;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class PresencaResourceTest {

    @Inject
    PresencaResource presencaResource;

    @Inject
    BimestreResource bimestreResource;

    @Inject
    AlunoResource alunoResource;

    @Test
    @Transactional
    void cadastrarPresenca() {
        Presenca presenca = new Presenca(29);
        Assertions.assertEquals(presenca, presencaResource.cadastrarPresenca(presenca));

    }

    @Test
    @Transactional
    void buscarPresenca() {
        Presenca presenca = new Presenca(29);
        presencaResource.cadastrarPresenca(presenca);
        Assertions.assertEquals(presenca, presencaResource.buscarPresenca(presenca.getId()));

    }

    @Test
    @Transactional
    void updatePresenca() {
        Presenca presenca = new Presenca(29);
        presencaResource.cadastrarPresenca(presenca);
        Presenca presencaAtualizada = presencaResource.buscarPresenca(presenca.getId());
        presencaAtualizada.setNumeroDeFaltas(20);
        presencaResource.updatePresenca(presencaAtualizada);
        Assertions.assertEquals(presencaAtualizada, presencaResource.buscarPresenca(presencaAtualizada.getId()));

    }

    @Test
    @Transactional
    void deletePresenca() {
        Presenca presenca = new Presenca(29);
        presencaResource.cadastrarPresenca(presenca);
        presencaResource.deletePresenca(presenca.getId());
        Assertions.assertNull(presencaResource.buscarPresenca(presenca.getId()));

    }

    @Test
    @Transactional
    void faltasPorBimestreDeUmAluno() {
        Aluno aluno = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");

        alunoResource.cadastrarAluno(aluno);

        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Bimestre bimestre2 = new Bimestre(2l, BimestreEnum.BIMESTRE_2, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 6, 2));
        Bimestre bimestre3 = new Bimestre(3l,BimestreEnum.BIMESTRE_3, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 8, 30));
        Bimestre bimestre4 = new Bimestre(4l,BimestreEnum.BIMESTRE_4, LocalDate.of(2020, 10, 5), LocalDate.of(2020, 11, 14));

        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.cadastrarBimestre(bimestre2);
        bimestreResource.cadastrarBimestre(bimestre3);
        bimestreResource.cadastrarBimestre(bimestre4);

        Presenca presenca1 = new Presenca(1, bimestre1, aluno);
        Presenca presenca2 = new Presenca(2, bimestre2, aluno);
        Presenca presenca3 = new Presenca(3, bimestre3, aluno);
        Presenca presenca4 = new Presenca(4, bimestre4, aluno);

        presencaResource.cadastrarPresenca(presenca1);
        presencaResource.cadastrarPresenca(presenca2);
        presencaResource.cadastrarPresenca(presenca3);
        presencaResource.cadastrarPresenca(presenca4);

        List<FaltasAlunoDTO> faltasAluno = presencaResource.faltasPorBimestreDeUmAluno(88888l);

        for(FaltasAlunoDTO faltasAlunoDTO : faltasAluno){
            if(faltasAlunoDTO.getNumeroDeFaltas() > bimestreResource.diasLetivosBimestre(faltasAlunoDTO.getBimestre()) ||
            faltasAlunoDTO.getNumeroDeFaltas() < 0){
                Assertions.fail("numero de faltas é maior que o numero de dias letivos do bimestre ou o numero de faltas é invalido");
            }
        }
    }

    @Test
    @Transactional
    void totalDeFaltasDeUmAluno() {
        Aluno aluno = new Aluno(88888l, "Leonardo", "leonardo@gmail.com", "321321321");

        alunoResource.cadastrarAluno(aluno);

        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Bimestre bimestre2 = new Bimestre(2l, BimestreEnum.BIMESTRE_2, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 6, 2));
        Bimestre bimestre3 = new Bimestre(3l,BimestreEnum.BIMESTRE_3, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 8, 30));
        Bimestre bimestre4 = new Bimestre(4l,BimestreEnum.BIMESTRE_4, LocalDate.of(2020, 10, 5), LocalDate.of(2020, 11, 14));

        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.cadastrarBimestre(bimestre2);
        bimestreResource.cadastrarBimestre(bimestre3);
        bimestreResource.cadastrarBimestre(bimestre4);

        Presenca presenca1 = new Presenca(1, bimestre1, aluno);
        Presenca presenca2 = new Presenca(2, bimestre2, aluno);
        Presenca presenca3 = new Presenca(3, bimestre3, aluno);
        Presenca presenca4 = new Presenca(4, bimestre4, aluno);

        presencaResource.cadastrarPresenca(presenca1);
        presencaResource.cadastrarPresenca(presenca2);
        presencaResource.cadastrarPresenca(presenca3);
        presencaResource.cadastrarPresenca(presenca4);

        TotalDeFaltasDTO totalDeFaltasDTO = presencaResource.totalDeFaltasDeUmAluno(aluno.getMatricula());
        if(totalDeFaltasDTO.getTotalDeFaltas() > bimestreResource.totalDeDiasLetivos()){
            Assertions.fail("as faltas excederam o total de dias letivos");
        }
    }
}