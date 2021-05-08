package com.example2;

import java.lang.reflect.Method;

/**
 * @description: 未打破双亲委派
 * @author: huangguoqiang
 * @create: 2021-04-24 19:40
 **/
public class MyTest2 {
    @org.junit.Test
    public void test1() throws Exception {
        String root = "C:\\Users\\hguoq\\Desktop\\classloader\\";

        String name = "com.example2.Student";
        ClassLoader classLoader = new CustomClassLoader2(root);
        Class aClass = classLoader.loadClass(name);
        System.out.println(aClass.getClassLoader());

    }

    @org.junit.Test
    public void test2() {
        try {
            String root = "C:\\Users\\hguoq\\Desktop\\classloader\\";

            String name = "com.example2.Student";

            ClassLoader classLoader = new CustomClassLoader2(root);
            Class classC = classLoader.loadClass(name);
            Object obj1 = classC.newInstance();


            ClassLoader classLoader2 = new CustomClassLoader2(root);
            Class classC2 = classLoader2.loadClass(name);
            Object obj2 = classC2.newInstance();

            System.out.println(classC == classC2);
            System.out.println(obj1.getClass().getClassLoader().toString());
            System.out.println(obj2.getClass().getClassLoader().toString());

            Method setSampleMethod = classC.getMethod("setSample", java.lang.Object.class);
            setSampleMethod.invoke(obj1, obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
