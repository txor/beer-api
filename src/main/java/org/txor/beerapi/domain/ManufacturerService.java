package org.txor.beerapi.domain;

import org.txor.beerapi.domain.exceptions.BadResourceDataException;
import org.txor.beerapi.domain.exceptions.ResourceNotFoundException;
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.domain.model.Sort;

import java.util.List;

public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<String> getAllManufacturerNames(Sort sort) {
        return manufacturerRepository.getAllManufacturerNames(sort);
    }

    public void createManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.saveManufacturer(manufacturer);
    }

    public Manufacturer getManufacturer(String name) {
        return manufacturerRepository.getManufacturer(name)
                .orElseThrow(() -> new ResourceNotFoundException(name));
    }

    public void updateManufacturer(String name, Manufacturer manufacturer) {
        if (!name.equalsIgnoreCase(manufacturer.getName())) {
            throw new BadResourceDataException(name);
        }
        if (!manufacturerRepository.existsManufacturer(name)) {
            throw new ResourceNotFoundException(name);
        }
        manufacturerRepository.saveManufacturer(manufacturer);
    }

    public void deleteManufacturer(String name) {
        if (!manufacturerRepository.existsManufacturer(name)) {
            throw new ResourceNotFoundException(name);
        }
        manufacturerRepository.deleteManufacturer(name);
    }
}
