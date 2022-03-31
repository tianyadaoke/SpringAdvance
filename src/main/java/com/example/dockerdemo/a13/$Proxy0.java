package com.example.dockerdemo.a13;

/**
 * 模仿jdk动态代理
 */
public class $Proxy0 implements A13.Foo {
    private A13.InvocationHandler invocationHandler;

    public $Proxy0(A13.InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }

    @Override
    public void foo() {
        invocationHandler.invoke();
    }

    @Override
    public void bar() {
        invocationHandler.invoke();
    }
}
