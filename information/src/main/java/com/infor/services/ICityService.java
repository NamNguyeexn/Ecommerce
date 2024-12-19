package com.infor.services;

import com.infor.models.City;

import java.util.List;
import java.util.Optional;

public interface ICityService {
    //create
    //chi goi ra trong batch, khi khoi chay chuong trinh va cap nhat du lieu cac thanh pho
    City createCity(City city);
    //read
    Optional<City> getCityById(int id);
    Optional<City> getCityByName(String name);
    List<City> getAllCities();
}
