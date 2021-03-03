package org.txor.beerapi.domain;

import org.txor.beerapi.domain.exceptions.ManufacturerNotFoundException;
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
        return manufacturerRepository.getManufacturer(name)
                .orElseThrow(() -> new ManufacturerNotFoundException(name));
    }

    public void updateManufacturer(String name, Manufacturer manufacturer) {

        manufacturerRepository.updateManufacturer(name, manufacturer);
    }

    public void deleteManufacturer(String manufacturer) {
    }
}
