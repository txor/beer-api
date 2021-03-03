package org.txor.beerapi.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.beerapi.repository.BeerDAO;
import org.txor.beerapi.repository.BeerDatabaseRepository;
import org.txor.beerapi.repository.converters.BeerEntityConverter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER2_NAME;
import static org.txor.beerapi.TestMother.allBeerNames;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    BeerDAO repository;

    private BeerService beerService;

    @BeforeEach
    public void setUp() {
        BeerEntityConverter converter = new BeerEntityConverter();
        BeerDatabaseRepository beerDatabaseRepository = new BeerDatabaseRepository(repository, converter);
        beerService = new BeerService(beerDatabaseRepository);
    }

    @Test
    public void getAllBeerNames_should_obtain_all_beer_names() {
        when(repository.getAllBeerNames()).thenReturn(allBeerNames());

        List<String> names = beerService.getAllBeerNames();

        assertThat(names.size()).isEqualTo(2);
        assertThat(names.get(0)).isEqualTo(BEER1_NAME);
        assertThat(names.get(1)).isEqualTo(BEER2_NAME);
    }
}
