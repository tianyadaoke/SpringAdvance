package com.example.dockerdemo.a13;

public class A13 {
    interface Foo {
        void foo();

        int bar();
    }

    static class Target implements Foo {

        @Override
        public void foo() {
            System.out.println("target foo");
        }

        @Override
        public int bar() {
            System.out.println("target bar");
            return 100;
        }
    }

    interface InvocationHandler {
        Object invoke();
    }

    public static void main(String[] args) {
        Foo proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke() {
                System.out.println("before...");
                return new A13.Target().bar();
            }
        });
        proxy.foo();
    }

}
