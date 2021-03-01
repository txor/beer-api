package org.txor.beerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.api.converters.ManufacturerConverter;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.ManufacturerService;
import org.txor.beerapi.domain.dao.ManufacturerDAO;

@Configuration
public class BeerApiConfiguration {

    @Autowired
    ManufacturerDAO manufacturerDAO;

    @Bean
    public BeerService beerService() {
        return new BeerService();
    }

    @Bean
    public ManufacturerService manufacturerService() {
        return new ManufacturerService(manufacturerDAO);
    }

    @Bean
    public BeerConverter beerConverter(ManufacturerConverter manufacturerConverter) {
        return new BeerConverter();
    }

    @Bean
    public ManufacturerConverter manufacturerConverter() {
        return new ManufacturerConverter();
    }
}
