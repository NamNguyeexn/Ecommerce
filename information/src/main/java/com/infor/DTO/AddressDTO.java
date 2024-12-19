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
public class AddressDTO {
    private int id;
    private String city_id;
    private String district_id;
    private String ward_id;
    private String street;
    private String consignee;
    private String user_id;
    @Nullable
    private String phone = "";
}
