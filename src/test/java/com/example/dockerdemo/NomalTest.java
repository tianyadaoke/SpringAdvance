package com.example.dockerdemo;

import org.junit.jupiter.api.Test;

public class NomalTest {
    @Test
    void test1(){
        String name="123.pdf";
        String[] split = name.split("\\.");
        System.out.println(split[0]);

    }
}
