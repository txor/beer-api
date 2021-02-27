package org.txor.beerapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.converters.Beer2BeerDtoConverter;
import org.txor.beerapi.domain.converters.Manufacturer2ManufacturerDtoConverter;

@Configuration
public class BeerApiConfiguration {

    @Bean
    public BeerService beerService() {
        return new BeerService();
    }

    @Bean
    public Beer2BeerDtoConverter beerConverter(Manufacturer2ManufacturerDtoConverter manufacturerConverter) {
        return new Beer2BeerDtoConverter(manufacturerConverter);
    }

    @Bean
    public Manufacturer2ManufacturerDtoConverter manufacturerConverter() {
        return new Manufacturer2ManufacturerDtoConverter();
    }
}
