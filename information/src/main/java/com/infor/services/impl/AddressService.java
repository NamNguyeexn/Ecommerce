package com.infor.services.impl;

import com.infor.models.Address;
import com.infor.repositories.AddressRepository;
import com.infor.services.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.infor.repositories.AddressRepository.Specs.*;

@Service
public class AddressService implements IAddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> getAddressById(int id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> getAddressesByUserId(int user_id) {
        return addressRepository.findOne(byUserId(user_id));
    }

    @Override
    public List<Address> getAddressesByCityId(int city_id) {
        return addressRepository.findAll(byCityId(city_id));
    }

    @Override
    public List<Address> getAddressesByDistrictId(int district_id) {
        return addressRepository.findAll(byDistrictId(district_id));
    }

    @Override
    public List<Address> getAddressesByWardId(int ward_id) {
        return addressRepository.findAll(byWardId(ward_id));
    }

    @Override
    public List<Address> getAddressByUser(int user_id) {
        return addressRepository.findAll(byUserId(user_id));
    }

    @Override
    public void deleteAddress(String id) {
        addressRepository.deleteById(Integer.parseInt(id));
    }

    @Override
    public List<Address>  saveAll(List<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }
}
