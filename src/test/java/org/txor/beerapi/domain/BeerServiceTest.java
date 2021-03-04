package org.txor.beerapi.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.beerapi.repository.BeerDAO;
import org.txor.beerapi.repository.BeerDatabaseRepository;
import org.txor.beerapi.repository.converters.BeerEntityConverter;
import org.txor.beerapi.repository.entity.BeerEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER1_TYPE;
import static org.txor.beerapi.TestMother.BEER2_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.allBeerNames;
import static org.txor.beerapi.TestMother.beer1;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    BeerDAO dao;

    @Captor
    private ArgumentCaptor<BeerEntity> beerCaptor;

    private BeerService beerService;

    @BeforeEach
    public void setUp() {
        BeerEntityConverter converter = new BeerEntityConverter();
        BeerRepository beerRepository = new BeerDatabaseRepository(dao, converter);
        beerService = new BeerService(beerRepository);
    }

    @Test
    public void getAllBeerNames_should_obtain_all_beer_names() {
        when(dao.getAllBeerNames()).thenReturn(allBeerNames());

        List<String> names = beerService.getAllBeerNames();

        assertThat(names.size()).isEqualTo(2);
        assertThat(names.get(0)).isEqualTo(BEER1_NAME);
        assertThat(names.get(1)).isEqualTo(BEER2_NAME);
    }

    @Test
    public void createBeer_should_persist_the_new_beer() {
        beerService.createBeer(beer1());

        verify(dao).save(beerCaptor.capture());
        assertThat(beerCaptor.getValue().getName()).isEqualTo(BEER1_NAME);
        assertThat(beerCaptor.getValue().getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(beerCaptor.getValue().getType()).isEqualTo(BEER1_TYPE);
        assertThat(beerCaptor.getValue().getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(beerCaptor.getValue().getManufacturer().getName()).isEqualTo(MANUFACTURER1_NAME);
    }
}
