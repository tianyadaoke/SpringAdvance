package com.example.dockerdemo.a21;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPart;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.print.DocFlavor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class A21 {
    public static void main(String[] args) throws NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Webconfig.class);
        // 创建测试请求
        HttpServletRequest multiRequest = mockRequest();
        // 自己创建HandlerMethod
        HandlerMethod handlerMethod = new HandlerMethod(new Controller(), Controller.class.getMethod("test", String.class, String.class, int.class, String.class, MultipartFile.class, int.class, String.class, String.class, String.class, HttpServletRequest.class, Controller.User.class, Controller.User.class, Controller.User.class));
        // 准备对象转换和类型绑定
        // 准备ModelAndView容器用来存储中间Model结果
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 解析每个参数值
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        for (MethodParameter methodParameter : methodParameters) {
            String annotations = Arrays.stream(methodParameter.getParameterAnnotations()).map(a -> a.annotationType().getSimpleName()).collect(Collectors.joining());
            String str=annotations.length()>0?"@"+annotations+" ":" ";
            methodParameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
            System.out.println("["+methodParameter.getParameterIndex()+"]"+str+methodParameter.getParameterType().getSimpleName()+" "+methodParameter.getParameterName())
            ;
        }
    }

    private static HttpServletRequest mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1","zhangsan");
        request.setParameter("name2","lisi");
        request.addPart(new MockPart("file","abc","hello".getBytes(StandardCharsets.UTF_8)));
        Map<String, String> map = new AntPathMatcher().extractUriTemplateVariables("/test/{id}", "/test/123");
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,map);
        request.setContentType("application/json");
        request.setCookies(new Cookie("token","123456"));
        request.setParameter("name","张三");
        request.setParameter("age","18");
        return request;

    }

    static class Controller{
        public void test(
                @RequestParam("name1") String name1,
                String name2,
                @RequestParam("age") int age,
                @RequestParam(name="home",defaultValue = "${JAVA_HOME}") String home1,
                @RequestParam("file")MultipartFile file,
                @PathVariable("id") int id,
                @RequestHeader("Content-Type") String header,
                @CookieValue("token") String token,
                @Value("${JAVA_HOME}") String home2,
                HttpServletRequest request,
                @ModelAttribute User user1,
                User user2,
                @RequestBody User user3
                ){

        }

        static class  User{
            private String name;
            private Integer age;

            public User() {
            }

            public User(String name, Integer age) {
                this.name = name;
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getAge() {
                return age;
            }

            public void setAge(Integer age) {
                this.age = age;
            }
        }
    }


}
