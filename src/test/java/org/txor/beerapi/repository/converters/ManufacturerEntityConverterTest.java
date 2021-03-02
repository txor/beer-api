package org.txor.beerapi.repository.converters;

import org.junit.jupiter.api.Test;
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.manufacturer1;
import static org.txor.beerapi.TestMother.manufacturer1Entity;

class ManufacturerEntityConverterTest {

    @Test
    public void convert_should_correctly_convert_a_manufacturer() {
        ManufacturerEntityConverter manufacturerEntityConverter = new ManufacturerEntityConverter();

        ManufacturerEntity convertedManufacturer = manufacturerEntityConverter.convert(manufacturer1());

        assertThat(convertedManufacturer.getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(convertedManufacturer.getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }

    @Test
    public void convert_should_correctly_convert_a_manufacturerEntity() {
        ManufacturerEntityConverter manufacturerEntityConverter = new ManufacturerEntityConverter();

        Manufacturer convertedManufacturer = manufacturerEntityConverter.convert(manufacturer1Entity());

        assertThat(convertedManufacturer.getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(convertedManufacturer.getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }
}