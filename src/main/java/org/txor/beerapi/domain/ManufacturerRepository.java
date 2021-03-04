package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.domain.model.Sort;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository {

    List<String> getAllManufacturerNames(Sort sort);

    void saveManufacturer(Manufacturer manufacturer);

    Optional<Manufacturer> getManufacturer(String manufacturerName);

    void deleteManufacturer(String name);

    boolean existsManufacturer(String name);
}
