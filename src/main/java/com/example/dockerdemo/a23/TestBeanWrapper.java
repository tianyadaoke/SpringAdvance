package com.example.dockerdemo.a23;

import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;

public class TestBeanWrapper {
    static class MyBean{
        public  int a;public String b;public Date c;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public Date getC() {
            return c;
        }

        public void setC(Date c) {
            this.c = c;
        }

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
        MyBean target=new MyBean();
        BeanWrapperImpl wrapper = new BeanWrapperImpl(target);
        wrapper.setPropertyValue("a","10");
        wrapper.setPropertyValue("b","hello");
        wrapper.setPropertyValue("c","1999/03/04");
        System.out.println(target);


    }
}
