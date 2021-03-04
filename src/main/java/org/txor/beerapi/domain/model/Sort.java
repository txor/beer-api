package org.txor.beerapi.domain.model;

public class Sort {

    private final String sort;
    private final String order;

    public Sort(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }
}
