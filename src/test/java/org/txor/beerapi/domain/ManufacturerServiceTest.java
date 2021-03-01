package org.txor.beerapi.domain;

import org.junit.jupiter.api.Test;
import org.txor.beerapi.domain.dao.ManufacturerDAO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER2_NAME;
import static org.txor.beerapi.TestMother.allManufacturerNames;

class ManufacturerServiceTest {

    @Test
    public void getAllManufacturerNames_should_obtain_all_names_from_the_dao() {
        ManufacturerDAO dao = mock(ManufacturerDAO.class);
        when(dao.getAllManufacturerNames()).thenReturn(allManufacturerNames());
        ManufacturerService manufacturerService = new ManufacturerService(dao);

        List<String> names = manufacturerService.getAllManufacturerNames();

        assertThat(names.size()).isEqualTo(2);
        assertThat(names.get(0)).isEqualTo(MANUFACTURER1_NAME);
        assertThat(names.get(1)).isEqualTo(MANUFACTURER2_NAME);
    }
}
