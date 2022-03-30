package com.example.dockerdemo.a10;


import com.example.dockerdemo.a10.aspect.MyAspect;
import com.example.dockerdemo.a10.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class A10Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(A10Application.class, args);
        MyService myService = context.getBean(MyService.class);
        System.out.println("class:"+myService.getClass());

        MyAspect myAspect = context.getBean(MyAspect.class);
        System.out.println("class aspect:"+myAspect);
        myService.foo();
        context.close();
    }
}
