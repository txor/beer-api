package org.txor.beerapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.domain.BeerService;

import java.util.List;

@RestController
public class BeerController {

    @Autowired
    BeerService beerService;

    @Autowired
    BeerConverter beerConverter;

    @RequestMapping("/api/beers")
    public List<String> getAllBeers() {
        return beerService.getAllBeerNames();
    }

    @RequestMapping("/api/beer/{name}")
    public BeerDTO getBeer(@PathVariable String name) {
        return beerConverter.convert(beerService.getBeer(name));
    }
}
