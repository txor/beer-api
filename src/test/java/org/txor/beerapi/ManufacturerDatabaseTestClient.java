package org.txor.beerapi;

import org.springframework.data.repository.CrudRepository;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.Optional;

public interface ManufacturerDatabaseTestClient extends CrudRepository<ManufacturerEntity, String> {
    Optional<ManufacturerEntity> findById(String s);
}
