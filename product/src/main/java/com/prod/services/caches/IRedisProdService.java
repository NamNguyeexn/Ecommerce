package com.prod.services.caches;

import com.prod.DTO.AccountDTO;
import com.prod.DTO.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface IRedisProdService {
    AccountDTO getAccount(int accountId);
    UserDTO getUserByAccount(int accountId);
}
