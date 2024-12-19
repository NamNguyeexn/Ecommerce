package com.prod.services.carts;

import com.prod.models.carts.Small_Quantity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISmallQuantityService {
    //create
    Small_Quantity create(Small_Quantity smallQuantity);
    //read
    Small_Quantity update(Small_Quantity smallQuantity, int quantity);
    Optional<Small_Quantity> getById(int id);
    List<Small_Quantity> getSmallQuantitiesByQuantityId(int id);
    Optional<Small_Quantity> getByCSProductId(int cspId);
    List<Small_Quantity> getSmallQuantitiesByQuantityGreaterThan(int min);
    List<Small_Quantity> getSmallQuantitiesByQuantityLessThan(int max);
    List<Small_Quantity> getSmallQuantitiesByQuantityInRange(int min, int max);
    List<Small_Quantity> getSmallQuantitiesBySoldGreaterThan(int min);
    List<Small_Quantity> getSmallQuantitiesBySoldLessThan(int max);
    List<Small_Quantity> getSmallQuantitiesBySoldInRange(int min, int max);
    //delete
    void deleteQuantityById(int id);
}
