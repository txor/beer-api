package org.txor.beerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.api.converters.ManufacturerConverter;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.FilterService;
import org.txor.beerapi.domain.ManufacturerService;
import org.txor.beerapi.repository.BeerDatabaseRepository;
import org.txor.beerapi.repository.ManufacturerDatabaseRepository;

@Configuration
public class BeerApiConfiguration {

    @Autowired
    BeerDatabaseRepository beerDatabaseRepository;

    @Autowired
    ManufacturerDatabaseRepository manufacturerDatabaseRepository;

    @Bean
    public BeerService beerService(FilterService filterService) {
        return new BeerService(beerDatabaseRepository, filterService);
    }

    @Bean
    public FilterService filterService() {
        return new FilterService(manufacturerDatabaseRepository);
    }

    @Bean
    public ManufacturerService manufacturerService() {
        return new ManufacturerService(manufacturerDatabaseRepository);
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
