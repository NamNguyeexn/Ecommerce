package com.infor.controllers;

import com.common.DTO.ResponseObject;
import com.infor.DTO.AccountDTO;
import com.infor.DTO.AddressDTO;
import com.infor.DTO.AddressResponseDTO;
import com.infor.filters.JwtTokenService;
import com.infor.mappers.AddressMapper;
import com.infor.models.*;
import com.infor.services.IAddressService;
import com.infor.services.impl.CityService;
import com.infor.services.impl.DistrictService;
import com.infor.services.impl.UserService;
import com.infor.services.impl.WardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/info/address")
public class AddressController {
    @Autowired
    private IAddressService addressService;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private WardService wardService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<Address>> create(
            @RequestBody AddressDTO address,
            HttpServletRequest request
    ) {
        try {
            String JWT = request.getHeader("Authorization").split(" ")[1].trim();
            int accountId = jwtTokenService.getAccountId(JWT);
            Optional<User> user = userService.getUserByAccountId(accountId);
            if (user.isPresent()) {
                address.setUser_id(user.get().getId()+"");
                String phone = address.getPhone()!=null && !address.getPhone().isEmpty() ?address.getPhone(): user.get().getPhone() ;
                if(phone == null || address.getCity_id()==null||address.getDistrict_id()==null||address.getWard_id()==null){
                    return ResponseEntity.badRequest().body(
                            ResponseObject.<Address>builder()
                                    .message("Khong the tao dia chi")
                                    .build()
                    );
                }
                City city = cityService.getCityById(Integer.parseInt(address.getCity_id())).orElse(null);

                Address address1 = addressService.createAddress(
                        addressMapper.addressInputToAddress(address, phone)
                );
//                if(address.getId()!=0){
//
//                }
                if(address1 != null){
                    return ResponseEntity.ok().body(
                            ResponseObject.<Address>builder()
                                    .data(null)
                                    .isSuccess(true)
                                    .message("Tao moi dia chi thanh cong")
                                    .build()
                    );
                } else return ResponseEntity.badRequest().body(
                        ResponseObject.<Address>builder()
                                .message("Khong the tao dia chi")
                                .build()
                );
            } else {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<Address>builder()
                                .message("Khong tim thay tai khoan")
                                .build()
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(
                    ResponseObject.<Address>builder()
                            .message("Gap loi server")
                            .build()
            );
        }
    }

//     Ve sua tiep

    @PostMapping("/update/{addressId}")
    public ResponseEntity<ResponseObject<Address>> update(
            @PathVariable("addressId") String addressId,
            @RequestBody AddressDTO address,
            HttpServletRequest request
    ) {
        try {
            String JWT = request.getHeader("Authorization").split(" ")[1].trim();
            int accountId = jwtTokenService.getAccountId(JWT);
            if(accountId==0){
                return ResponseEntity.status(401).body(
                        ResponseObject.<Address>builder()
                                .isSuccess(false)
                                .message("Không có quyền")
                                .data(null)
                                .build()
                );
            }
            Optional<User> user = userService.getUserByAccountId(accountId);
            Optional<Address> addressRes = addressService.getAddressById(Integer.parseInt(addressId));

            if (user.isPresent() && addressRes.isPresent()) {
                Address existingAddress = addressRes.get();

                // Cập nhật các trường chỉ khi chúng không null
                if (address.getPhone() != null) {
                    existingAddress.setPhone(address.getPhone());
                }
                if (address.getCity_id() != null) {
                    City city = cityService.getCityById(Integer.parseInt(address.getCity_id())).orElse(null);

                    existingAddress.setCity(city);
                }
                if (address.getDistrict_id() != null) {
                    District district = districtService.getDistrictById(Integer.parseInt(address.getDistrict_id())).orElse(null);
                    existingAddress.setDistrict(district);
                }
                if (address.getWard_id() != null) {
                    Ward ward = wardService.getWardById(Integer.parseInt(address.getWard_id())).orElse(null);
                    existingAddress.setWard(ward);
                }
                // Cập nhật thêm các trường khác nếu cần

                // Lưu đối tượng đã cập nhật
                Address updatedAddress = addressService.createAddress(existingAddress);

                return ResponseEntity.ok().body(
                        ResponseObject.<Address>builder()
                                .data(updatedAddress)
                                .isSuccess(true)
                                .message("Cập nhật địa chỉ thành công")
                                .build()
                );
            } else {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<Address>builder()
                                .message("Không tìm thấy tài khoản hoặc địa chỉ")
                                .build()
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(
                    ResponseObject.<Address>builder()
                            .message("Gặp lỗi server")
                            .build()
            );
        }
    }


    @GetMapping("/get-address/{userId}")
    public ResponseEntity<ResponseObject<List<AddressResponseDTO>>> getAddress(
            @PathVariable("userId") String userId
    ) {
        try {
            List<Address> addresses= addressService.getAddressByUser(Integer.parseInt(userId));
            List<AddressResponseDTO> response= addressMapper.addressListToAddressDTOList(addresses);
            return ResponseEntity.ok().body( ResponseObject.<List<AddressResponseDTO>>builder()
                    .data(response)
                    .isSuccess(true)
                    .message("oke")
                    .build());

        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<List<AddressResponseDTO>>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }
    @GetMapping("/get-city")
    public ResponseEntity<ResponseObject<City>> getCity(@RequestBody String id){
        try {
            Optional<City> city= cityService.getCityById(Integer.parseInt(id));
            if(city.isPresent()){
                return ResponseEntity.ok().body( ResponseObject.<City>builder()
                        .data(city.get())
                        .isSuccess(true)
                        .message("oke")
                        .build());
            }
            else {
                return ResponseEntity.badRequest().body( ResponseObject.<City>builder()
                        .data(null)
                        .isSuccess(false)
                        .message("City not found!")
                        .build());
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<City>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }
    @GetMapping("/get-all-city")
    public ResponseEntity<ResponseObject<List<City>>> getAllCity(){
        try {
            List<City> cities= cityService.getAllCities();
                return ResponseEntity.ok().body( ResponseObject.<List<City>>builder()
                        .data(cities)
                        .isSuccess(true)
                        .message("oke")
                        .build());

        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<List<City>>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<ResponseObject<String>> deleteAddress(
            @PathVariable("addressId") String addressId
    ) {
        try {
            Address address= addressService.getAddressById(Integer.parseInt(addressId)).orElse(null);
            if(address!=null){
                addressService.deleteAddress(addressId);
                return ResponseEntity.ok().body( ResponseObject.<String>builder()
                        .data(addressId)
                        .isSuccess(true)
                        .message("Xóa thành công địa chỉ có id: "+addressId)
                        .build());
            }
            else {
                return ResponseEntity.badRequest().body( ResponseObject.<String>builder()
                        .data(null)
                        .isSuccess(false)
                        .message("Không tồn tại địa chỉ có id: "+addressId)
                        .build());
            }

        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<String>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }
    @GetMapping("/set-default/{addressId}")
    public ResponseEntity<ResponseObject<String>> setDefaultAddress(
            @PathVariable("addressId") String addressId,
            HttpServletRequest request
        ) {
        try {
            String JWT = request.getHeader("Authorization").split(" ")[1].trim();
            int accountId = jwtTokenService.getAccountId(JWT);
            if (accountId == 0) {
                return ResponseEntity.status(401).body(
                        ResponseObject.<String>builder()
                                .isSuccess(false)
                                .message("Không có quyền")
                                .data(null)
                                .build()
                );
            }
            Optional<User> user = userService.getUserByAccountId(accountId);
            if (user.isPresent()) {
                int userId = user.get().getId();
                List<Address> addresses = addressService.getAddressByUser(userId);
                List<Address> addressesUpdate = new ArrayList<>();
                for (Address address : addresses) {
                    if (address.getId() != Integer.parseInt(addressId)) {
                        address.setDefault(false);
                    } else {
                        address.setDefault(true);
                    }
                    addressesUpdate.add(address);
                }
                try {
                    addressService.saveAll(addressesUpdate);
                    return ResponseEntity.ok().body(ResponseObject.<String>builder()
                            .data(null)
                            .isSuccess(true)
                            .message("Cập nhật thành công")
                            .build());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return ResponseEntity.ok().body(ResponseObject.<String>builder()
                            .data(null)
                            .isSuccess(false)
                            .message("Cập nhật tất bại")
                            .build());
                }

            }
            else {
                return ResponseEntity.ok().body(ResponseObject.<String>builder()
                        .data(null)
                        .isSuccess(false)
                        .message("Cập nhật thất bại")
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseObject.<String>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }

    @GetMapping("/get-district")
    public ResponseEntity<ResponseObject<District>> getDistrict(@RequestBody String id){
        try {
            Optional<District> district= districtService.getDistrictById(Integer.parseInt(id));
            if(district.isPresent()){
                return ResponseEntity.ok().body( ResponseObject.<District>builder()
                        .data(district.get())
                        .isSuccess(true)
                        .message("oke")
                        .build());
            }
            else {
                return ResponseEntity.badRequest().body( ResponseObject.<District>builder()
                        .data(null)
                        .isSuccess(false)
                        .message("district not found!")
                        .build());
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<District>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }
    @GetMapping("/get-all-district")
    public ResponseEntity<ResponseObject<List<District>>> getAllDistrict(){
        try {
            List<District> districts= districtService.getAllCities();
            return ResponseEntity.ok().body( ResponseObject.<List<District>>builder()
                    .data(districts)
                    .isSuccess(true)
                    .message("oke")
                    .build());

        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<List<District>>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }


    @GetMapping("/get-ward")
    public ResponseEntity<ResponseObject<Ward>> getWard(@RequestBody String id){
        try {
            Optional<Ward> ward= wardService.getWardById(Integer.parseInt(id));
            if(ward.isPresent()){
                return ResponseEntity.ok().body( ResponseObject.<Ward>builder()
                        .data(ward.get())
                        .isSuccess(true)
                        .message("oke")
                        .build());
            }
            else {
                return ResponseEntity.badRequest().body( ResponseObject.<Ward>builder()
                        .data(null)
                        .isSuccess(false)
                        .message("Ward not found!")
                        .build());
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<Ward>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }
    @GetMapping("/get-all-ward")
    public ResponseEntity<ResponseObject<List<Ward>>> getAllWard(){
        try {
            List<Ward> wards= wardService.getAllCities();
            return ResponseEntity.ok().body( ResponseObject.<List<Ward>>builder()
                    .data(wards)
                    .isSuccess(true)
                    .message("oke")
                    .build());

        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body( ResponseObject.<List<Ward>>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("Server error!")
                    .build());
        }
    }
//    @GetMapping("/get")
//    public ResponseEntity<ResponseObject<Address>> get(
//            @RequestBody int id,
//            HttpServletRequest request
//    ) {
//        try {
//            String username = jwtTokenService.getUsernameByRequest(request);
//            int accountId = jwtTokenService.getAccountId(username);
//            Optional<Account> account = accountService.getAccountById(accountId);
//            if (account.isPresent()) {
//                Optional<Address> address = addressService.getAddressById(id);
//                if (address.isPresent()) {
//                    return ResponseEntity.ok().body(
//                            ResponseObject.<Address>builder()
//                                    .data(address.get())
//                                    .isSuccess(true)
//                                    .message("Tim duoc dia chi")
//                                    .build()
//                    );
//                } else return ResponseEntity.badRequest().body(
//                        ResponseObject.<Address>builder()
//                                .message("Khong the tim duoc id dia chi")
//                                .build()
//                );
//            } else return ResponseEntity.badRequest().body(
//                    ResponseObject.<Address>builder()
//                            .message("Khong tim thay tai khoan")
//                            .build()
//            );
//        } catch (Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity.badRequest().body(
//                    ResponseObject.<Address>builder()
//                            .message("Gap loi server")
//                            .build()
//            );
//        }
//    }
//    @GetMapping("/delete")
//    public ResponseEntity<ResponseObject<Address>> delete(
//            @RequestBody int id,
//            HttpServletRequest request
//    ) {
//        try {
//            String username = jwtTokenService.getUsernameByRequest(request);
//            int accountId = jwtTokenService.getAccountId(username);
//            Optional<Account> account = accountService.getAccountById(accountId);
//            if (account.isPresent()) {
//                Optional<Address> address = addressService.getAddressById(id);
//                if (address.isPresent()) {
//                    addressService.deleteAddress(address.get());
//                    return ResponseEntity.ok().body(
//                            ResponseObject.<Address>builder()
//                                    .isSuccess(true)
//                                    .message("Xoa dia chi thanh cong")
//                                    .build()
//                    );
//                } else return ResponseEntity.badRequest().body(
//                        ResponseObject.<Address>builder()
//                                .message("Khong the tim duoc id dia chi")
//                                .build()
//                );
//            } else return ResponseEntity.badRequest().body(
//                    ResponseObject.<Address>builder()
//                            .message("Khong tim thay tai khoan")
//                            .build()
//            );
//        } catch (Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity.badRequest().body(
//                    ResponseObject.<Address>builder()
//                            .message("Gap loi server")
//                            .build()
//            );
//        }
//    }
//    @GetMapping("/update")
//    public ResponseEntity<ResponseObject<Address>> update(
//            @RequestBody AddressDTO aDTO,
//            HttpServletRequest request
//    ) {
//        try {
//            String username = jwtTokenService.getUsernameByRequest(request);
//            int accountId = jwtTokenService.getAccountId(username);
//            Optional<Account> account = accountService.getAccountById(accountId);
//            if (account.isPresent()) {
//                Optional<Address> address = addressService.getAddressesByUserId(aDTO.getUser_id());
//                if (address.isPresent()) {
//                    String phone = address.get().getPhone();
//                    if(phone == null){
////                        phone = account.get().getPhone();
//                    }
//                    Address address1 = addressService.createAddress(
//                            addressMapper.addressInputToAddress(aDTO, phone)
//                    );
//                    return ResponseEntity.ok().body(
//                            ResponseObject.<Address>builder()
//                                    .data(address1)
//                                    .isSuccess(true)
//                                    .message("Cap nhat dia chi thanh cong")
//                                    .build()
//                    );
//                } else return ResponseEntity.badRequest().body(
//                        ResponseObject.<Address>builder()
//                                .message("Khong the tim duoc dia chi")
//                                .build()
//                );
//            } else return ResponseEntity.badRequest().body(
//                    ResponseObject.<Address>builder()
//                            .message("Khong tim thay tai khoan")
//                            .build()
//            );
//        } catch (Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity.badRequest().body(
//                    ResponseObject.<Address>builder()
//                            .message("Gap loi server")
//                            .build()
//            );
//        }
//    }
}
