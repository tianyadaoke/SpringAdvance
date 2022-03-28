package com.example.dockerdemo.a05.component;

import org.springframework.stereotype.Component;

@Component
public class Bean2 {
    public Bean2() {
        System.out.println("我被spring管理啦");
    }
}
