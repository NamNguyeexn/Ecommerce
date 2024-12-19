package com.infor.DTO;

import com.infor.validators.IValidName;
import com.infor.validators.IValidPhoneCharacter;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class InfoInputDTO {
    @IValidName
    private String full_name;
    @IValidPhoneCharacter
    private String phone;
}
