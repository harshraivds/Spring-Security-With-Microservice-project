package com.microservice2.client;

import com.microservice2.filter.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MICROSERVICE1",configuration = FeignConfig.class)
public interface MessageClient {

    @GetMapping("api/v1/message/getmessage")
    String getMessage();
    @GetMapping("api/v1/message/getmessage2")
    String getMessage2();

}
