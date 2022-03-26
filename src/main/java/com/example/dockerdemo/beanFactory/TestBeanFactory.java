package com.example.dockerdemo.beanFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
beanFactory处理器不会做的事情
1.不会主动调用BeanFactory后置处理器
2.不会主动添加Bean后处理器
3.不会主动初始化单例对象
4.不会解析beanFactory，还不会解析${},#{}

bean后处理器会有排序的逻辑
 */
public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",beanDefinition);

        // 给beanFactory添加常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        //beanFactory的后处理器主要功能，补充了一些bean定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        //Bean后处理器，针对bean的生命周期各个阶段扩展，例如@autowired解析，@resource解析
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);

        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        // 可以提前创建实例化单例
        beanFactory.preInstantiateSingletons();
        // 延迟初始化实例化
        System.out.println(">>>>>>>>>>>>>>>>>>>>>");
        // System.out.println(beanFactory.getBean(Bean1.class).getBean2());
        System.out.println(beanFactory.getBean(Bean1.class).getInter());
    }
    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }
        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
        @Bean
        public Bean3 bean3(){
            return new Bean3();
        }
        @Bean
        public Bean4 bean4(){
            return new Bean4();
        }
    }
    interface Inter{}
    static class Bean1{
        public Bean1(){
            System.out.println("构造Bean1()");
        }
        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2(){
            return bean2;
        }

        // 先解析autowired
        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;
        public Inter getInter(){
            return bean3;
        }

    }
    static class Bean2{
        public Bean2(){
            System.out.println("构造Bean2()");
        }
    }
    static class Bean3 implements Inter{
        public Bean3(){
            System.out.println("构造Bean3()");
        }
    }
    static class Bean4 implements Inter{
        public Bean4(){
            System.out.println("构造Bean4()");
        }
    }




}
