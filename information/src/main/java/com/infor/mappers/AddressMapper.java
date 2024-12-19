package com.infor.mappers;

import com.infor.DTO.AddressDTO;
import com.infor.DTO.AddressResponseDTO;
import com.infor.models.Address;
import com.infor.models.City;
import com.infor.models.District;
import com.infor.models.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "aDTO.city_id", target = "city.id")       // Chỉ ánh xạ ID của City
    @Mapping(source = "aDTO.district_id", target = "district.id") // Chỉ ánh xạ ID của District
    @Mapping(source = "aDTO.ward_id", target = "ward.id")       // Chỉ ánh xạ ID của Ward
    @Mapping(source = "aDTO.street", target = "street")
    @Mapping(source = "aDTO.user_id", target = "user_id")
    @Mapping(source = "aDTO.id", target = "id")
    @Mapping(source = "phone", target = "phone")
    Address addressInputToAddress(AddressDTO aDTO, String phone);

    // Tạo phương thức để ánh xạ field 'direction' từ City, District, Ward
    default String mapDirection(City city, District district, Ward ward) {
        // Kết hợp thông tin từ City, District, Ward thành một chuỗi 'direction'
        return city.getName() + ", " + district.getName() + ", " + ward.getName();
    }

    // Ánh xạ trường 'direction' từ các đối tượng City, District, Ward
    @Mapping(target = "direction", expression = "java(mapDirection(address.getCity(), address.getDistrict(), address.getWard()))")
    @Mapping(source = "city.id", target = "city_code") // Ánh xạ ID của City
    @Mapping(source = "district.id", target = "district_code") // Ánh xạ ID của District
    @Mapping(source = "ward.id", target = "ward_code") // Ánh xạ ID của Ward
    @Mapping(source = "id", target = "id") // Ánh xạ ID của Ward
    AddressResponseDTO addressToAddressDTO(Address address);


    // Phương thức ánh xạ List<Address> thành List<AddressDTO>
    List<AddressResponseDTO> addressListToAddressDTOList(List<Address> addresses);
}
