package com.example.dockerdemo.a23;

import org.springframework.beans.SimpleTypeConverter;

import java.util.Date;

public class TestSimpleConverter {
    public static void main(String[] args) {
        SimpleTypeConverter converter = new SimpleTypeConverter();
        Integer integer = converter.convertIfNecessary("13", int.class);
        Date date = converter.convertIfNecessary("1999/03/04", Date.class);
        System.out.println(integer);
        System.out.println(date);
    }
}
