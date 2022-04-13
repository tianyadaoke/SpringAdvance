package com.example.dockerdemo.a20;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;

@Controller
public class Controller1 {
    static class  User{

        private String name;
        private Integer age;

        public User() {
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

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }
    private final static Log logger = LogFactory.getLog(Controller1.class);
    @GetMapping("/test1")
    public ModelAndView test1(){
        logger.info("test1()");
        return null;
    }
    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name){
        logger.info("test2:"+name);
        return null;
    }
    @PutMapping("/test3")
    public ModelAndView test3(@Token String token){
        logger.info("test3:"+token);
        return null;
    }
    @RequestMapping("/test4")
    @Yml
    public User test4(){
        logger.info("test4()");
        return new User("zhangsan",18);
    }

    public static void main(String[] args) {
        String str = new Yaml().dump(new User("张三", 25));
        System.out.println(str);
    }



}
