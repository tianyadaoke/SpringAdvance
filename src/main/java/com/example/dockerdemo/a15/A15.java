package com.example.dockerdemo.a15;

import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class A15 {
    @Aspect
    static class MyAspect{
        @Before("execution(* foo())")
        public void before(){
            System.out.println("before...");
        }
        @After("execution(* foo())")
        public void after(){
            System.out.println("after...");
        }

    }

    public static void main(String[] args) {
        //1.准备好切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");
        //2.准备好通知
        MethodInterceptor advice = invocation -> {
            System.out.println("before...");
            Object result = invocation.proceed();
            System.out.println("after...");
            return result;
        };
        //3.准备好切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut,advice);
        //4.创建代理
        /**
         * a.目标实现接口，用jdk代理
         * b.目标没有实现接口，用CGLIB代理
         * c.proxyTargetClass=true，总是用CGLIB代理
         */
        Target1 target = new Target1();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);
        // 设置以下接口，可以用jdk代理
        proxyFactory.setInterfaces(target.getClass().getInterfaces());
        //设置为true一直会用CGLIB代理
        proxyFactory.setProxyTargetClass(true);
        I1 proxy= (I1) proxyFactory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();

    }
    interface I1{
        void foo();
        void bar();
    }
    static class Target1 implements I1{
        @Override
        public void foo() {
            System.out.println("target1 foo");
        }

        @Override
        public void bar() {
            System.out.println("target1 bar");
        }
    }
    static class Target2 {

        public void foo() {
            System.out.println("target2 foo");
        }


        public void bar() {
            System.out.println("target2 bar");
        }
    }


}
