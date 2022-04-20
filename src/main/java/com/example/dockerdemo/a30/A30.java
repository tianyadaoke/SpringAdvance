package com.example.dockerdemo.a30;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class A30 {
    public static void main(String[] args) throws NoSuchMethodException {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
        resolver.afterPropertiesSet();
        // 测试json
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerMethod handlerMethod=new HandlerMethod(new Controller1(),Controller1.class.getMethod("foo"));
        Exception e = new ArithmeticException("除以0");
        resolver.resolveException(request,response,handlerMethod,e);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
        // 测试mav
        handlerMethod=new HandlerMethod(new Controller2(),Controller2.class.getMethod("foo"));
        ModelAndView modelAndView = resolver.resolveException(request, response, handlerMethod, e);
        System.out.println(modelAndView.getModel());
        System.out.println(modelAndView.getViewName());

    }
    static class Controller1{
        public void foo(){

        }
        @ExceptionHandler
        @ResponseBody
        public Map<String,Object> handle(ArithmeticException e){
            return Map.of("error",e.getMessage());
        }
    }
    static class Controller2{
        public void foo(){}
        @ExceptionHandler
        public ModelAndView handle(ArithmeticException e){
            return new ModelAndView("test2",Map.of("error",e.getMessage()));
        }

    }
}
