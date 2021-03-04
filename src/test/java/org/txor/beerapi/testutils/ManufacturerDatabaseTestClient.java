package org.txor.beerapi.testutils;

import org.springframework.data.repository.CrudRepository;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

public interface ManufacturerDatabaseTestClient extends CrudRepository<ManufacturerEntity, String> {
}
