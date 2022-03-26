package com.example.dockerdemo.web;

import com.example.dockerdemo.registComp.RegisterComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    RegisterComponent registerComponent;
    @GetMapping("test")
    public void test(){
        System.out.println("用户开始注册");
        registerComponent.register();
    }
}
