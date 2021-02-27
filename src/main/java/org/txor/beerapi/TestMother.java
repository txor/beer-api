package org.txor.beerapi;

import org.txor.beerapi.domain.dto.BeerDTO;
import org.txor.beerapi.domain.model.Beer;

import java.util.Arrays;
import java.util.List;

public class TestMother {

    public static final String BEER1_NAME = "Coronita";
    public static final String BEER2_NAME = "Pilsner Urquell";

    public static List<Beer> someBeers() {
        return Arrays.asList(beer1(), beer2());
    }

    public static Beer beer1() {
        return new Beer();
    }

    public static Beer beer2() {
        return new Beer();
    }

    public static BeerDTO beerDto1() {
        return new BeerDTO(BEER1_NAME, 0, "", "", null);
    }

    public static BeerDTO beerDto2() {
        return new BeerDTO(BEER2_NAME, 0, "", "", null);
    }

    public static String beer1Name() {
        return BEER1_NAME;
    }

    public static String beer2Name() {
        return BEER2_NAME;
    }
}
