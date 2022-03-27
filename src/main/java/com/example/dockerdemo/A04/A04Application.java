package com.example.dockerdemo.A04;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * bean后处理器的作用
 */
public class A04Application {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
        context.registerBean("bean4",Bean4.class);
        // 可以解析@value
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 可解析@autowired
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 可以解析@resource @postconstruct @predestroy
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 解析ConfigurationProperties
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());
        context.refresh();
        System.out.println("context.getBean(\"bean4\") = " + context.getBean("bean4"));
        context.close();

    }
}
