package com.example.dockerdemo.registComp;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Component1 {
    @EventListener
    public void aaa(UserRegisteredEvent event){
        System.out.println("event"+event);
        System.out.println("发送短信");
    }
}
