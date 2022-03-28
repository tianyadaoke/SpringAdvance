package com.example.dockerdemo.a05;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.invoke.VarHandle;
import java.util.Map;
import java.util.Set;

public class A05Application {
    public static void main(String[] args) throws IOException {
        GenericApplicationContext context=new GenericApplicationContext();
         context.registerBean("config",Config.class);
        // 识别@ComponentScan @Bean
        // context.registerBean(ConfigurationClassPostProcessor.class);
        // 识别mapperScanner
        //context.registerBean(MapperScannerConfigurer.class,bd->{
       //     bd.getPropertyValues().add("basePackage","com.example.dockerdemo.a05.mapper");
        //});
        //  初始化容器

        //模仿是上面的代码
        context.registerBean(ComponentScanPostProcessor.class);
//        ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
//        if(componentScan!=null){
//            for (String basePackage : componentScan.basePackages()) {
//                System.out.println(basePackage);
//                String path="classpath*:"+basePackage.replace(".","/")+"/**/*.class";
//                System.out.println(path);
//                CachingMetadataReaderFactory factory=new CachingMetadataReaderFactory();
//                Resource[] resources = context.getResources(path);
//                for (Resource resource : resources) {
//                    System.out.println(resource);
//                    MetadataReader reader = factory.getMetadataReader(resource);
//                    BeanNameGenerator generator=new AnnotationBeanNameGenerator();
//                    System.out.println("类名 = " + reader.getClassMetadata().getClassName());
//                    System.out.println(" 是否加了component注解 " + reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName()));
//                    if(reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName())||
//                            reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())){
//                        AbstractBeanDefinition beanDefinition =
//                                BeanDefinitionBuilder.genericBeanDefinition(reader.getClassMetadata().getClassName()).getBeanDefinition();
//                        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
//                        String name=generator.generateBeanName(beanDefinition, beanFactory);
//                        beanFactory.registerBeanDefinition(name,beanDefinition);
//                    }
//                }
//            }
//        }
        System.out.println("------methods------");
//        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
//        MetadataReader reader = factory.getMetadataReader(new ClassPathResource("com/example/dockerdemo/a05/Config.class"));
//        Set<MethodMetadata> methods = reader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
//        methods.forEach(m->{
//            System.out.println(m);
//            String initMethod = m.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
//            builder.setFactoryMethodOnBean(m.getMethodName(),"config");
//            builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
//            if(initMethod.length()>0){
//                builder.setInitMethodName(initMethod);
//            }
//            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
//            context.getDefaultListableBeanFactory().registerBeanDefinition(m.getMethodName(),beanDefinition);
//        });
        // 代替以上代码
        context.registerBean(AtBeanPostProcessor.class);
        System.out.println("------methods------");
        context.refresh();
        System.out.println("------getBeanDefinitionNames------");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("------getBeanDefinitionNames------");
        // 销毁容器
        context.close();
    }
}
