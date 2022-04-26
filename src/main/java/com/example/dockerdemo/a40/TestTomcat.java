package com.example.dockerdemo.a40;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestTomcat {
    public static void main(String[] args) throws IOException, LifecycleException {
        // 1.创建tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");
        // 2.创建项目文件夹，docBase
        File docBase = Files.createTempDirectory("boot.").toFile();
        docBase.deleteOnExit();
        // 3.创建tomcat目录，在tomcat中为context
        Context context = tomcat.addContext("", docBase.getAbsolutePath());
        WebApplicationContext springContext = getApplicationContext();

        // 4.编程添加Servlet
        context.addServletContainerInitializer((set, servletContext) -> {
            HelloServlet helloServlet = new HelloServlet();
            servletContext.addServlet("hello",helloServlet).addMapping("/hello");
//                DispatcherServlet dispatcherServlet = springContext.getBean(DispatcherServlet.class);
//                servletContext.addServlet("dispatcherServlet",dispatcherServlet).addMapping("/");
            for (DispatcherServletRegistrationBean registrationBean : springContext.getBeansOfType(DispatcherServletRegistrationBean.class).values()) {
                registrationBean.onStartup(servletContext);
            }

        }, Collections.emptySet());
        // 5.启动tomcat
        tomcat.start();
        // 6.创建连接器
        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);
        tomcat.setConnector(connector);
    }
    public static WebApplicationContext getApplicationContext(){
        AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext();
        context.register(Config.class);
        context.refresh();
        return context;
    }
    @Configuration
    static class Config{
        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
        }
        @Bean
        public DispatcherServlet dispatcherServlet(WebApplicationContext applicationContext){
            return new DispatcherServlet(applicationContext);
        }
        @Bean
        public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
            RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
            adapter.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
            return adapter;
        }
        @RestController
        static class MyController{
            @GetMapping("/hello2")
            public Map<String,Object> hello(){
                return Map.of("hello2","hello2,Spring!");
            }
        }

    }
}
