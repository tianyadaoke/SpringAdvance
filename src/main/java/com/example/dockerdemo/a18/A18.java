package com.example.dockerdemo.a18;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class A18 {
    public static void main(String[] args) {
        // 高级切面转低级切面
        List<Advisor> advisors=new ArrayList<>();
        for (Method method : Aspect.class.getDeclaredMethods()) {
            AspectInstanceFactory factory = new SingletonAspectInstanceFactory(new Aspect());
            if (method.isAnnotationPresent(Before.class)) {
                // 解析切点
                String expression = method.getAnnotation(Before.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                AspectJMethodBeforeAdvice beforeAdvice = new AspectJMethodBeforeAdvice(method, pointcut, factory);
                // 切面
                DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
                advisors.add(advisor);
            } else if(method.isAnnotationPresent(AfterReturning.class)) {
                // 解析切点
                String expression = method.getAnnotation(AfterReturning.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                AspectJAfterReturningAdvice beforeAdvice = new AspectJAfterReturningAdvice(method, pointcut, factory);
                // 切面
                DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
                advisors.add(advisor);
            }
            else if(method.isAnnotationPresent(Around.class)) {
                // 解析切点
                String expression = method.getAnnotation(Around.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 通知类
                AspectJAroundAdvice beforeAdvice = new AspectJAroundAdvice(method, pointcut, factory);
                // 切面
                DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
                advisors.add(advisor);
            }
        }
        for (Advisor advisor : advisors) {
            System.out.println(advisor);
        }
    }

    static class Aspect {
        @Before("execution(* foo())")
        public void before1() {
            System.out.println("before1");
        }

        @Before("execution(* foo())")
        public void before2() {
            System.out.println("before2");
        }

        public void after() {
            System.out.println("after");
        }

        @AfterReturning("execution(* foo())")
        public void afterReturning() {
            System.out.println("after returning");
        }

        @Around("execution(* foo())")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            return pjp.proceed();
        }

    }

    static class Target {
        public void foo() {
            System.out.println("foo");
        }
    }
}
