package org.txor.beerapi.api.converters;

import org.junit.jupiter.api.Test;
import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.domain.model.Beer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.txor.beerapi.testutils.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.testutils.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.testutils.TestMother.BEER1_NAME;
import static org.txor.beerapi.testutils.TestMother.BEER1_TYPE;
import static org.txor.beerapi.testutils.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.testutils.TestMother.beer1;
import static org.txor.beerapi.testutils.TestMother.beer1Dto;

class BeerConverterTest {

    @Test
    public void convert_should_correctly_convert_a_beer() {
        BeerConverter beerConverter = new BeerConverter();

        BeerDTO convertedBeer = beerConverter.convert(beer1());

        assertThat(convertedBeer.getName()).isEqualTo(BEER1_NAME);
        assertThat(convertedBeer.getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(convertedBeer.getType()).isEqualTo(BEER1_TYPE);
        assertThat(convertedBeer.getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(convertedBeer.getManufacturer()).isEqualTo(MANUFACTURER1_NAME);
    }

    @Test
    public void convert_should_correctly_convert_a_beerDto() {
        BeerConverter beerConverter = new BeerConverter();

        Beer convertedBeer = beerConverter.convert(beer1Dto());

        assertThat(convertedBeer.getName()).isEqualTo(BEER1_NAME);
        assertThat(convertedBeer.getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(convertedBeer.getType()).isEqualTo(BEER1_TYPE);
        assertThat(convertedBeer.getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(convertedBeer.getManufacturer()).isEqualTo(MANUFACTURER1_NAME);
    }
}
