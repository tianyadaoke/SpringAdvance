package com.example.dockerdemo.a39;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class A39_3 {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication();
        application.addInitializers(applicationContext -> System.out.println("执行初始化器增强"));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>8.封装启动args");
        DefaultApplicationArguments arguments = new DefaultApplicationArguments(args);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>8.创建容器");
        GenericApplicationContext context = createApplicationContext(WebApplicationType.SERVLET);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>9.准备容器");
        for (ApplicationContextInitializer initializer : application.getInitializers()) {
            initializer.initialize(context);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>10.加载bean定义");
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader1 = new AnnotatedBeanDefinitionReader(context.getDefaultListableBeanFactory());
        reader1.register(Config.class);

        XmlBeanDefinitionReader reader2 = new XmlBeanDefinitionReader(beanFactory);
        reader2.loadBeanDefinitions(new ClassPathResource("b03.xml"));

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        // scanner.scan("...") 此处也可以添加beanDefinition，省略不写

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>11.refresh容器");
        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("name:"+name+"来源"+beanFactory.getBeanDefinition(name).getResourceDescription());
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>12.执行runner");
        for (CommandLineRunner runner : context.getBeansOfType(CommandLineRunner.class).values()) {
            runner.run(args);
        }
        for (ApplicationRunner runner : context.getBeansOfType(ApplicationRunner.class).values()) {
            runner.run(arguments);
        }

    }
    private static GenericApplicationContext createApplicationContext(WebApplicationType type){
        GenericApplicationContext context=null;
        switch (type){
            case SERVLET -> context=new AnnotationConfigServletWebServerApplicationContext();
            case REACTIVE -> context=new AnnotationConfigReactiveWebServerApplicationContext();
            case NONE -> context=new AnnotationConfigApplicationContext();
        }
        return context;

    }
    static class Bean5{
    }
    static class Bean4{
    }

    @Configuration
    static class Config{
        @Bean
        public Bean5 bean5(){
            return new Bean5();
        }
        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }
        @Bean
        public ApplicationRunner applicationRunner(){
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    System.out.println("applicationRunner");
                }
            };
        }
        @Bean
        public CommandLineRunner commandLineRunner(){
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println("commandLineRunner");
                }
            };
        }
    }
}
