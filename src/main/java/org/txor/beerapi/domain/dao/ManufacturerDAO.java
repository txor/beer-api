package org.txor.beerapi.domain.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.txor.beerapi.repository.entity.Manufacturer;

import java.util.List;

public interface ManufacturerDAO extends CrudRepository<Manufacturer, String> {

    @Query(nativeQuery = true, value = "SELECT name FROM manufacturer")
    List<String> getAllManufacturerNames();
}
