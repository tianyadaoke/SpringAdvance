package com.example.dockerdemo;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootApplication
public class DockerDemoApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(DockerDemoApplication.class, args);
        Field field = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        field.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String,Object> map = (Map<String, Object>) field.get(beanFactory);
//        map.forEach((k,v)->{
//            System.out.println(k+"="+v);
//        });
        map.entrySet().stream().filter(e->e.getKey().startsWith("component"))
                .forEach(e->{
                    System.out.println(e.getKey()+"="+e.getValue());
                });
       // Resource[] resources = context.getResources("classpath:application.properties");
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));

        // context.publishEvent(new UserRegisteredEvent(context));
        // context.getBean(Component2.class).register();


    }

}
