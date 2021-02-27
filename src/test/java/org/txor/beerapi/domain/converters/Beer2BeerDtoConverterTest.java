package org.txor.beerapi.domain.converters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.beerapi.domain.dto.BeerDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.txor.beerapi.TestMother.BEER1_DESCRIPTION;
import static org.txor.beerapi.TestMother.BEER1_GRADUATION;
import static org.txor.beerapi.TestMother.BEER1_NAME;
import static org.txor.beerapi.TestMother.BEER1_TYPE;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NAME;
import static org.txor.beerapi.TestMother.MANUFACTURER1_NATIONALITY;
import static org.txor.beerapi.TestMother.beer1;
import static org.txor.beerapi.TestMother.manufacturer1Dto;

@ExtendWith(MockitoExtension.class)
class Beer2BeerDtoConverterTest {

    @Mock
    private Manufacturer2ManufacturerDtoConverter manufacturerConverter;

    @Test
    public void convert_should_correctly_convert_a_beer() {
        when(manufacturerConverter.convert(any())).thenReturn(manufacturer1Dto());
        Beer2BeerDtoConverter beer2BeerDtoConverter = new Beer2BeerDtoConverter(manufacturerConverter);

        BeerDTO convertedBeer = beer2BeerDtoConverter.convert(beer1());

        assertThat(convertedBeer.getName()).isEqualTo(BEER1_NAME);
        assertThat(convertedBeer.getGraduation()).isEqualTo(BEER1_GRADUATION);
        assertThat(convertedBeer.getType()).isEqualTo(BEER1_TYPE);
        assertThat(convertedBeer.getDescription()).isEqualTo(BEER1_DESCRIPTION);
        assertThat(convertedBeer.getManufacturer().getName()).isEqualTo(MANUFACTURER1_NAME);
        assertThat(convertedBeer.getManufacturer().getNationality()).isEqualTo(MANUFACTURER1_NATIONALITY);
    }
}
