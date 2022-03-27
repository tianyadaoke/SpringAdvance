package com.example.dockerdemo.A04;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DigInAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2",new Bean2());
        beanFactory.registerSingleton("bean3",new Bean3());
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);//${}解析器

        AutowiredAnnotationBeanPostProcessor processor=new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(beanFactory);
        Bean1 bean1 = new Bean1();
//        System.out.println(bean1);
//        processor.postProcessProperties(null,bean1,"bean1");//执行依赖注入解析@autowired @value
//        System.out.println(bean1 );
        Method method = AutowiredAnnotationBeanPostProcessor.class
                .getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        method.setAccessible(true);
        InjectionMetadata metadata = (InjectionMetadata) method.invoke(processor, "bean1", Bean1.class, null);//获取Bean1上加了@autowired和@value的信息
        System.out.println(metadata);
        metadata.inject(bean1,"bean1",null);
        System.out.println(bean1);

        //如何按类型查找值
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        DependencyDescriptor descriptor = new DependencyDescriptor(bean3, false);
        Object o = beanFactory.doResolveDependency(descriptor, null, null, null);
        System.out.println(o);

        //如果按照方法查找
        Method setBean2=Bean1.class.getDeclaredMethod("setBean2",Bean2.class);
        DependencyDescriptor descriptor1 = new DependencyDescriptor((new MethodParameter(setBean2, 0)),false);
        Object o1 = beanFactory.doResolveDependency(descriptor1, null, null, null);
        System.out.println(o1);

        // 值注入
        Method setHome=Bean1.class.getDeclaredMethod("setHome",String.class);
        DependencyDescriptor descriptor2 = new DependencyDescriptor((new MethodParameter(setHome, 0)),true);
        Object o2 = beanFactory.doResolveDependency(descriptor2, null, null, null);
        System.out.println(o2);
    }
}
