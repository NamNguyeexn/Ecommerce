package com.infor.services.impl;

import com.infor.models.District;
import com.infor.models.Ward;
import com.infor.repositories.WardRepository;
import com.infor.services.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.infor.repositories.WardRepository.Specs.byDistrictId;

@Service
public class WardService implements IWardService {
    @Autowired
    private WardRepository wardRepository;
    @Override
    public Ward createWard(Ward ward) {
        return wardRepository.save(ward);
    }

    @Override
    public List<Ward> getWardsByDistrictId(int districtId) {
        return wardRepository.findAll(byDistrictId(districtId));
    }

    @Override
    public Optional<Ward> getWardById(int wardId) {
        return wardRepository.findById(wardId);
    }
    @Override
    public List<Ward> getAllCities() {
        return wardRepository.findAll();
    }
}
