package com.infor.services.impl;

import com.infor.models.City;
import com.infor.repositories.CityRepository;
import com.infor.services.ICityService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.infor.repositories.CityRepository.Specs.byName;

@Service
public class CityService implements ICityService {
    @Autowired
    private CityRepository cityRepository;
    @Override
    public City createCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> getCityById(int id) {
        return cityRepository.findById(id);
    }

    @Override
    public Optional<City> getCityByName(String name) {
        return cityRepository.findOne(byName(name));
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
