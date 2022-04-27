package com.example.dockerdemo.a42;

import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class A42Conditional_2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(Config.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
    static class MyCondition1 implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());
            String className = attributes.get("className").toString();
            boolean exists = (boolean) attributes.get("exists");
            boolean present = ClassUtils.isPresent(className, null);
            return exists?present:!present;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD,ElementType.TYPE})
    @Conditional(MyCondition1.class)
    @interface ConditionalOnClass{
        boolean exists(); // true存在 false不存在
        String className(); //要判断的类名
    }

    @Configuration
    static class Config{
        @Bean
        @ConditionalOnClass(className = "com.alibaba.druid.pool.DruidDataSource",exists = false)
        public Bean1 bean1(){
            return new Bean1();
        }
        @Bean
        @ConditionalOnClass(className = "com.alibaba.druid.pool.DruidDataSource",exists = true)
        public Bean2 bean2(){
            return new Bean2();
        }
    }
    static class Bean1{
    }
    static class Bean2{
    }
}
