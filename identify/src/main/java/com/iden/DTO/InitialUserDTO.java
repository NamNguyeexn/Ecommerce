package com.iden.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
public class InitialUserDTO {
    private String user_name;
    private String email;
    private boolean is_active;
    private String account_id;
}
