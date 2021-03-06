package org.txor.beerapi.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.beerapi.domain.exceptions.BadResourceDataException;
import org.txor.beerapi.domain.exceptions.ResourceNotFoundException;
import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.domain.model.Sort;
import org.txor.beerapi.repository.BeerDAO;
import org.txor.beerapi.repository.BeerDatabaseRepository;
import org.txor.beerapi.repository.converters.BeerEntityConverter;
import org.txor.beerapi.repository.entity.BeerEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.testutils.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.testutils.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.testutils.TestMother.BEER1_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER1_TYPE;
import static org.txor.beerapi.testutils.TestMother.BEER2_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER3_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER4_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER5_NAME;
import static org.txor.beerapi.testutils.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.testutils.TestMother.allBeerEntities;
import static org.txor.beerapi.testutils.TestMother.beer1;
import static org.txor.beerapi.testutils.TestMother.beer1Entity;

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
        beerService = new BeerService(beerRepository, null);
    }

    @Test
    public void getAllBeerNames_should_obtain_all_beer_names() {
        when(dao.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(allBeerEntities());

        List<String> names = beerService.getAllBeerNames(new Sort("name", "asc"), Collections.emptyList());

        assertThat(names.size()).isEqualTo(5);
        assertThat(names.get(0)).isEqualTo(BEER1_NAME);
        assertThat(names.get(1)).isEqualTo(BEER2_NAME);
        assertThat(names.get(2)).isEqualTo(BEER3_NAME);
        assertThat(names.get(3)).isEqualTo(BEER4_NAME);
        assertThat(names.get(4)).isEqualTo(BEER5_NAME);
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

    @Test
    public void getBeer_should_return_an_existing_beer() {
        when(dao.findById(anyString())).thenReturn(Optional.of(beer1Entity()));

        Beer beer = beerService.getBeer(MANUFACTURER1_NAME);

        assertThat(beer.getName()).isEqualTo(BEER1_NAME);
        assertThat(beer.getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(beer.getType()).isEqualTo(BEER1_TYPE);
        assertThat(beer.getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(beer.getManufacturer()).isEqualTo(MANUFACTURER1_NAME);
    }

    @Test
    public void getBeer_should_throw_an_exception_when_trying_to_fetch_a_non_existing_beer() {
        when(dao.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> beerService.getBeer(BEER1_NAME));
    }

    @Test
    public void updateBeer_should_update_an_existing_beer_with_the_given_data() {
        when(dao.existsById(anyString())).thenReturn(true);
        beerService.updateBeer(BEER1_NAME, beer1());

        verify(dao).save(beerCaptor.capture());
        assertThat(beerCaptor.getValue().getName()).isEqualTo(BEER1_NAME);
        assertThat(beerCaptor.getValue().getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(beerCaptor.getValue().getType()).isEqualTo(BEER1_TYPE);
        assertThat(beerCaptor.getValue().getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(beerCaptor.getValue().getManufacturer().getName()).isEqualTo(MANUFACTURER1_NAME);
    }

    @Test
    public void updateBeer_should_throw_an_exception_when_asked_to_update_a_non_existing_beer() {
        when(dao.existsById(anyString())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> beerService.updateBeer(BEER1_NAME, beer1())
        );
    }

    @Test
    public void updateBeer_should_throw_an_exception_when_asked_to_persist_the_beer_data_on_a_different_beer_name() {
        assertThrows(BadResourceDataException.class,
                () -> beerService.updateBeer("random beer name", beer1())
        );
    }

    @Test
    public void deleteBeer_should_delete_an_existing_beer() {
        when(dao.existsById(anyString())).thenReturn(true);

        beerService.deleteBeer(BEER1_NAME);

        verify(dao).deleteById(BEER1_NAME);
    }

    @Test
    public void deleteBeer_throw_exception_when_asked_to_delete_a_non_existing_beer() {
        when(dao.existsById(BEER1_NAME)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> beerService.deleteBeer(BEER1_NAME)
        );
    }
}
