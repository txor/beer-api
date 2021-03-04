package org.txor.beerapi.domain;

import org.txor.beerapi.domain.exceptions.BadBeerDataException;
import org.txor.beerapi.domain.exceptions.BeerNotFoundException;
import org.txor.beerapi.domain.model.Beer;

import java.util.List;

public class BeerService {

    private final BeerRepository repository;

    public BeerService(BeerRepository repository) {
        this.repository = repository;
    }

    public List<String> getAllBeerNames() {
        return repository.getAllBeerNames();
    }

    public void createBeer(Beer beer) {
        repository.saveBeer(beer);
    }

    public Beer getBeer(String name) {
        return repository.getBeer(name)
                .orElseThrow(() -> new BeerNotFoundException(name));
    }

    public void updateBeer(String name, Beer beer) {
        if (!name.equalsIgnoreCase(beer.getName())) {
            throw new BadBeerDataException(name);
        }
        if (!repository.existsBeer(name)) {
            throw new BeerNotFoundException(name);
        }
        repository.saveManufacturer(beer);
    }

    public void deleteBeer(String name) {
    }
}
