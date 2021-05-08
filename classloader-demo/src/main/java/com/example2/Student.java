package com.example2;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-24 19:33
 **/
public class Student {

    private Student instance;

    public void setSample(Object instance) {
        this.instance = (Student) instance;
    }

    public String getName() {
        return "test1";
    }




}