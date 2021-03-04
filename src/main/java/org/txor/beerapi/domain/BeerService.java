package org.txor.beerapi.domain;

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
        return null;
    }

    public void updateBeer(String name, Beer beerData) {
    }

    public void deleteBeer(String name) {
    }
}
