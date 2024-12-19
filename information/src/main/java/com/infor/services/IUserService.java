package com.infor.services;

import com.infor.DTO.UserListDTO;
import com.infor.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    //create
    User createUser(User user);
    //read
    Optional<User> getUserById(int id);
    Optional<User> getUserByPhone(String phone);
    Optional<User> getUserByAccountId(int account_id);
    //update
    User changeUser(User user);
    //delete
    void deleteUser(int id);
    UserListDTO getAllUser(int page, int limit,
//                          String full_name, String user_name, String email, String phone,
                           String key,
                           boolean isActive);

}
