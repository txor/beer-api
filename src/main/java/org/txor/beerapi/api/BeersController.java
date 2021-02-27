package org.txor.beerapi.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BeersController {

    @RequestMapping("/api/beers")
    public List<String> getAllBeers() {
        return new ArrayList<>();
    }
}
