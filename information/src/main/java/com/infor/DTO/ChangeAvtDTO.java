package com.infor.DTO;

import com.infor.validators.IValidName;
import com.infor.validators.IValidPhoneCharacter;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class ChangeAvtDTO {
    private String urlAvatar;
}
