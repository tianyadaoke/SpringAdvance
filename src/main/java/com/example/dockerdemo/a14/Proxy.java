package com.example.dockerdemo.a14;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Proxy extends Target{
    private MethodInterceptor interceptor;

    public void setInterceptor(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }
    // >>>>>>>>>>>>>>>>>>>带原始功能的方法
    public void saveSuper(){
        super.save();
    }
    public void saveSuper(int i){
        super.save(i);
    }
    public void saveSuper(long j){
        super.save(j);
    }

    static Method save0;
    static Method save1;
    static Method save2;
    static MethodProxy save0Proxy;
    static MethodProxy save1Proxy;
    static MethodProxy save2Proxy;
    static{
        try {
            save0=Target.class.getDeclaredMethod("save");
            save1=Target.class.getDeclaredMethod("save",int.class);
            save2=Target.class.getDeclaredMethod("save",long.class);
            save0Proxy=MethodProxy.create(Target.class,Proxy.class,"()V","save","saveSuper");
            save1Proxy=MethodProxy.create(Target.class,Proxy.class,"(I)V","save","saveSuper");
            save2Proxy=MethodProxy.create(Target.class,Proxy.class,"(J)V","save","saveSuper");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    // >>>>>>>>>>>>>>>>>>>带增强功能的方法
    @Override
    public void save() {
        try {
            interceptor.intercept(this,save0,new Object[0],null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void save(int i) {
        try {
            interceptor.intercept(this,save1,new Object[]{i},null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void save(long j) {
        try {
            interceptor.intercept(this,save2,new Object[]{j},null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
