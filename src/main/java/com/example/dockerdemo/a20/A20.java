package com.example.dockerdemo.a20;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.stream.IntStream;

public class A20 {
    private final static Log logger = LogFactory.getLog(A20.class);

    public static void main(String[] args) throws Exception {
        //支持内嵌tomcat的容器的实现
        AnnotationConfigServletWebServerApplicationContext context
                = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        //解析@RequestMapping以及派生注解，生成路径和控制器方法的映射，在初始化时就生成了
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        //获取映射结果
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((k,v)-> System.out.println(k+":"+v));
        //请求来了，获取控制器的方法, 返回处理器执行链对象
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test1");
        MockHttpServletRequest request2 = new MockHttpServletRequest("POST", "/test2");
        request2.setParameter("name","张三");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        HandlerExecutionChain chain2 = handlerMapping.getHandler(request2);
        System.out.println(chain);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // handlerAdaptor作用：调用控制器方法
        MyRequestMappingHandlerAdaptor handlerAdapter = context.getBean(MyRequestMappingHandlerAdaptor.class);
        handlerAdapter.invokeHandlerMethod(request,response,(HandlerMethod) chain.getHandler());
        handlerAdapter.invokeHandlerMethod(request2,response,(HandlerMethod) chain2.getHandler());

    }
}
