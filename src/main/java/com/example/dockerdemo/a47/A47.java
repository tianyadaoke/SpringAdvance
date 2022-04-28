package com.example.dockerdemo.a47;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Configuration
public class A47 {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A47.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        System.out.println("----------------------------------------------");
        //1.根据属性
        DependencyDescriptor dd1=new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"),false);
        System.out.println(beanFactory.doResolveDependency(dd1, "bean1", null, null));
        //2.根据参数的类型
        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2, 0), false);
        System.out.println(beanFactory.doResolveDependency(dd2, "bean1", null, null));
        //3.结果包装为Optional<Bean2>
        DependencyDescriptor dd3=new DependencyDescriptor(Bean1.class.getDeclaredField("bean3"),false);
        while(dd3.getDependencyType()==Optional.class){
            dd3.increaseNestingLevel();
        }
        Object result = beanFactory.doResolveDependency(dd3, "bean3", null, null);
        System.out.println(Optional.ofNullable(result));
        //4.结果为ObjectFactory
        DependencyDescriptor dd4=new DependencyDescriptor(Bean1.class.getDeclaredField("bean4"),false);
        while(dd4.getDependencyType()==ObjectFactory.class){
            dd4.increaseNestingLevel();
        }
        ObjectFactory objectFactory=new ObjectFactory() {
            @Override
            public Object getObject() throws BeansException {
                Object result1 = beanFactory.doResolveDependency(dd4, "bean4", null, null);
                return result1;
            }
        };
        System.out.println(objectFactory.getObject());

    }
    static class Bean1{
        @Autowired
        private Bean2 bean2;
        @Autowired
        public void setBean2(@Lazy Bean2 bean2){this.bean2=bean2;}
        @Autowired
        private Optional<Bean2> bean3;
        @Autowired
        private ObjectFactory<Bean2> bean4;
    }
    @Component("bean2")
    static class Bean2{}
}
