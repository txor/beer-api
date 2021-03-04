package org.txor.beerapi.repository;

import org.springframework.stereotype.Component;
import org.txor.beerapi.domain.BeerRepository;
import org.txor.beerapi.domain.model.Beer;
import org.txor.beerapi.repository.converters.BeerEntityConverter;

import java.util.List;
import java.util.Optional;

@Component
public class BeerDatabaseRepository implements BeerRepository {

    private final BeerDAO repository;
    private final BeerEntityConverter converter;

    public BeerDatabaseRepository(BeerDAO repository, BeerEntityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public List<String> getAllBeerNames() {
        return repository.getAllBeerNames();
    }

    @Override
    public void saveBeer(Beer beer) {
        repository.save(converter.convert(beer));
    }

    @Override
    public Optional<Beer> getBeer(String name) {
        return repository.findById(name).map(converter::convert);
    }

    @Override
    public void saveManufacturer(Beer beer) {
        repository.save(converter.convert(beer));
    }

    @Override
    public boolean existsBeer(String name) {
        return repository.existsById(name);
    }

    @Override
    public void deleteBeer(String name) {
        repository.deleteById(name);
    }
}
