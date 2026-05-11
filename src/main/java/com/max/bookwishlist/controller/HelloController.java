package com.max.bookwishlist.controller;

import com.max.bookwishlist.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
    public final HelloService helloService;
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }



    @GetMapping("/hello")
    public String hello() {
        return helloService.sayHello();

    }
}
