package org.txor.beerapi.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.repository.converters.ManufacturerEntityConverter;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.manufacturer1;
import static org.txor.beerapi.TestMother.manufacturer1Entity;

@ExtendWith(MockitoExtension.class)
class ManufacturerDatabaseRepositoryTest {

    @Mock
    private ManufacturerDAO dao;

    @Mock
    private ManufacturerEntityConverter converter;

    @Test
    void getAllManufacturerNames_should_call_the_dao() {
        ManufacturerDatabaseRepository manufacturerDatabaseRepository = new ManufacturerDatabaseRepository(dao, converter);

        manufacturerDatabaseRepository.getAllManufacturerNames();

        verify(dao).getAllManufacturerNames();
    }

    @Test
    void saveManufacturer_should_pass_the_dao_a_converted_entity() {
        when(converter.convert(any(Manufacturer.class))).thenReturn(manufacturer1Entity());
        ManufacturerDatabaseRepository manufacturerDatabaseRepository = new ManufacturerDatabaseRepository(dao, converter);

        manufacturerDatabaseRepository.saveManufacturer(manufacturer1());

        verify(converter).convert(any(Manufacturer.class));
        verify(dao).save(any(ManufacturerEntity.class));
    }

    @Test
    void getManufacturer_should_obtain_the_manufacturer_from_the_dao_and_convert_it_back() {
        when(dao.findById(anyString())).thenReturn(Optional.of(manufacturer1Entity()));
        when(converter.convert(any(ManufacturerEntity.class))).thenReturn(manufacturer1());
        ManufacturerDatabaseRepository manufacturerDatabaseRepository = new ManufacturerDatabaseRepository(dao, converter);

        Manufacturer manufacturer = manufacturerDatabaseRepository.getManufacturer(MANUFACTURER1_NAME);

        assertThat(manufacturer.getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(manufacturer.getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }
}