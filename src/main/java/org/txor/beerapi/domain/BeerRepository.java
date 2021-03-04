package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerRepository {

    List<String> getAllBeerNames();

    void saveBeer(Beer beer);

    Optional<Beer> getBeer(String name);

    void saveManufacturer(Beer beer);

    boolean existsBeer(String name);
}
