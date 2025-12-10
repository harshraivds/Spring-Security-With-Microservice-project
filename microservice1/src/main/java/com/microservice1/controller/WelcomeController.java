package com.microservice1.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
//http:localhost:8082/api/v1/message/getmessage
public class WelcomeController {

    @GetMapping("/getmessage")
    public String getMessage(){
        return "Welcome";
    }

    @GetMapping("/getmessage2")
    public String getMessage2(){
        return "Welcome";
    }
}
