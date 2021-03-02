package org.txor.beerapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.txor.beerapi.domain.ManufacturerRepository;

import java.util.List;

@Component
public class ManufacturerDatabaseRepository implements ManufacturerRepository {

    @Autowired
    private ManufacturerDAO manufacturerDAO;

    @Override
    public List<String> getAllManufacturerNames() {
        return manufacturerDAO.getAllManufacturerNames();
    }
}
