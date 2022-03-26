package com.example.dockerdemo.A03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifeCycleBean {
    public LifeCycleBean() {
        System.out.println("构建");
    }

    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String home){
        System.out.println("依赖注入:"+home);
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("销毁");
    }

}
