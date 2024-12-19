package com.infor.services;

import com.infor.DTO.AccountDTO;
import com.infor.models.User;
import org.springframework.stereotype.Service;

@Service
public interface IRedisInfoService {
    AccountDTO getAccount(int account_id);
    void pushUserToRedis(User user);
    void updateUserToRedis(User user);
}
