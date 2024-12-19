package com.prod.services.carts.impl;

import com.prod.JPARepositories.carts.SmallQuantityRepository;
import com.prod.models.carts.Small_Quantity;
import com.prod.services.carts.ISmallQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.carts.SmallQuantityRepository.Specs.*;

@Service
public class SmallQuantityService implements ISmallQuantityService {
    @Autowired
    private SmallQuantityRepository repository;
    @Override
    public Small_Quantity create(Small_Quantity smallQuantity) {
        if(smallQuantity.getCreate_at() != smallQuantity.getUpdate_at()){
            smallQuantity.setUpdate_at(LocalDateTime.now());
        }
        return repository.save(smallQuantity);
    }

    @Override
    public Small_Quantity update(Small_Quantity smallQuantity, int quantity) {
        return repository.save(
                Small_Quantity.builder()
                        .id(smallQuantity.getId())
                        .color_size_product_id(smallQuantity.getColor_size_product_id())
                        .quantity_id(smallQuantity.getQuantity_id())
                        .quantity(smallQuantity.getQuantity() - quantity)
                        .sold(smallQuantity.getSold() + quantity)
                        .create_at(smallQuantity.getCreate_at())
                        .update_at(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public Optional<Small_Quantity> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Small_Quantity> getSmallQuantitiesByQuantityId(int id) {
        return repository.findAll(byQuantityId(id));
    }

    @Override
    public Optional<Small_Quantity> getByCSProductId(int cspId) {
        return repository.findOne(byCSPId(cspId));
    }

    @Override
    public List<Small_Quantity> getSmallQuantitiesByQuantityGreaterThan(int min) {
        return repository.findAll(byQuantityGreaterThanQuantity(min));
    }

    @Override
    public List<Small_Quantity> getSmallQuantitiesByQuantityLessThan(int max) {
        return repository.findAll(byQuantityLessThanQuantity(max));
    }

    @Override
    public List<Small_Quantity> getSmallQuantitiesByQuantityInRange(int min, int max) {
        return repository.findAll(byQuantityInRangeQuantity(min, max));
    }

    @Override
    public List<Small_Quantity> getSmallQuantitiesBySoldGreaterThan(int min) {
        return repository.findAll(byQuantityGreaterThanSold(min));
    }

    @Override
    public List<Small_Quantity> getSmallQuantitiesBySoldLessThan(int max) {
        return repository.findAll(byQuantityLessThanSold(max));
    }

    @Override
    public List<Small_Quantity> getSmallQuantitiesBySoldInRange(int min, int max) {
        return repository.findAll(byQuantityInRangeSold(min, max));
    }

    @Override
    public void deleteQuantityById(int id) {
        repository.deleteById(id);
    }
}
