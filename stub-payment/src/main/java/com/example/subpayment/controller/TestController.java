package com.example.subpayment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stub-payment")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Мои защищенные данные в микросервисе StubPayment";
    }
}