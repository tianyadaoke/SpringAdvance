package com.example.dockerdemo.a23.sub;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestGenericType {
    public static void main(String[] args) {
        // 获取泛型参数 jdk api
        Type type = StudentDao.class.getGenericSuperclass();
        System.out.println("type = " + type);
        if (type instanceof ParameterizedType p) {
            System.out.println("p.getActualTypeArguments()[0] = " + p.getActualTypeArguments()[0]);
        }
        // Spring api
        Class<?> t = GenericTypeResolver.resolveTypeArgument(StudentDao.class, BaseDao.class);
        System.out.println(t);

    }
}
