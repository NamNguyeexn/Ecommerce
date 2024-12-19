package com.prod.services.details;

import com.prod.models.details.Quantity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IQuantityService {
    //create
    Quantity createQuantity(Quantity quantity);
    Quantity updateQuantity(Quantity quantity);
    //get
    Optional<Quantity> getQuantityById(int id);
    List<Quantity> getQuantitiesGreaterThanQuantity(int min);
    List<Quantity> getQuantitiesLessThanQuantity(int max);
    List<Quantity> getQuantitiesInRangeQuantity(int min, int max);
    List<Quantity> getQuantitiesGreaterThanSold(int min);
    List<Quantity> getQuantitiesLessThanSold(int max);
    List<Quantity> getQuantitiesInRangeSold(int min, int max);
    //delete
    void deleteQuantityById(int id);
    void deleteQuantity(Quantity quantity);
}
