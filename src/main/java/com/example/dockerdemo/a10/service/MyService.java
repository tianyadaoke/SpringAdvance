package com.example.dockerdemo.a10.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public static void foo(){
        System.out.println("foo()");
    }
}
