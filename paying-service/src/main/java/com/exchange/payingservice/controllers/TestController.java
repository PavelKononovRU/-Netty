package com.exchange.payingservice.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paying-service")
public class TestController {

    @GetMapping("/photos")
    public String secure1() {
        return "Мои защищенные фото в микросервисе PaymentService";
    }


    @GetMapping("/messages")
    public String secure2() {
        return "Мои защищенные сообщения в микросервисе PaymentService";
    }
}
