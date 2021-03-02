package org.txor.beerapi;

import org.springframework.data.repository.CrudRepository;
import org.txor.beerapi.repository.entity.Manufacturer;

import java.util.Optional;

public interface ManufacturerDatabaseTestClient extends CrudRepository<Manufacturer, String> {
    Optional<Manufacturer> findById(String s);
}
