package org.txor.beerapi.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.beerapi.domain.model.Manufacturer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER2_NAME;
import static org.txor.beerapi.TestMother.allManufacturerNames;
import static org.txor.beerapi.TestMother.manufacturer1;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceTest {

    @Mock
    ManufacturerRepository repository;

    @Test
    public void getAllManufacturerNames_should_obtain_all_names_from_the_repository() {
        when(repository.getAllManufacturerNames()).thenReturn(allManufacturerNames());
        ManufacturerService manufacturerService = new ManufacturerService(repository);

        List<String> names = manufacturerService.getAllManufacturerNames();

        assertThat(names.size()).isEqualTo(2);
        assertThat(names.get(0)).isEqualTo(MANUFACTURER1_NAME);
        assertThat(names.get(1)).isEqualTo(MANUFACTURER2_NAME);
    }

    @Test
    public void createManufacturer_should_call_the_repository() {
        ManufacturerService manufacturerService = new ManufacturerService(repository);

        manufacturerService.createManufacturer(manufacturer1());

        verify(repository).saveManufacturer(any(Manufacturer.class));
    }

    @Test
    public void getManufacturer_should_call_the_repository_with_the_given_name() {
        ManufacturerService manufacturerService = new ManufacturerService(repository);

        manufacturerService.getManufacturer(MANUFACTURER1_NAME);

        verify(repository).getManufacturer(MANUFACTURER1_NAME);
    }
}
