package org.txor.beerapi.domain.filter;

import org.txor.beerapi.domain.model.Beer;

public class NationalityFilter implements Filter {

    private final String nationality;
    private String beerNationality = "";

    public NationalityFilter(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public boolean filter(Beer beer) {
        return nationality.equalsIgnoreCase(beerNationality);
    }

    public void setNationality(String beerNationality) {
        this.beerNationality = beerNationality;
    }
}
