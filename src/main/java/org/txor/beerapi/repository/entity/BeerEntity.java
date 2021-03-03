package org.txor.beerapi.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "beer")
public class BeerEntity {

    @Id
    private String name;
    private Long graduation;
    private String type;
    private String description;
}
