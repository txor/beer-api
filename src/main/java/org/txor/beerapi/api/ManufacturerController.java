package org.txor.beerapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txor.beerapi.domain.ManufacturerService;

import java.util.List;

@RestController
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;

    @RequestMapping("/api/manufacturers")
    public List<String> getAllManufacturerNames() {
        return manufacturerService.getAllManufacturerNames();
    }
}
