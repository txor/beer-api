package org.txor.beerapi;

import org.txor.beerapi.domain.dto.BeerDTO;
import org.txor.beerapi.domain.dto.ManufacturerDTO;
import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.domain.model.Manufacturer;

import java.util.Arrays;
import java.util.List;

public class TestMother {

    public static final String BEER1_NAME = "Coronita";
    public static final float BEER1_GRADUATION = 4.5F;
    public static final String BEER1_TYPE = "Pale Lager";
    public static final String BEER1_DESCRIPTION = "Extra Coronita Mexican Beer is an even-keeled import beer with fruity-honey aromas and a touch of malt.";

    public static final String BEER2_NAME = "Franziskaner";
    public static final float BEER2_GRADUATION = 5F;
    public static final String BEER2_TYPE = "Wheat Beer";
    public static final String BEER2_DESCRIPTION = "Smells of doughy malt and estery fruit and spice, with notes of banana and clove, as well as lighter, herbal hops. Taste follows the nose, with a decent balance of doughy malt, estery fruit and spice, with distinct banana and clove notes and lighter grassy and herbal hops.";

    public static final String MANUFACTURER1_NAME = "Grupo Modelo, AB InBev";
    public static final String MANUFACTURER1_NATIONALITY = "Mexican";

    public static final String MANUFACTURER2_NAME = "Spaten-Franziskaner-Bräu GmbH";
    public static final String MANUFACTURER2_NATIONALITY = "German";

    public static List<Beer> someBeers() {
        return Arrays.asList(beer1(), beer2());
    }

    public static Beer beer1() {
        return new Beer(BEER1_NAME, BEER1_GRADUATION, BEER1_TYPE, BEER1_DESCRIPTION, manufacturer1());
    }

    public static BeerDTO beer1Dto() {
        return new BeerDTO(BEER1_NAME, BEER1_GRADUATION, BEER1_TYPE, BEER1_DESCRIPTION, manufacturer1Dto());
    }

    public static Beer beer2() {
        return new Beer(BEER2_NAME, BEER2_GRADUATION, BEER2_TYPE, BEER2_DESCRIPTION, manufacturer2());
    }

    public static BeerDTO beer2Dto() {
        return new BeerDTO(BEER2_NAME, BEER2_GRADUATION, BEER2_TYPE, BEER2_DESCRIPTION, manufacturer2Dto());
    }

    public static Manufacturer manufacturer1() {
        return new Manufacturer(MANUFACTURER1_NAME, MANUFACTURER1_NATIONALITY);
    }

    public static ManufacturerDTO manufacturer1Dto() {
        return new ManufacturerDTO(MANUFACTURER1_NAME, MANUFACTURER1_NATIONALITY);
    }

    public static Manufacturer manufacturer2() {
        return new Manufacturer(MANUFACTURER2_NAME, MANUFACTURER2_NATIONALITY);
    }

    public static ManufacturerDTO manufacturer2Dto() {
        return new ManufacturerDTO(MANUFACTURER2_NAME, MANUFACTURER2_NATIONALITY);
    }
}