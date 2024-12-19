package com.infor.services;

import com.infor.models.Ward;

import java.util.List;
import java.util.Optional;

public interface IWardService {
    Ward createWard(Ward ward);
    List<Ward> getWardsByDistrictId(int districtId);
    Optional<Ward> getWardById(int wardId);
    public List<Ward> getAllCities();
}
