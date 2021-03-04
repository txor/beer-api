package org.txor.beerapi.domain;

import org.txor.beerapi.domain.exceptions.BadResourceDataException;
import org.txor.beerapi.domain.exceptions.ResourceNotFoundException;
import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.domain.model.Sort;

import java.util.List;

public class BeerService {

    private final BeerRepository repository;

    public BeerService(BeerRepository repository) {
        this.repository = repository;
    }

    public List<String> getAllBeerNames(Sort sort) {
        return repository.getAllBeerNames(sort);
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
