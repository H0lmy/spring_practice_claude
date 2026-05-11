package com.max.bookwishlist.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String sayHello() {
        return "Hello from Spring Boot,served by server level!";

    }
}
