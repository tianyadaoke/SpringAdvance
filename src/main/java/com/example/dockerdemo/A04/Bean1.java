package com.example.dockerdemo.A04;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class Bean1 {

    private Bean2 bean2;
    @Autowired
    public void setBean2(Bean2 bean2) {
        System.out.println("autowired生效:"+bean2);
        this.bean2 = bean2;
    }
    private Bean3 bean3;
    @Resource
    public void setBean3(Bean3 bean3) {
        System.out.println("resource生效:"+bean3);
        this.bean3 = bean3;
    }
    private String home;
    @Autowired
    public void setHome(@Value("${JAVA_HOME}") String home){
        System.out.println("@value生效:"+home);
        this.home=home;
    }
    @PostConstruct
    public void init(){
        System.out.println("postConstract生效");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("PreDestroy生效");
    }

    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", home='" + home + '\'' +
                '}';
    }
}
