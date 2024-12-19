package com.infor.mappers;

import com.infor.DTO.AddressDTO;
import com.infor.DTO.AddressResponseDTO;
import com.infor.DTO.AddressResponseDTO.AddressResponseDTOBuilder;
import com.infor.models.Address;
import com.infor.models.Address.AddressBuilder;
import com.infor.models.City;
import com.infor.models.City.CityBuilder;
import com.infor.models.District;
import com.infor.models.District.DistrictBuilder;
import com.infor.models.Ward;
import com.infor.models.Ward.WardBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-17T22:47:20+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address addressInputToAddress(AddressDTO aDTO, String phone) {
        if ( aDTO == null && phone == null ) {
            return null;
        }

        AddressBuilder address = Address.builder();

        if ( aDTO != null ) {
            address.city( addressDTOToCity( aDTO ) );
            address.district( addressDTOToDistrict( aDTO ) );
            address.ward( addressDTOToWard( aDTO ) );
            address.street( aDTO.getStreet() );
            if ( aDTO.getUser_id() != null ) {
                address.user_id( Integer.parseInt( aDTO.getUser_id() ) );
            }
            address.id( aDTO.getId() );
            address.consignee( aDTO.getConsignee() );
        }
        if ( phone != null ) {
            address.phone( phone );
        }

        return address.build();
    }

    @Override
    public AddressResponseDTO addressToAddressDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponseDTOBuilder addressResponseDTO = AddressResponseDTO.builder();

        Integer id = addressCityId( address );
        if ( id != null ) {
            addressResponseDTO.city_code( String.valueOf( id ) );
        }
        Integer id1 = addressDistrictId( address );
        if ( id1 != null ) {
            addressResponseDTO.district_code( String.valueOf( id1 ) );
        }
        Integer id2 = addressWardId( address );
        if ( id2 != null ) {
            addressResponseDTO.ward_code( String.valueOf( id2 ) );
        }
        addressResponseDTO.id( address.getId() );
        addressResponseDTO.street( address.getStreet() );
        addressResponseDTO.consignee( address.getConsignee() );
        addressResponseDTO.user_id( String.valueOf( address.getUser_id() ) );
        addressResponseDTO.phone( address.getPhone() );

        addressResponseDTO.direction( mapDirection(address.getCity(), address.getDistrict(), address.getWard()) );

        return addressResponseDTO.build();
    }

    @Override
    public List<AddressResponseDTO> addressListToAddressDTOList(List<Address> addresses) {
        if ( addresses == null ) {
            return null;
        }

        List<AddressResponseDTO> list = new ArrayList<AddressResponseDTO>( addresses.size() );
        for ( Address address : addresses ) {
            list.add( addressToAddressDTO( address ) );
        }

        return list;
    }

    protected City addressDTOToCity(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        CityBuilder city = City.builder();

        if ( addressDTO.getCity_id() != null ) {
            city.id( Integer.parseInt( addressDTO.getCity_id() ) );
        }

        return city.build();
    }

    protected District addressDTOToDistrict(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        DistrictBuilder district = District.builder();

        if ( addressDTO.getDistrict_id() != null ) {
            district.id( Integer.parseInt( addressDTO.getDistrict_id() ) );
        }

        return district.build();
    }

    protected Ward addressDTOToWard(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        WardBuilder ward = Ward.builder();

        if ( addressDTO.getWard_id() != null ) {
            ward.id( Integer.parseInt( addressDTO.getWard_id() ) );
        }

        return ward.build();
    }

    private Integer addressCityId(Address address) {
        if ( address == null ) {
            return null;
        }
        City city = address.getCity();
        if ( city == null ) {
            return null;
        }
        int id = city.getId();
        return id;
    }

    private Integer addressDistrictId(Address address) {
        if ( address == null ) {
            return null;
        }
        District district = address.getDistrict();
        if ( district == null ) {
            return null;
        }
        int id = district.getId();
        return id;
    }

    private Integer addressWardId(Address address) {
        if ( address == null ) {
            return null;
        }
        Ward ward = address.getWard();
        if ( ward == null ) {
            return null;
        }
        int id = ward.getId();
        return id;
    }
}
