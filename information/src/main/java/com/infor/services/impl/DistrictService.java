package com.infor.services.impl;

import com.infor.models.City;
import com.infor.models.District;
import com.infor.repositories.DistrictRepository;
import com.infor.services.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.infor.repositories.DistrictRepository.Specs.byCityId;

@Service
public class DistrictService implements IDistrictService {
    @Autowired
    private DistrictRepository districtRepository;
    @Override
    public District createDistrict(District district) {
        return districtRepository.save(district);
    }

    @Override
    public Optional<District> getDistrictById(int id) {
        return districtRepository.findById(id);
    }

    @Override
    public List<District> getDistrictByCityId(int city_id) {
        return districtRepository.findAll(byCityId(city_id));
    }

    @Override
    public List<District> getAllCities() {
        return districtRepository.findAll();
    }
}
