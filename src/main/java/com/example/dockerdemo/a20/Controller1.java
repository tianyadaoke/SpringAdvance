package com.example.dockerdemo.a20;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controller1 {
    record User(String name,Integer age){}
    private final static Log logger = LogFactory.getLog(Controller1.class);
    @GetMapping("/test1")
    public ModelAndView test1(){
        logger.info("test1()");
        return null;
    }
    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name){
        logger.debug("test2:"+name);
        return null;
    }
    @PutMapping("/test3")
    public ModelAndView test3(@Token String token){
        logger.debug("test3:"+token);
        return null;
    }
    @RequestMapping("/test4.yml")
    @Yml
    public User test4(){
        logger.debug("test4()");
        return new User("zhangsan",18);
    }





}
