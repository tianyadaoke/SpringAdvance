package com.example.dockerdemo.a39;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * springboot初始化过程
 */
public class A39 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1.获取bean definition源
        SpringApplication spring = new SpringApplication(A39.class);
        // 2.演示推断应用类型 reactive,non,servlet
        Method deduce = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduce.setAccessible(true);
        System.out.println("应用类型为："+deduce.invoke(null));
        // 3.演示ApplicationContext初始化器
        ConfigurableApplicationContext context = spring.run(args);
        for (String name:context.getBeanDefinitionNames()){
            System.out.println("name:"+name+",来源："+context.getBeanFactory().getBeanDefinition(name).getResourceDescription());
        }
        context.close();
    }

    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }
}
