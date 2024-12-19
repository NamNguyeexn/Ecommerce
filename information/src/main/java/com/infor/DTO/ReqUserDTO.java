package com.infor.DTO;

import com.infor.validators.IValidName;
import com.infor.validators.IValidPhoneCharacter;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReqUserDTO {
    private String user_name;
    private String email;
    private String full_name;
    private String phone;
    private String avt;
    private boolean is_active;
    private String account_id;
}
