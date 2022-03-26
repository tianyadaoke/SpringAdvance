package com.example.dockerdemo.registComp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Component2 {
    @Autowired
    private ApplicationEventPublisher context;

    public void register(){
        System.out.println("用户注册");
        context.publishEvent(new UserRegisteredEvent(this));
    }
}
