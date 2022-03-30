package com.example.dockerdemo.a08;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Scope("application")
public class BeanForApplication {
    @PreDestroy
    public void destroy(){
        System.out.println("application bean destroy");
    }
}
