package com.iden.services;

import com.iden.DTO.InitialUserDTO;
import com.iden.DTO.UserClientResponseDTO;
import com.iden.configs.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userClient", url = "http://localhost:6066", configuration = FeignConfig.class)
public interface IUserClient {
    @GetMapping("/api/info/user/info")
    ResponseEntity<UserClientResponseDTO> getUserInfo(@RequestHeader("Authorization") String token);

    @PostMapping("/api/info/user/change/email")
    ResponseEntity<UserClientResponseDTO> changeEmail(@RequestHeader("Authorization") String token,@RequestBody  String newEmail);

    @PostMapping("/api/info/user/create")
    ResponseEntity<UserClientResponseDTO> initialUser(@RequestBody InitialUserDTO initialUserDTO);
}
