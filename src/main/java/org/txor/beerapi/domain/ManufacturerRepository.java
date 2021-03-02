package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Manufacturer;

import java.util.List;

public interface ManufacturerRepository {

    List<String> getAllManufacturerNames();

    void saveManufacturer(Manufacturer manufacturer);
}