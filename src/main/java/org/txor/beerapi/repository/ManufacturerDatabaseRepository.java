package org.txor.beerapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.txor.beerapi.domain.ManufacturerRepository;
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.repository.converters.ManufacturerEntityConverter;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.List;
import java.util.Optional;

@Component
public class ManufacturerDatabaseRepository implements ManufacturerRepository {

    private final ManufacturerDAO manufacturerDAO;
    private final ManufacturerEntityConverter converter;

    @Autowired
    public ManufacturerDatabaseRepository(ManufacturerDAO manufacturerDAO, ManufacturerEntityConverter converter) {
        this.manufacturerDAO = manufacturerDAO;
        this.converter = converter;
    }

    @Override
    public List<String> getAllManufacturerNames() {
        return manufacturerDAO.getAllManufacturerNames();
    }

    @Override
    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturerDAO.save(converter.convert(manufacturer));
    }

    @Override
    public Manufacturer getManufacturer(String manufacturerName) {
        Optional<ManufacturerEntity> entity = manufacturerDAO.findById(manufacturerName);
        return converter.convert(entity.get());
    }
}
