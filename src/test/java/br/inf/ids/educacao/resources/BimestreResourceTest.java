package br.inf.ids.educacao.resources;

import br.inf.ids.educacao.enums.BimestreEnum;
import br.inf.ids.educacao.models.Aluno;
import br.inf.ids.educacao.models.Bimestre;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class BimestreResourceTest {

    @Inject
    BimestreResource bimestreResource;

    @Test
    @Transactional
    void cadastrarBimestre() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Assertions.assertEquals(bimestre1, bimestreResource.cadastrarBimestre(bimestre1));
    }

    @Test
    @Transactional
    void buscarBimestre() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.buscarBimestre(1l);
    }

    @Test
    @Transactional
    void updateBimestre() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        bimestreResource.cadastrarBimestre(bimestre1);
        Bimestre bimestreAtualizado = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2021, 2, 4),LocalDate.of(2021, 3, 15));
        bimestreResource.updateBimestre(bimestreAtualizado);
        Assertions.assertEquals(bimestreAtualizado, bimestreResource.buscarBimestre(1l));
    }

    @Test
    @Transactional
    void deleteBimestre() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.deleteBimestre(1l);

        Assertions.assertNull(bimestreResource.buscarBimestre(1l));
    }

    @Test
    @Transactional
    void listarBimestresPorData() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Bimestre bimestre2 = new Bimestre(2l, BimestreEnum.BIMESTRE_2, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 6, 2));
        Bimestre bimestre3 = new Bimestre(3l,BimestreEnum.BIMESTRE_3, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 8, 30));
        Bimestre bimestre4 = new Bimestre(4l,BimestreEnum.BIMESTRE_4, LocalDate.of(2020, 10, 5), LocalDate.of(2020, 11, 14));

        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.cadastrarBimestre(bimestre2);
        bimestreResource.cadastrarBimestre(bimestre3);
        bimestreResource.cadastrarBimestre(bimestre4);

        List<Bimestre> bimestres = bimestreResource.listarBimestresPorData();

        for(Bimestre bimestre : bimestres){
            if(bimestre.getInicioBimestre().isAfter(bimestre.getFimBimestre())){
                Assertions.fail("Data de inicio está cadastrada depois da data de fim");
            }
        }
    }

    @Test
    @Transactional
    void contarBimestres() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Bimestre bimestre2 = new Bimestre(2l, BimestreEnum.BIMESTRE_2, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 6, 2));
        Bimestre bimestre3 = new Bimestre(3l,BimestreEnum.BIMESTRE_3, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 8, 30));
        Bimestre bimestre4 = new Bimestre(4l,BimestreEnum.BIMESTRE_4, LocalDate.of(2020, 10, 5), LocalDate.of(2020, 11, 14));

        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.cadastrarBimestre(bimestre2);
        bimestreResource.cadastrarBimestre(bimestre3);
        bimestreResource.cadastrarBimestre(bimestre4);

        Long totalDeBimestres = bimestreResource.contarBimestres();
        Assertions.assertEquals(4, totalDeBimestres);

    }

    @Test
    @Transactional
    void totalDeDiasLetivos() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));
        Bimestre bimestre2 = new Bimestre(2l, BimestreEnum.BIMESTRE_2, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 6, 2));
        Bimestre bimestre3 = new Bimestre(3l,BimestreEnum.BIMESTRE_3, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 8, 30));
        Bimestre bimestre4 = new Bimestre(4l,BimestreEnum.BIMESTRE_4, LocalDate.of(2020, 10, 5), LocalDate.of(2020, 11, 14));

        bimestreResource.cadastrarBimestre(bimestre1);
        bimestreResource.cadastrarBimestre(bimestre2);
        bimestreResource.cadastrarBimestre(bimestre3);
        bimestreResource.cadastrarBimestre(bimestre4);

        long diasLetivos = bimestreResource.totalDeDiasLetivos();
        Assertions.assertEquals(160,diasLetivos);
    }

    @Test
    @Transactional
    void diasLetivosBimestre() {
        Bimestre bimestre1 = new Bimestre(1l, BimestreEnum.BIMESTRE_1, LocalDate.of(2020, 2, 4), LocalDate.of(2020, 3, 15));

        bimestreResource.cadastrarBimestre(bimestre1);

            Assertions.assertEquals(40, bimestreResource.diasLetivosBimestre(bimestre1.getId()));

    }
}
