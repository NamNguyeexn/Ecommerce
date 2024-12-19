package com.prod.services.details.impl;

import com.prod.JPARepositories.details.QuantityRepository;
import com.prod.models.details.Quantity;
import com.prod.services.details.IQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static com.prod.JPARepositories.details.QuantityRepository.Specs.*;

@Service
public class QuantityService implements IQuantityService {
    @Autowired
    private QuantityRepository repository;

    @Override
    public Quantity createQuantity(Quantity quantity) {
        if (quantity.getCreate_at() != quantity.getUpdate_at()) {
            quantity.setUpdate_at(LocalDateTime.now());
        }
        return repository.save(quantity);
    }

    @Override
    public Quantity updateQuantity(Quantity quantity) {
        return null;
    }

    @Override
    public Optional<Quantity> getQuantityById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Quantity> getQuantitiesGreaterThanQuantity(int min) {
        return repository.findAll(byQuantityGreaterThan(min));
    }

    @Override
    public List<Quantity> getQuantitiesLessThanQuantity(int max) {
        return repository.findAll(byQuantityGreaterThan(max));
    }

    @Override
    public List<Quantity> getQuantitiesInRangeQuantity(int min, int max) {
        return repository.findAll(byQuantityInRange(min, max));
    }

    @Override
    public List<Quantity> getQuantitiesGreaterThanSold(int min) {
        return repository.findAll(bySoldGreaterThan(min));
    }

    @Override
    public List<Quantity> getQuantitiesLessThanSold(int max) {
        return repository.findAll(bySoldSmallerThan(max));
    }

    @Override
    public List<Quantity> getQuantitiesInRangeSold(int min, int max) {
        return repository.findAll(bySoldInRange(min, max));
    }

    @Override
    public void deleteQuantityById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteQuantity(Quantity quantity) {
        repository.delete(quantity);
    }
}
