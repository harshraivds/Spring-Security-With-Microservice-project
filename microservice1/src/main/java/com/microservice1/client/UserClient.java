package com.microservice1.client;


import com.microservice1.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="AUTHENTICATION")
public interface UserClient {

    @GetMapping("/api/v1/auth/getUser")
    public User getUserByUsername(@RequestParam("username") String username, @RequestHeader("Authorization") String token);
}
