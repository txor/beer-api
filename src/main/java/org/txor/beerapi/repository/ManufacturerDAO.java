package org.txor.beerapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.List;

public interface ManufacturerDAO extends CrudRepository<ManufacturerEntity, String> {

    @Query(nativeQuery = true, value = "SELECT name FROM manufacturer")
    List<String> getAllManufacturerNames();
}
