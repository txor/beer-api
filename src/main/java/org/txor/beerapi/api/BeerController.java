package org.txor.beerapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.domain.BeerService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BeerController {

    @Autowired
    BeerService beerService;

    @Autowired
    BeerConverter beerConverter;

    @GetMapping("/api/beers")
    public List<String> getAllBeers() {
        return beerService.getAllBeerNames();
    }

    @PostMapping("/api/beer")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBeer(@Valid @RequestBody BeerDTO beer) {
        beerService.createBeer(beerConverter.convert(beer));
    }

    @GetMapping("/api/beer/{name}")
    public BeerDTO retrieveBeer(@PathVariable String name) {
        return beerConverter.convert(beerService.getBeer(name));
    }

    @PutMapping("/api/beer/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeer(@PathVariable String name, @Valid @RequestBody BeerDTO beer) {
        beerService.updateBeer(name, beerConverter.convert(beer));
    }
}
