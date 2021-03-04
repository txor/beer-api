package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.repository.BeerDatabaseRepository;

import java.util.ArrayList;
import java.util.List;

public class BeerService {

    private final BeerDatabaseRepository repository;

    public BeerService(BeerDatabaseRepository repository) {
        this.repository = repository;
    }

    public List<String> getAllBeerNames() {
        return repository.getAllBeerNames();
    }

    public Beer getBeer(String name) {
        return null;
    }

    public void createBeer(Beer beer) {
    }

    public void updateBeer(String name, Beer beerData) {
    }

    public void deleteBeer(String name) {
    }
}
