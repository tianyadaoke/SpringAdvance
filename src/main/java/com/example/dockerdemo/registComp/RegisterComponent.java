package com.example.dockerdemo.registComp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RegisterComponent {
    @Autowired
    ApplicationContext context;
    public void register(){
        System.out.println("注册完毕");
        //注册完发送一条event
        context.publishEvent(new UserRegisteredEvent(context));
    }
}
