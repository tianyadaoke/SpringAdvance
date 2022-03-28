package com.example.dockerdemo.a05;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class ComponentScanPostProcessor implements BeanFactoryPostProcessor {

    @Override //context.refresh()调用
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
            if(componentScan!=null){
                for (String basePackage : componentScan.basePackages()) {
                    //System.out.println(basePackage);
                    String path="classpath*:"+basePackage.replace(".","/")+"/**/*.class";
                    //System.out.println(path);
                    CachingMetadataReaderFactory factory=new CachingMetadataReaderFactory();
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
                    for (Resource resource : resources) {
                        System.out.println(resource);
                        MetadataReader reader = factory.getMetadataReader(resource);
                        BeanNameGenerator generator=new AnnotationBeanNameGenerator();
                        //System.out.println("类名 = " + reader.getClassMetadata().getClassName());
                       // System.out.println(" 是否加了component注解 " + reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName()));
                        if(reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName())||
                                reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())){
                            if(configurableListableBeanFactory instanceof DefaultListableBeanFactory){
                                DefaultListableBeanFactory beanFactory= (DefaultListableBeanFactory) configurableListableBeanFactory;
                                AbstractBeanDefinition beanDefinition =
                                        BeanDefinitionBuilder.genericBeanDefinition(reader.getClassMetadata().getClassName()).getBeanDefinition();
                                String name=generator.generateBeanName(beanDefinition, beanFactory);
                                beanFactory.registerBeanDefinition(name,beanDefinition);
                            }

                        }
                    }
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
