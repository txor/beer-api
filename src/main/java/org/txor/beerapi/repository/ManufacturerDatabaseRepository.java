package org.txor.beerapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.txor.beerapi.domain.ManufacturerRepository;
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.repository.converters.ManufacturerEntityConverter;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<String> getAllManufacturerNames(org.txor.beerapi.domain.model.Sort sort) {
        Sort sortBy;
        if ("desc".equalsIgnoreCase(sort.getOrder())) {
            sortBy = Sort.by(Sort.Direction.DESC, sort.getSort());
        } else {
            sortBy = Sort.by(Sort.Direction.ASC, sort.getSort());
        }
        return manufacturerDAO.findAll(sortBy).stream()
                .map(ManufacturerEntity::getName).collect(Collectors.toList());
    }

    @Override
    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturerDAO.save(converter.convert(manufacturer));
    }

    @Override
    public Optional<Manufacturer> getManufacturer(String manufacturerName) {
        return manufacturerDAO.findById(manufacturerName).map(converter::convert);
    }

    @Override
    public void deleteManufacturer(String name) {
        manufacturerDAO.deleteById(name);
    }

    @Override
    public boolean existsManufacturer(String name) {
        return manufacturerDAO.existsById(name);
    }
}
