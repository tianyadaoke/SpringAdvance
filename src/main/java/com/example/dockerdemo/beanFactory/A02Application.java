package com.example.dockerdemo.beanFactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class A02Application {
    public static void main(String[] args) {
        testClassPathXmlApplicationContext();
    }
    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b01.xml");
        System.out.println(context);
    }
}
