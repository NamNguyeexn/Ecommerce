package com.iden.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserClientResponseDTO {
    private UserDTO data;
    private String message;
    private boolean isSuccess;

}
