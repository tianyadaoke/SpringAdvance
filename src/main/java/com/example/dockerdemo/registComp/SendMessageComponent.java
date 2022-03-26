package com.example.dockerdemo.registComp;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SendMessageComponent {
    @EventListener
    public void sendMessage(UserRegisteredEvent event){
        System.out.println("监听到了注册");
        System.out.println("开始发送手机短信。。。");

    }
}
