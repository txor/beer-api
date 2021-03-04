package org.txor.beerapi.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.txor.beerapi.repository.entity.ManufacturerEntity;

import java.util.List;

public interface ManufacturerDAO extends PagingAndSortingRepository<ManufacturerEntity, String> {
    List<ManufacturerEntity> findAll(Sort sort);
}
