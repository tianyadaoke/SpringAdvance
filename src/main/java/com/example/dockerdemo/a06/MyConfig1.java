package com.example.dockerdemo.a06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyConfig1 {
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        System.out.println("注入Application Context");
    }
    @PostConstruct
    public void init(){
        System.out.println("初始化");
    }

    @Bean //BeanFactory后处理器
    public BeanFactoryPostProcessor processor1(){
        return beanfactory->System.out.println("执行processor1");
        }


}
