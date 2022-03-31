package com.example.dockerdemo.a12;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * Cglib代理是子类，所以目标对象不能为final
 */
public class CglibProxyDemo {
    static  class Target{
        public void foo(){
            System.out.println("target foo");
        }
    }
    public static void main(String[] args) {
        Target target=new Target();
        Target proxy = (Target) Enhancer.create(Target.class, (MethodInterceptor) (o, method, params, methodProxy) -> {
            System.out.println("before...");
            // Object result = method.invoke(target,params); // 用方法反射调用目标
            //methodProxy可以避免反射调用
            //Object result = methodProxy.invoke(target, params); // 内部没用反射，需要目标，Spring使用的是这个
            Object result = methodProxy.invokeSuper(o, params); // 内部没用反射，需要代理，连target都可以省略
            System.out.println("after...");
            return result;
        });
        proxy.foo();
    }
}
