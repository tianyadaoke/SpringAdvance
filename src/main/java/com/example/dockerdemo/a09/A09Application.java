package com.example.dockerdemo.a09;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 多例注入单例会有问题，可以用
 * 1. @lazy才能生成多例
 * 2. 或者在注入目标的scope中设置proxyMode
 * 3. 或者采用对象工厂ObjectFactory<F3>
 * 4. 或者用applicationContext.getBean()
 */
@ComponentScan("com.example.dockerdemo.a09")
public class A09Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A09Application.class);
        E e = context.getBean(E.class);
        System.out.println("f1:"+e.getF1());
        System.out.println("f1:"+e.getF1());

        System.out.println("f2:"+e.getF2());
        System.out.println("f2:"+e.getF2());

        System.out.println("f3:"+e.getF3());
        System.out.println("f3:"+e.getF3());

        System.out.println("f4:"+e.getF4());
        System.out.println("f4:"+e.getF4());
        context.close();
    }
}
