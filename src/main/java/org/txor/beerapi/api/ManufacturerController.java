package org.txor.beerapi.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.txor.beerapi.domain.ManufacturerService;
import org.txor.beerapi.domain.converters.Manufacturer2ManufacturerDtoConverter;
import org.txor.beerapi.domain.dto.ManufacturerDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    Manufacturer2ManufacturerDtoConverter manufacturerConverter;

    @RequestMapping("/api/manufacturers")
    public List<String> getAllManufacturerNames() {
        return manufacturerService.getAllManufacturerNames();
    }

    @RequestMapping("/api/manufacturer/{name}")
    public ManufacturerDTO getManufacturer(@PathVariable String name) {
        return manufacturerConverter.convert(manufacturerService.getManufacturer(name));
    }

    @PostMapping(value = "/api/manufacturer")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManufacturer(@Valid @RequestBody ManufacturerDTO manufacturer) {
        manufacturerService.createManufacturer(manufacturer);
    }
}
