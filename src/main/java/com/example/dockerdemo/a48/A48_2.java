package com.example.dockerdemo.a48;

import com.alibaba.druid.support.logging.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;



@Configuration
@Slf4j
public class A48_2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_2.class);
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
            log.info("主线业务");
            publisher.publishEvent(new MyEvent("MyService.doBusiness()"));
        }
    }

    @Component
    static class SmsApplicationListener {
        @EventListener
        public void listener(MyEvent myEvent){
            log.info("发送短信");
        }
    }
    @Component
    static class EmailApplicationListener {
        @EventListener
        public void listener(MyEvent myEvent){
            log.info("发送邮件");
        }
    }

    @Bean
    public ThreadPoolTaskExecutor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        return executor;
    }

    @Bean
    public SimpleApplicationEventMulticaster simpleApplicationEventMulticaster(ThreadPoolTaskExecutor executor){
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(executor);
        return multicaster;
    }

}