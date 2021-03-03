package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.repository.BeerDatabaseRepository;

import java.util.ArrayList;
import java.util.List;

public class BeerService {

    private final BeerDatabaseRepository beerDatabaseRepository;

    public BeerService(BeerDatabaseRepository beerDatabaseRepository) {
        this.beerDatabaseRepository = beerDatabaseRepository;
    }

    public List<String> getAllBeerNames() {
        return new ArrayList<>();
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
