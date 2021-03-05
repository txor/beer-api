package org.txor.beerapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.txor.beerapi.api.converters.BeerConverter;
import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.domain.BeerService;
import org.txor.beerapi.domain.filter.Filter;
import org.txor.beerapi.domain.filter.ManufacturerFilter;
import org.txor.beerapi.domain.filter.NameFilter;
import org.txor.beerapi.domain.filter.NationalityFilter;
import org.txor.beerapi.domain.filter.TypeFilter;
import org.txor.beerapi.domain.model.Sort;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BeerController {

    @Autowired
    BeerService beerService;

    @Autowired
    BeerConverter beerConverter;

    @GetMapping("/api/beers")
    public List<String> getAllBeers(
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String nationality
    ) {
        List<Filter> filters = new ArrayList<>(4);
        if (name != null) {
            filters.add(new NameFilter(name));
        }
        if (type != null) {
            filters.add(new TypeFilter(type));
        }
        if (manufacturer != null) {
            filters.add(new ManufacturerFilter(manufacturer));
        }
        if (nationality != null) {
            filters.add(new NationalityFilter(nationality));
        }
        return beerService.getAllBeerNames(new Sort(sort, order), filters);
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

    @DeleteMapping("/api/beer/{name}")
    public void deleteBeer(@PathVariable String name) {
        beerService.deleteBeer(name);
    }
}
