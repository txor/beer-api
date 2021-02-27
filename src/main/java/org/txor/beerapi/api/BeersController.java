package org.txor.beerapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.converters.Beer2BeerDtoConverter;
import org.txor.beerapi.domain.dto.BeerDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BeersController {

    @Autowired
    BeerService beerService;

    @Autowired
    Beer2BeerDtoConverter beer2BeerDtoConverter;

    @RequestMapping("/api/beers")
    public List<BeerDTO> getAllBeers() {
        return beerService.getAllBeers().stream().map(beer2BeerDtoConverter::convert).collect(Collectors.toList());
    }
}
