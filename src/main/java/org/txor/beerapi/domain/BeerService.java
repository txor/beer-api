package org.txor.beerapi.domain;

import org.txor.beerapi.domain.exceptions.BadResourceDataException;
import org.txor.beerapi.domain.exceptions.ResourceNotFoundException;
import org.txor.beerapi.domain.filter.Filter;
import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.domain.model.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class BeerService {

    private final BeerRepository repository;
    private final FilterService filterService;

    public BeerService(BeerRepository repository, FilterService filterService) {
        this.repository = repository;
        this.filterService = filterService;
    }

    public List<String> getAllBeerNames(Sort sort, List<Filter> filters) {
        if (filters.isEmpty()) {
            return repository.getAllBeers(sort).stream()
                    .map(Beer::getName).collect(Collectors.toList());
        } else {
            return repository.getAllBeers(sort).stream()
                    .filter(beer -> filterService.filter(filters, beer))
                    .map(Beer::getName).collect(Collectors.toList());
        }
    }

    public void createBeer(Beer beer) {
        repository.saveBeer(beer);
    }

    public Beer getBeer(String name) {
        return repository.getBeer(name)
                .orElseThrow(() -> new ResourceNotFoundException(name));
    }

    public void updateBeer(String name, Beer beer) {
        if (!name.equalsIgnoreCase(beer.getName())) {
            throw new BadResourceDataException(name);
        }
        if (!repository.existsBeer(name)) {
            throw new ResourceNotFoundException(name);
        }
        repository.saveManufacturer(beer);
    }

    public void deleteBeer(String name) {
        if (!repository.existsBeer(name)) {
            throw new ResourceNotFoundException(name);
        }
        repository.deleteBeer(name);
    }
}
