package com.example.dockerdemo.a23;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;

import java.util.Date;

/**
 * Servlet环境下的数据绑定
 */
public class TestServletDataBinder {
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
        ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(target);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("a","10");
        request.setParameter("b","hello");
        request.setParameter("c","1999/03/04");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(target);


    }
}
