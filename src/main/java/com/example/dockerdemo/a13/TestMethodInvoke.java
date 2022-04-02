package com.example.dockerdemo.a13;
import java.lang.reflect.Method;

public class TestMethodInvoke {
    public static void foo(int i){
        System.out.println(i+":"+"foo");
    }
    private static void show(int i, Method foo){
        System.out.println(i+":"+ System.identityHashCode(foo));
    }
    public static void main(String[] args) throws Exception {
        Method foo=TestMethodInvoke.class.getDeclaredMethod("foo",int.class);
        for (int i = 0; i < 17; i++) {
            show(i,foo);
            foo.invoke(null,i);
        }
        System.in.read();
    }
}
