package com.example.dockerdemo.a41;

import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

public class A41 {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config",Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class); //解析Configuration
        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
    @Configuration
    //@Import({AutoConfiguration1.class,AutoConfiguration2.class})
    @Import(MyImportSelector.class)
    static class Config{
        //本项目配置类,导入其他配置类
    }
    // 实现配置导入
    static class MyImportSelector implements ImportSelector{
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{AutoConfiguration1.class.getName(),AutoConfiguration2.class.getName()};
        }
    }

    static class Bean1{}
    static class Bean2{}
    @Configuration
    static class AutoConfiguration1{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }
    }
    @Configuration
    static class AutoConfiguration2{
        @Bean
        public Bean1 bean2(){
            return new Bean1();
        }
    }
}
