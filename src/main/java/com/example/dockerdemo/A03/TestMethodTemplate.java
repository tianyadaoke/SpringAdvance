package com.example.dockerdemo.A03;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试模板方法, 把不变的地方固定，变化的地方设置为接口
 */
public class TestMethodTemplate {
    public static void main(String[] args) {
        MyBeanFactory beanFactory=new MyBeanFactory();
        beanFactory.addBeanPostProcessor(bean -> System.out.println("autoweired解析"));
        beanFactory.addBeanPostProcessor(bean -> System.out.println("resource解析"));
        beanFactory.getBean();

    }
    static class MyBeanFactory{
        public Object getBean(){
            Object bean=new Object();
            System.out.println("构造"+bean);
            System.out.println("依赖注入"+bean); //@autowired @resource
            for(BeanPostProcessor processor:processors){
                processor.inject(bean);
            }
            System.out.println("初始化"+bean);
            return bean;
        }
        private List<BeanPostProcessor> processors=new ArrayList<>();
        public void addBeanPostProcessor(BeanPostProcessor processor){
            processors.add(processor);
        }
    }
    static interface BeanPostProcessor{
        public void inject(Object bean); // 对依赖注入阶段的扩展
    }
}
