package com.infor.DTO;

import com.infor.models.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListDTO {
    private long total;
    private long totalPage;
    private List<User> users;
}
