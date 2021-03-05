package org.txor.beerapi.domain;

import org.txor.beerapi.domain.filter.Filter;
import org.txor.beerapi.domain.filter.NationalityFilter;
import org.txor.beerapi.domain.model.Beer;

import java.util.List;

public class FilterService {

    private final ManufacturerRepository manufacturerRepository;

    public FilterService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public boolean filter(List<Filter> filters, Beer beer) {

        return filters.stream().allMatch(filter -> {
            if (filter instanceof NationalityFilter) {
                manufacturerRepository.getManufacturer(beer.getManufacturer())
                        .ifPresent(value -> ((NationalityFilter) filter).setNationality(value.getNationality()));
            }
            return filter.filter(beer);
        });
    }
}
