package org.txor.beerapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.ManufacturerService;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.api.converters.ManufacturerConverter;

@Configuration
public class BeerApiConfiguration {

    @Bean
    public BeerService beerService() {
        return new BeerService();
    }

    @Bean
    public ManufacturerService manufacturerService() {
        return new ManufacturerService();
    }

    @Bean
    public BeerConverter beerConverter(ManufacturerConverter manufacturerConverter) {
        return new BeerConverter(manufacturerConverter);
    }

    @Bean
    public ManufacturerConverter manufacturerConverter() {
        return new ManufacturerConverter();
    }
}
