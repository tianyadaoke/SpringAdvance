package com.example.dockerdemo.a06;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class A06Application {
    public static void main(String[] args) {
        /**
         * Aware接口注入一些与容器相关信息
         * 1. BeanNameAware 注入Bean名字信息
         * 2. BeanFactoryAware 注入BeanFactory容器
         * 3. ApplicationContextAware注入ApplicacionContext容器
         * 4. EmbededValueResolverAware ${}
         */
        GenericApplicationContext context = new GenericApplicationContext();
        //context.registerBean("myBean",MyBean.class);

        //需要让postconstruct和autowire生效，需要加入后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        //配置类生效
        //context.registerBean("myConfig1",MyConfig1.class);
        context.registerBean("myConfig2",MyConfig2.class);
        // 解析@componentScan,@Bean,@Import @ImportResource
        context.registerBean(ConfigurationClassPostProcessor.class);


        context.refresh(); //1. 添加BeanFactory后处理器 2. 添加Bean后处理器 3.初始化所有的单例
        context.close();
        /**
         * 1,2,3功能用autowire就可以实现区别是
         * autowire需要bean后处理器，
         * aware属于内置功能
         */
    }
}
