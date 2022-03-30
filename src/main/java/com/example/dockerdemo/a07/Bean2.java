package com.example.dockerdemo.a07;


import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

public class Bean2 implements DisposableBean {
    @PreDestroy
    public void destroy1(){
        System.out.println("销毁1");
    }

    

    public void destroy3(){
        System.out.println("销毁3");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁2");
    }
}
