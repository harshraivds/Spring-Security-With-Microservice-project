package com.microservice2.controller;

import com.microservice2.client.MessageClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/call")
public class CallController {

    private final MessageClient messageClient;

    private final RestTemplate restTemp;

    public CallController(MessageClient messageClient, RestTemplate restTemp) {
        this.messageClient = messageClient;
        this.restTemp = restTemp;
    }
/// api/v1/call/message
    @GetMapping("/message")
    public String callMessage(){
        return messageClient.getMessage();
    }

    @GetMapping("/message2")
    public String callMessage2(){
        String url = "http://microservice1/api/v1/message/getmessage2";
        return restTemp.getForObject(url, String.class);
    }

}
