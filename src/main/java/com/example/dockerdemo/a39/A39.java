package com.example.dockerdemo.a39;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * springboot初始化过程
 */
public class A39 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1.获取bean definition源
        SpringApplication spring = new SpringApplication(A39.class);
        spring.setSources(Set.of("classpath:a39bean1.xml"));
        // 2.演示推断应用类型 reactive,non,servlet
        Method deduce = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduce.setAccessible(true);
        System.out.println("应用类型为："+deduce.invoke(null));
        // 3.演示ApplicationContext初始化器(在ApplicationContext创建和refresh之间)
        spring.addInitializers(applicationContext -> {
            if (applicationContext instanceof GenericApplicationContext context) {
                context.registerBean("bean3",Bean3.class);
            }
        });
        // 4.演示监听器与事件
        spring.addListeners(event -> {
            System.out.println("\t事件为:"+event.getClass());
        });
        // 5.演示主类推断
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        System.out.println("主类是 " + deduceMainApplicationClass.invoke(spring));

        // run
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

    static class Bean3{}
    static class Bean1{}
    static class Bean2{}
    @Bean
    public Bean2 bean2(){
        return new Bean2();
    }

}
