package org.txor.beerapi.testutils;

import org.txor.beerapi.api.dto.BeerDTO;
import org.txor.beerapi.api.dto.ManufacturerDTO;
import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.domain.model.Manufacturer;
import org.txor.beerapi.repository.entity.BeerEntity;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.Arrays;
import java.util.List;

public class TestMother {

    public static final String BEER1_NAME = "A.K. Damm";
    public static final float BEER1_GRADUATION = 4.8F;
    public static final String BEER1_TYPE = "Alsatian";
    public static final String BEER1_DESCRIPTION = "A flavoursome, full-bodied beer with an elegant appearance, A.K. Damm comes in a unique, premium bottle.";

    public static final String BEER2_NAME = "Coronita";
    public static final float BEER2_GRADUATION = 4.5F;
    public static final String BEER2_TYPE = "Pale Lager";
    public static final String BEER2_DESCRIPTION = "Extra Coronita Mexican Beer is an even-keeled import beer with fruity-honey aromas and a touch of malt.";

    public static final String BEER3_NAME = "Estrella";
    public static final float BEER3_GRADUATION = 5.4F;
    public static final String BEER3_TYPE = "Lager Beer";
    public static final String BEER3_DESCRIPTION = "Estrella Damm is a lager beer, brewed in Barcelona, Spain. It has existed since 1876, when August Küntzmann Damm founded his brewery in Barcelona, and is the flagship beer of S.A. Damm";

    public static final String BEER4_NAME = "Franziskaner";
    public static final float BEER4_GRADUATION = 5F;
    public static final String BEER4_TYPE = "Wheat Beer";
    public static final String BEER4_DESCRIPTION = "Smells of doughy malt and estery fruit and spice, with notes of banana and clove, as well as lighter, herbal hops. Taste follows the nose, with a decent balance of doughy malt, estery fruit and spice, with distinct banana and clove notes and lighter grassy and herbal hops.";

    public static final String BEER5_NAME = "Voll Damm";
    public static final float BEER5_GRADUATION = 7.2F;
    public static final String BEER5_TYPE = "Märzenbier";
    public static final String BEER5_DESCRIPTION = "This robust, full-bodied beer emulates a German-style Vollbier and contains 7.2% alcohol by volume. Its dark-green colored labels display writing in the Gothic script.";

    public static final String MANUFACTURER1_NAME = "Damm S.A.";
    public static final String MANUFACTURER1_NATIONALITY = "Spanish";

    public static final String MANUFACTURER2_NAME = "Grupo Modelo, AB InBev";
    public static final String MANUFACTURER2_NATIONALITY = "Mexican";

    public static final String MANUFACTURER3_NAME = "Spaten-Franziskaner-Bräu GmbH";
    public static final String MANUFACTURER3_NATIONALITY = "German";


    public static List<String> allBeerNames() {
        return Arrays.asList(BEER1_NAME, BEER2_NAME, BEER3_NAME, BEER4_NAME, BEER5_NAME);
    }

    public static List<BeerEntity> allBeerEntities() {
        return Arrays.asList(
                new BeerEntity(BEER1_NAME, BEER1_GRADUATION, BEER1_TYPE, BEER1_DESCRIPTION, manufacturer3Entity()),
                new BeerEntity(BEER2_NAME, BEER2_GRADUATION, BEER2_TYPE, BEER2_DESCRIPTION, manufacturer2Entity()),
                new BeerEntity(BEER3_NAME, BEER3_GRADUATION, BEER3_TYPE, BEER3_DESCRIPTION, manufacturer3Entity()),
                new BeerEntity(BEER4_NAME, BEER4_GRADUATION, BEER4_TYPE, BEER4_DESCRIPTION, manufacturer1Entity()),
                new BeerEntity(BEER5_NAME, BEER5_GRADUATION, BEER5_TYPE, BEER5_DESCRIPTION, manufacturer3Entity())
        );
    }

    public static Beer beer1() {
        return new Beer(BEER1_NAME, BEER1_GRADUATION, BEER1_TYPE, BEER1_DESCRIPTION, MANUFACTURER1_NAME);
    }

    public static BeerDTO beer1Dto() {
        return new BeerDTO(BEER1_NAME, BEER1_GRADUATION, BEER1_TYPE, BEER1_DESCRIPTION, MANUFACTURER1_NAME);
    }

    public static BeerEntity beer1Entity() {
        return new BeerEntity(BEER1_NAME, BEER1_GRADUATION, BEER1_TYPE, BEER1_DESCRIPTION, new ManufacturerEntity(MANUFACTURER1_NAME, MANUFACTURER1_NATIONALITY));
    }

    public static String beer1JsonString() {
        return "{\"name\": \"" + BEER1_NAME + "\", " +
                "\"graduation\":\"" + BEER1_GRADUATION + "\", " +
                "\"type\":\"" + BEER1_TYPE + "\", " +
                "\"description\":\"" + BEER1_DESCRIPTION + "\", " +
                "\"manufacturer\":\"" + MANUFACTURER1_NAME +
                "\"}";
    }

    public static List<ManufacturerEntity> allManufacturerEntities() {
        return Arrays.asList(
                new ManufacturerEntity(MANUFACTURER1_NAME, MANUFACTURER1_NATIONALITY),
                new ManufacturerEntity(MANUFACTURER2_NAME, MANUFACTURER2_NATIONALITY),
                new ManufacturerEntity(MANUFACTURER3_NAME, MANUFACTURER3_NATIONALITY));
    }

    public static Manufacturer manufacturer1() {
        return new Manufacturer(MANUFACTURER1_NAME, MANUFACTURER1_NATIONALITY);
    }

    public static ManufacturerDTO manufacturer1Dto() {
        return new ManufacturerDTO(MANUFACTURER1_NAME, MANUFACTURER1_NATIONALITY);
    }

    public static ManufacturerEntity manufacturer1Entity() {
        return new ManufacturerEntity(MANUFACTURER1_NAME, MANUFACTURER1_NATIONALITY);
    }


    public static String manufacturer1JsonString() {
        return "{\"name\": \"" + MANUFACTURER1_NAME + "\", \"nationality\":\"" + MANUFACTURER1_NATIONALITY + "\"}";
    }

    public static ManufacturerEntity manufacturer2Entity() {
        return new ManufacturerEntity(MANUFACTURER2_NAME, MANUFACTURER2_NATIONALITY);
    }

    public static ManufacturerEntity manufacturer3Entity() {
        return new ManufacturerEntity(MANUFACTURER3_NAME, MANUFACTURER3_NATIONALITY);
    }
}
