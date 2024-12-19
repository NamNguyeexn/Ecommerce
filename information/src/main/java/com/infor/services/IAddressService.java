package com.infor.services;

import com.infor.models.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IAddressService {
    //create
    Address createAddress(Address address);
    //read
    Optional<Address> getAddressById(int id);
    Optional<Address> getAddressesByUserId(int user_id);
    List<Address> getAddressesByCityId(int city_id);
    List<Address> getAddressesByDistrictId(int district_id);
    List<Address> getAddressesByWardId(int ward_id);
    //update
    //delete
    List<Address> getAddressByUser(int user_id);
    void deleteAddress(String id);
    List<Address>  saveAll(List<Address> addresses);
}
