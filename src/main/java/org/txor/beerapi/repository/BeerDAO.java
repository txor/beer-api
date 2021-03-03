package org.txor.beerapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.txor.beerapi.repository.entity.BeerEntity;

import java.util.List;

public interface BeerDAO extends CrudRepository<BeerEntity, String> {
    @Query(nativeQuery = true, value = "SELECT name FROM manufacturer")
    List<String> getAllBeerNames();
}
