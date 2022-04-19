package com.example.dockerdemo.a23;

import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class TestServletDataBinderFactory {
    static class User{
        private Date birthday;
        private Address address;

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "User{" +
                    "birthday=" + birthday +
                    ", address=" + address +
                    '}';
        }
    }
    public static class Address{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
    static class MyController{
        @InitBinder
        // 或者用默认的DefaultFormattingConversionService配合@DateTimeFormat(pattern="yyyy|MM|dd")使用
        public void aaa(WebDataBinder dataBinder){

            //扩展dataBinder转换器
            dataBinder.addCustomFormatter(new MyDateFormatter("用@InitBinder扩展"));
        }
    }


    public static void main(String[] args) throws Exception {
        User target=new User();
        MockHttpServletRequest request=new MockHttpServletRequest();
        request.setParameter("birthday","1991|01|02");
        request.setParameter("address.name","西安");
        InvocableHandlerMethod method = new InvocableHandlerMethod(new MyController(),MyController.class.getMethod("aaa",WebDataBinder.class));
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(List.of(method),null);
        WebDataBinder dataBinder=factory.createBinder(new ServletWebRequest(request),target,"user");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(target);


    }
}
