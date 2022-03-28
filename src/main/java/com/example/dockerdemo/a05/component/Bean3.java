package com.example.dockerdemo.a05.component;

import org.springframework.stereotype.Component;

@Component
public class Bean3 {
    public Bean3() {
        System.out.println("我被spring管理啦");
    }
}
