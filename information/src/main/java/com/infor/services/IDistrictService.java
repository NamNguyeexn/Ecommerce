package com.infor.services;

import com.infor.models.City;
import com.infor.models.District;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IDistrictService {
    //create
    District createDistrict(District district);
    //read
    Optional<District> getDistrictById(int id);
    List<District> getDistrictByCityId(int city_id);
    public List<District> getAllCities() ;
    //update
    //delete
}
