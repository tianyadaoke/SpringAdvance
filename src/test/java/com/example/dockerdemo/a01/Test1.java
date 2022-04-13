package com.example.dockerdemo.a01;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Test1 {
    static class Person{
        private String name;
        private LocalDateTime nowTime;
        Supplier<LocalDateTime> dateTimeSupplier = LocalDateTime::now;

        public Person(String name) {
            this.name = name;
            this.nowTime = dateTimeSupplier.get();
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", nowTime=" + nowTime +
                    '}';
        }
    }

    @Test
    void testSupplier() throws InterruptedException {
        Supplier<LocalDateTime> dateTimeSupplier = LocalDateTime::now;
        System.out.println(dateTimeSupplier.get());
        TimeUnit.SECONDS.sleep(5);
        System.out.println(dateTimeSupplier.get());
    }

    @Test
    void testSupplier2() throws InterruptedException {
        Person person1=new Person("张三");
        System.out.println(person1);
        TimeUnit.SECONDS.sleep(5);
        Person person2=new Person("李四");
        System.out.println(person2);

    }


}
