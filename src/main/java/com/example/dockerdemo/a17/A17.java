package com.example.dockerdemo.a17;

import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.BeanFactoryAdvisorRetrievalHelper;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class A17 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1", Aspect1.class);
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        //BeanPostProcessor
        // 创建-》(*)依赖注入-》初始化(*)
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {

            System.out.println(name);
        }
        //AnnotationAwareAspectJAutoProxyCreator creator = AnnotationAwareAspectJAutoProxyCreator.class.getDeclaredConstructor().newInstance();
        // 底层执行Object o1 = creator.wrapIfNessary(new Target1(),"target1","target1")
        // 功能增强
        // ((Target1)o1).foo();


        /*
        AnnotationAwareAspectJAutoProxyCreator creator = AnnotationAwareAspectJAutoProxyCreator.class.getDeclaredConstructor().newInstance();
        Method[] methods = creator.getClass().getSuperclass().getSuperclass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("findEligibleAdvisors")) {
                method.setAccessible(true);
                // 以下反射会有No BeanFactoryAdvisorRetrievalHelper available问题
                method.invoke(creator, Target1.class, "target1");
            }
        }
        */


    }

     static  class Target1 {
        public void foo() {
            System.out.println("target1 foo");
        }
    }

     static class Target2 {
        public void bar() {
            System.out.println("target2 bar");
        }
    }

    @Aspect //高级切面类
    static class Aspect1 {
        @Before("execution(* foo())")
        public void before() {
            System.out.println("aspect1 before...");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("aspect1 after...");
        }
    }

    @Configuration
     static class Config {



        @Bean
        public Advisor advisor3(MethodInterceptor advice3) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut,advice3);
        }

        @Bean
        public MethodInterceptor advice3() {
            return invocation -> {
                System.out.println("advice3 before");
                Object result = invocation.proceed();
                System.out.println("advice3 after");
                return result;
            };
        }
    }
}
