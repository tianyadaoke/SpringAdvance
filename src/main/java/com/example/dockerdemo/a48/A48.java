package com.example.dockerdemo.a48;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Configuration
public class A48 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48.class);
        context.getBean(MyService.class).doBusiness();
        context.close();

    }

    static class MyEvent extends ApplicationEvent{

        public MyEvent(Object source) {
            super(source);
        }
    }
    @Component
    static class MyService{
        @Autowired
        private ApplicationEventPublisher publisher;

        public void doBusiness(){
            System.out.println("主线业务");
            publisher.publishEvent(new MyEvent("MyService.doBusiness()"));
//            System.out.println("发送短信");
//            System.out.println("发送邮件");

        }
    }

    @Component
    static class SmsApplicationListener implements ApplicationListener<MyEvent>{
        @Override
        public void onApplicationEvent(MyEvent event) {
            System.out.println("发送短信");
        }
    }
    @Component
    static class EmailApplicationListener implements ApplicationListener<MyEvent>{
        @Override
        public void onApplicationEvent(MyEvent event) {
            System.out.println("发送邮件");
        }
    }

}