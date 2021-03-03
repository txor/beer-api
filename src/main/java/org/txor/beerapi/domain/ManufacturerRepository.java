package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository {

    List<String> getAllManufacturerNames();

    void saveManufacturer(Manufacturer manufacturer);

    Optional<Manufacturer> getManufacturer(String manufacturerName);
}
