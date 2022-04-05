package com.example.dockerdemo.a16;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

public class A16 {
    public static void main(String[] args) throws NoSuchMethodException {
        // 根据方法匹配
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* bar())");
        System.out.println(pointcut.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pointcut.matches(T1.class.getMethod("bar"), T1.class));
        System.out.println("------------------------");
        // 根据注解匹配
        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
        System.out.println(pointcut2.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pointcut2.matches(T1.class.getMethod("bar"), T1.class));
        System.out.println("------------------------");

        StaticMethodMatcher pointcut3 = new StaticMethodMatcher() {

            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                MergedAnnotations annotations = MergedAnnotations.from(method);
                // 检查方法上是否加了Transactional注解
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                // 检查类上是否加了Transactional注解
                annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                return false;

            }
        };
        System.out.println(pointcut3.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pointcut3.matches(T1.class.getMethod("bar"), T1.class));
        System.out.println(pointcut3.matches(T2.class.getMethod("foo"), T2.class));
        System.out.println(pointcut3.matches(T2.class.getMethod("bar"), T2.class));
        System.out.println("------------------------");
        System.out.println(pointcut3.matches(T3.class.getMethod("foo"), T3.class));


    }

    class T1 {
        @Transactional
        public void foo() {
        }

        public void bar() {
        }
    }

    @Transactional
    class T2 {
        public void foo() {
        }

        public void bar() {
        }
    }

    @Transactional
    interface I3 {
        void foo();
    }

    class T3 implements I3 {
        @Override
        public void foo() {

        }
    }
}
