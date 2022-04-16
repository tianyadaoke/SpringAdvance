package com.example.dockerdemo.a23;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;

import java.util.Date;

public class TestFieldAccessor {
    static class MyBean{
        public  int a;public String b;public Date c;
        @Override
        public String toString() {
            return "MyBean{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    ", c=" + c +
                    '}';
        }
    }
    public static void main(String[] args) {
        TestBeanWrapper.MyBean target=new TestBeanWrapper.MyBean();
        DirectFieldAccessor wrapper = new DirectFieldAccessor(target);
        wrapper.setPropertyValue("a","10");
        wrapper.setPropertyValue("b","hello");
        wrapper.setPropertyValue("c","1999/03/04");
        System.out.println(target);
    }
}
