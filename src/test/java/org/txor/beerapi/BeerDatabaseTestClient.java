package org.txor.beerapi;

import org.springframework.data.repository.CrudRepository;
import org.txor.beerapi.repository.entity.BeerEntity;

public interface BeerDatabaseTestClient extends CrudRepository<BeerEntity, String> {
}
