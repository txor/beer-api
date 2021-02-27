package org.txor.beerapi.domain.converters;

import org.junit.jupiter.api.Test;
import org.txor.beerapi.domain.dto.ManufacturerDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.manufacturer1;

class Manufacturer2ManufacturerDtoConverterTest {

    @Test
    public void convert_should_correctly_convert_a_manufacturer() {
        Manufacturer2ManufacturerDtoConverter manufacturerConverter = new Manufacturer2ManufacturerDtoConverter();

        ManufacturerDTO convertedManufacturer = manufacturerConverter.convert(manufacturer1());

        assertThat(convertedManufacturer.getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(convertedManufacturer.getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }
}
