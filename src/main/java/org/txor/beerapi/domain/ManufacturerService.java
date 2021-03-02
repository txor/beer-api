package org.txor.beerapi.domain;

import org.txor.beerapi.domain.dao.ManufacturerDAO;
import org.txor.beerapi.domain.model.Manufacturer;

import java.util.List;

public class ManufacturerService {

    private final ManufacturerDAO manufacturerDao;

    public ManufacturerService(ManufacturerDAO manufacturerDao) {
        this.manufacturerDao = manufacturerDao;
    }

    public List<String> getAllManufacturerNames() {
        return manufacturerDao.getAllManufacturerNames();
    }

    public void createManufacturer(Manufacturer manufacturer) {
    }

    public Manufacturer getManufacturer(String name) {
        return null;
    }

    public void updateManufacturer(String manufacturer, Manufacturer manufacturerInformation) {
    }

    public void deleteManufacturer(String manufacturer) {
    }
}
