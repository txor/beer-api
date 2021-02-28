package org.txor.beerapi.api.converters;

import org.junit.jupiter.api.Test;
import org.txor.beerapi.api.dto.ManufacturerDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.manufacturer1;

class ManufacturerConverterTest {

    @Test
    public void convert_should_correctly_convert_a_manufacturer() {
        ManufacturerConverter manufacturerConverter = new ManufacturerConverter();

        ManufacturerDTO convertedManufacturer = manufacturerConverter.convert(manufacturer1());

        assertThat(convertedManufacturer.getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(convertedManufacturer.getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }
}
