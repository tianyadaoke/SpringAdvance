package com.example.dockerdemo.a44;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class A44 {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        ClassPathBeanDefinitionScanner scanner=new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan(A44.class.getPackageName());
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
