package org.txor.beerapi.domain;

import org.txor.beerapi.domain.model.Manufacturer;

import java.util.List;

public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<String> getAllManufacturerNames() {
        return manufacturerRepository.getAllManufacturerNames();
    }

    public void createManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.saveManufacturer(manufacturer);
    }

    public Manufacturer getManufacturer(String name) {
        return null;
    }

    public void updateManufacturer(String manufacturer, Manufacturer manufacturerInformation) {
    }

    public void deleteManufacturer(String manufacturer) {
    }
}
