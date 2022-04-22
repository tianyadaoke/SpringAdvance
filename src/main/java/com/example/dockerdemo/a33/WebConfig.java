package com.example.dockerdemo.a33;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class WebConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
        return new TomcatServletWebServerFactory(8080);
    }
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet){
        return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
    }

    @Component
    static class MyHandlerMapping implements HandlerMapping{
        @Override
        public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
            String key = request.getRequestURI();
            Controller controller = collect.get(key);
            if(controller==null){
                return null;
            }
            return new HandlerExecutionChain(controller);
        }
        @Autowired
        private ApplicationContext context;
        private Map<String,Controller> collect;
        @PostConstruct
        public void init(){
            collect = context.getBeansOfType(Controller.class)
                    .entrySet().stream()
                    .filter(e -> e.getKey().startsWith("/"))
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            System.out.println(collect);
        }
    }
    @Component
    static class MyHandlerAdaptor implements HandlerAdapter{
        @Override
        public boolean supports(Object handler) {
            return handler instanceof Controller;
        }

        @Override
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (handler instanceof Controller controller) {
                controller.handleRequest(request,response);
            }
            return null;
        }

        @Override
        public long getLastModified(HttpServletRequest request, Object handler) {
            return -1;
        }
    }

    @Component("/c1")
    public static class Controller1 implements Controller{

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().println("this is c1");
            return null;
        }
    }
    @Component("c2")
    public static class Controller2 implements Controller{

        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().println("this is c2");
            return null;
        }
    }
    @Bean("/c3")
    public Controller Controller3(){
        return (request,response)->{
            response.getWriter().println("this is c3");
            return null;
        };
    }


}
