package com.example.dockerdemo.a08;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Scope("request")
public class BeanForRequest {
    @PreDestroy
    public void destroy(){
        System.out.println("request bean destroy");
    }
}
