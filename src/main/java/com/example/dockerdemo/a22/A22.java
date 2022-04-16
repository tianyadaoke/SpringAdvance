package com.example.dockerdemo.a22;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class A22 {
    public static void main(String[] args) throws NoSuchMethodException {
        //1. 反射获取参数名
        Method foo = Bean2.class.getMethod("foo", String.class, int.class);
        for (Parameter parameter : foo.getParameters()) {
            System.out.println(parameter.getName());
        }
    }
}
