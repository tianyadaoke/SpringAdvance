package com.example.dockerdemo.a39;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class A39_2 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 添加app监听器
        SpringApplication springApplication = new SpringApplication();
        springApplication.addListeners(e-> System.out.println(e.getClass()));
        // 获取事件发送器实现类名
        List<String> names = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, A39_2.class.getClassLoader());
        for (String name : names) {
            System.out.println(name);
            Class<?> clazz = Class.forName(name);
            Constructor<?> constructor = clazz.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publisher = (SpringApplicationRunListener)constructor.newInstance(springApplication, args);
            // 发布事件
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
            publisher.starting(bootstrapContext); // springboot开始启动
            publisher.environmentPrepared(bootstrapContext,new StandardEnvironment()); //环境信息准备完毕
            GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
            publisher.contextPrepared(genericApplicationContext); //在spring容器创建，并调用初始化器之后，发送此事件
            publisher.contextLoaded(genericApplicationContext); //所有的BeanDefinition准备完毕
            genericApplicationContext.refresh();
            publisher.started(genericApplicationContext); //Spring容器初始化完成（refresh调用完毕）
            publisher.running(genericApplicationContext); // springboot启动完毕
            publisher.failed(genericApplicationContext,new Exception("出错了")); //springboot启动出错
        }
    }
}
