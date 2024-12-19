package com.infor.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AddressResponseDTO {
    private int id;
    private String city_code;
    private String district_code;
    private String ward_code;
    private String direction;
    private String street;
    private String consignee;
    private String user_id;
    private String phone;
    private boolean isDefault;

}
