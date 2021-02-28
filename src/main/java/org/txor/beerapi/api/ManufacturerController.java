package org.txor.beerapi.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.txor.beerapi.api.converters.ManufacturerConverter;
import org.txor.beerapi.api.dto.ManufacturerDTO;
import org.txor.beerapi.domain.ManufacturerService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    ManufacturerConverter manufacturerConverter;

    @GetMapping(value = "/api/manufacturers")
    public List<String> getAllManufacturerNames() {
        return manufacturerService.getAllManufacturerNames();
    }

    @GetMapping(value = "/api/manufacturer/{name}")
    public ManufacturerDTO getManufacturer(@PathVariable String name) {
        return manufacturerConverter.convert(manufacturerService.getManufacturer(name));
    }

    @PostMapping(value = "/api/manufacturer")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManufacturer(@Valid @RequestBody ManufacturerDTO manufacturer) {
        manufacturerService.createManufacturer(manufacturerConverter.convert(manufacturer));
    }

    @PutMapping(value = "/api/manufacturer/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateManufacturer(@PathVariable String name, @Valid @RequestBody ManufacturerDTO manufacturer) {
        manufacturerService.updateManufacturer(name, manufacturerConverter.convert(manufacturer));
    }

    @DeleteMapping(value = "/api/manufacturer/{name}")
    public void deleteManufacturer(@PathVariable String name) {
        manufacturerService.deleteManufacturer(name);
    }
}
