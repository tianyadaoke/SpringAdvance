package com.example.dockerdemo.a20;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

public class A20 {
    private final static Log logger = LogFactory.getLog(A20.class);
    public static void main(String[] args) {
        //支持内嵌tomcat的容器的实现
        AnnotationConfigServletWebServerApplicationContext context
                = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);


    }
}
