package com.iden.DTO;

import com.iden.validators.ICharEmail;
import com.iden.validators.ICharPassword;
import com.iden.validators.ICharUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMailInputDTO {
    @ICharEmail
    private String email;
}
