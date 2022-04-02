package com.example.dockerdemo.a14;


public class A14 {
    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        Target target = new Target();
        proxy.setInterceptor((o, method, objects, methodProxy) -> {
            System.out.println("before....");
            // return method.invoke(target, objects);
            return methodProxy.invoke(target,objects); //内部无反射，结合目标用
        });
        proxy.save();
        proxy.save(1);
        proxy.save(2L);
    }
}
