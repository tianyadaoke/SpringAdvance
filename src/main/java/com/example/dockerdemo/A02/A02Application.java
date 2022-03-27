package com.example.dockerdemo.A02;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * xml，配置类等初始化容器
 */
public class A02Application {
    public static void main(String[] args) {
        // testClassPathXmlApplicationContext();
         //testFileSystemXmlApplication();
        // beanFactoryXmlReader();
        //testAnnotationConfigApplicationContext();
        testAnnotationConfigServletApplciationContext();
    }

    private static void testAnnotationConfigServletApplciationContext() {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(webConfig.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
    @Configuration
    static class webConfig{
        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }
        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet();
        }
        @Bean
        public DispatcherServletRegistrationBean registrationBean(){
            return new DispatcherServletRegistrationBean(dispatcherServlet(),"/");
        }
        @Bean("/hello")
        public Controller controller1(){
            return (request, response) -> {
                response.getWriter().println("hello");
                return null;
            };
        }
    }


    private static void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(config.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("context.getBean(Bean2.class).getBean1() = " + context.getBean(Bean2.class).getBean1());

    }

    private static void beanFactoryXmlReader() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        for (String name : factory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>");
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("b01.xml"));
        for (String name : factory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

    private static void testFileSystemXmlApplication() {
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("C:\\java\\myproject2022\\SpringAdvance\\src\\main\\resources\\b01.xml");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("context.getBean(Bean2.class).getBean1() = " + context.getBean(Bean2.class).getBean1());
    }



    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b01.xml");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("context.getBean(Bean2.class).getBean1() = " + context.getBean(Bean2.class).getBean1());
    }
    @Configuration
    static class config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }
        @Bean
        public Bean2 bean2(Bean1 bean1){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }
    static class Bean1{}
    static class Bean2{
        private Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
    }
}
