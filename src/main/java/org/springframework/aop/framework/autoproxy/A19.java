package org.springframework.aop.framework.autoproxy;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class A19 {
    @Aspect
    static class MyAspect{
        @Before("execution(* foo(..))") //静态通知调用,不带参数绑定,执行时不需要切点
        public void before1(){
            System.out.println("before1");
        }
        @Before("execution(* foo(..))&& args(x)") //动态通知调用，需要参数绑定，执行时还需要切点对象
        public void before2(int x){
            System.out.printf("before2 (%d)%n",x);
        }
    }
    static class Target{
        public void foo(int x ){
            System.out.printf("target foo(%d)%n",x);
        }
    }
    @Configuration
    static class MyConfig{
        @Bean
        AnnotationAwareAspectJAutoProxyCreator proxyCreator(){
            return new AnnotationAwareAspectJAutoProxyCreator();
        }
        @Bean
        public MyAspect myAspect(){
            return new MyAspect();
        }
    }

    public static void main(String[] args) throws Throwable {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(MyConfig.class);
        context.refresh();

        AnnotationAwareAspectJAutoProxyCreator proxyCreator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        List<Advisor> advisors = proxyCreator.findEligibleAdvisors(Target.class, "target");

        Target target=new Target();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisors(advisors);
        Object proxy = proxyFactory.getProxy();

        List<Object> interceptorList = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(Target.class.getMethod("foo", int.class), Target.class);
        for (Object o : interceptorList) {
            System.out.println(o);
        }

        ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(
                proxy,target,Target.class.getMethod("foo",int.class),new Object[]{100},Target.class,interceptorList
        ){};
        invocation.proceed();
    }

}
