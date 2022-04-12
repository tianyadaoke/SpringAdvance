package com.example.dockerdemo.a18;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class A18_1 {
    static class Target{
        public void foo(){
            System.out.println("Target foo()");
        }
    }
    static class Advice1 implements MethodInterceptor{
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Advice1 before");
            Object result = invocation.proceed();//调用下一个通知或目标
            System.out.println("Advice1 after");
            return result;
        }
    }
    static class Advice2 implements MethodInterceptor{
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Advice2 before");
            Object result = invocation.proceed();//调用下一个通知或目标
            System.out.println("Advice2 after");
            return result;
        }
    }

    static class MyInvocation implements MethodInvocation{
        private Object target;
        private Method method;
        private Object[] args;
        List<MethodInterceptor> methodInterceptorList;
        private int count=1; //调用次数


        public MyInvocation(Object target, Method method, Object[] args, List<MethodInterceptor> methodInterceptorList) {
            this.target = target;
            this.method = method;
            this.args = args;
            this.methodInterceptorList = methodInterceptorList;
        }

        @Override
        public Method getMethod() {
            return this.method;
        }

        @Override
        public Object[] getArguments() {
            return this.args;
        }

        @Override
        public Object proceed() throws Throwable {
            // 调用每一个环绕通知，调用目标
            if(count>methodInterceptorList.size()){
                //调用目标，返回并结束递归
                Object result = method.invoke(target, args);
                return result;

            }
            // 逐一调用通知
            MethodInterceptor methodInterceptor = methodInterceptorList.get(count++ - 1);
            return methodInterceptor.invoke(this);
        }

        @Override
        public Object getThis() {
            return this.target;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return method;
        }
    }

    public static void main(String[] args) throws Throwable {
        Target target = new Target();
        List<MethodInterceptor> list = List.of(
                new Advice1(),new Advice2()
        );
        MyInvocation invocation = new MyInvocation(target,Target.class.getMethod("foo"),new Object[0],list);
        invocation.proceed();
    }
}
