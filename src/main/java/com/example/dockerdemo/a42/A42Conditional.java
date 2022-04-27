package com.example.dockerdemo.a42;

import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class A42Conditional {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(Config.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
    static class MyCondition1 implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource",null);
        }
    }
    static class MyCondition2 implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return !ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource",null);
        }
    }

    @Configuration
    static class Config{
        @Bean
        @Conditional(MyCondition1.class)
        public Bean1 bean1(){
            return new Bean1();
        }
        @Bean
        @Conditional(MyCondition2.class)
        public Bean2 bean2(){
            return new Bean2();
        }
    }
    static class Bean1{
    }
    static class Bean2{
    }
}
