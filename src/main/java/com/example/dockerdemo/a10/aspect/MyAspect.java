package com.example.dockerdemo.a10.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Before("execution(* com.example.dockerdemo.a10.service.MyService.foo())")
    public void before(){
        System.out.println("before()");
    }
}
