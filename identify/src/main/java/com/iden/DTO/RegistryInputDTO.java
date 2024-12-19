package com.iden.DTO;

import com.iden.validators.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistryInputDTO {
    @ICharUsername
    private String username;
    @ICharPassword
    private String password;
    @ICharEmail
    private String email;
    private String role;
}
