package com.example.dockerdemo.a42;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

public class A42TestAopauto {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        // 加入类似于properties的参数
        StandardEnvironment env=new StandardEnvironment();
        env.getPropertySources().addLast(new SimpleCommandLinePropertySource("--spring.aop.auto=true"));
        context.setEnvironment(env);

        DefaultListableBeanFactory defaultListableBeanFactory = context.getDefaultListableBeanFactory();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(defaultListableBeanFactory);
        context.registerBean(Config.class);
        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
    @Configuration
    @Import(MyImportSelector.class)
    static class Config{}
    static class MyImportSelector implements DeferredImportSelector{
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{
                AopAutoConfiguration.class.getName()
            };
        }
    }
}
