package com.example.dockerdemo.a13;

public class A13 {
    interface Foo {
        void foo();
        void bar();
    }

    static class Target implements Foo {

        @Override
        public void foo() {
            System.out.println("target foo");
        }

        @Override
        public void bar() {
            System.out.println("target bar");
        }
    }
    interface InvocationHandler{
         void invoke();
    }
    public static void main(String[] args) {
         Foo proxy = new $Proxy0(new InvocationHandler() {
             @Override
             public void invoke() {
                 System.out.println("before...");
                 new A13.Target().foo();
                 System.out.println("after...");
             }
         });
         proxy.foo();
    }

}
