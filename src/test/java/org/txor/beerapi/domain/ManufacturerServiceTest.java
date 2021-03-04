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
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.repository.ManufacturerDAO;
import org.txor.beerapi.repository.ManufacturerDatabaseRepository;
import org.txor.beerapi.repository.converters.ManufacturerEntityConverter;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.MANUFACTURER2_NAME;
import static org.txor.beerapi.TestMother.allManufacturerNames;
import static org.txor.beerapi.TestMother.manufacturer1;
import static org.txor.beerapi.TestMother.manufacturer1Entity;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceTest {

    @Mock
    ManufacturerDAO dao;

    @Captor
    private ArgumentCaptor<ManufacturerEntity> manufacturerCaptor;

    private ManufacturerService manufacturerService;

    @BeforeEach
    public void setUp() {
        ManufacturerEntityConverter converter = new ManufacturerEntityConverter();
        ManufacturerRepository manufacturerRepository = new ManufacturerDatabaseRepository(dao, converter);
        manufacturerService = new ManufacturerService(manufacturerRepository);
    }

    @Test
    public void getAllManufacturerNames_should_obtain_all_manufacturer_names() {
        when(dao.getAllManufacturerNames()).thenReturn(allManufacturerNames());

        List<String> names = manufacturerService.getAllManufacturerNames();

        assertThat(names.size()).isEqualTo(2);
        assertThat(names.get(0)).isEqualTo(MANUFACTURER1_NAME);
        assertThat(names.get(1)).isEqualTo(MANUFACTURER2_NAME);
    }

    @Test
    public void createManufacturer_should_persist_the_new_manufacturer() {
        manufacturerService.createManufacturer(manufacturer1());

        verify(dao).save(manufacturerCaptor.capture());
        assertThat(manufacturerCaptor.getValue().getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturerCaptor.getValue().getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }

    @Test
    public void getManufacturer_should_return_an_existing_manufacturer() {
        when(dao.findById(anyString())).thenReturn(Optional.of(manufacturer1Entity()));

        Manufacturer manufacturer = manufacturerService.getManufacturer(MANUFACTURER1_NAME);

        assertThat(manufacturer.getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturer.getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }

    @Test
    public void getManufacturer_should_throw_an_exception_when_trying_to_fetch_a_non_existing_manufacturer() {
        when(dao.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> manufacturerService.getManufacturer(MANUFACTURER1_NAME));
    }

    @Test
    public void updateManufacturer_should_update_an_existing_manufacturer_with_the_given_data() {
        when(dao.existsById(anyString())).thenReturn(true);
        manufacturerService.updateManufacturer(MANUFACTURER1_NAME, manufacturer1());

        verify(dao).save(manufacturerCaptor.capture());
        assertThat(manufacturerCaptor.getValue().getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturerCaptor.getValue().getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }

    @Test
    public void updateManufacturer_should_throw_an_exception_when_asked_to_update_a_non_existing_manufacturer() {
        when(dao.existsById(anyString())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> manufacturerService.updateManufacturer(MANUFACTURER1_NAME, manufacturer1())
        );
    }

    @Test
    public void updateManufacturer_should_throw_an_exception_when_asked_to_persist_the_manufacturer_data_on_a_different_manufacturer_name() {
        assertThrows(BadResourceDataException.class,
                () -> manufacturerService.updateManufacturer("random manufacturer name", manufacturer1())
        );
    }

    @Test
    public void deleteManufacturer_should_delete_an_existing_manufacturer() {
        when(dao.existsById(anyString())).thenReturn(true);

        manufacturerService.deleteManufacturer(MANUFACTURER1_NAME);

        verify(dao).deleteById(MANUFACTURER1_NAME);
    }

    @Test
    public void deleteManufacturer_throw_exception_when_asked_to_delete_a_non_existing_manufacturer() {
        when(dao.existsById(MANUFACTURER1_NAME)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> manufacturerService.deleteManufacturer(MANUFACTURER1_NAME)
        );
    }
}
