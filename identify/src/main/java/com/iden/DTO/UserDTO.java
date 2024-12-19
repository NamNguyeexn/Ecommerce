package com.iden.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    private int id;
    private String full_name;
    private String phone;
    private boolean is_active;
    private String avt;
    private int account_id;
    private String created_at ;
    private String updated_at;
}
