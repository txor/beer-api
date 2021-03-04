package org.txor.beerapi.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.txor.beerapi.repository.entity.BeerEntity;

import java.util.List;

public interface BeerDAO extends PagingAndSortingRepository<BeerEntity, String> {
    List<BeerEntity> findAll(Sort sort);
}
