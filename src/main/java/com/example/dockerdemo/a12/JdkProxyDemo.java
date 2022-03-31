package com.example.dockerdemo.a12;

import java.lang.reflect.Proxy;

/**
 * jdk代理和cglib代理
 * jdk代理是针对接口的代理,代理的类可以为final
 */
public class JdkProxyDemo {

    interface Foo{
        void foo();
    }
    static class Target implements Foo{
        @Override
        public void foo() {
            System.out.println("target foo");
        }
    }
    public static void main(String[] args) {
        // 目标对象
        Target target = new Target();
        // 用来加载在运行期间动态生成的字节码
        ClassLoader loader=JdkProxyDemo.class.getClassLoader();
        Foo proxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, (p, method, args1) -> {
            // 后置增强
            System.out.println("before...");
            // 目标.方法(参数)
            // 方法.invoke(目标)
            Object result = method.invoke(target, args1);
            // 前置增强
            System.out.println("after...");
            // 返回目标方法执行的结果
            return result;
        });
        proxy.foo();
    }
}
