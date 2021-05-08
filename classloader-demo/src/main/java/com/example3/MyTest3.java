package com.example3;

import com.example2.CustomClassLoader2;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-25 17:25
 **/
public class MyTest3 {


    @Test
    public void test1() throws Exception {
        String root1 = "C:\\Users\\hguoq\\Desktop\\classloader\\war4\\";
        String root2 = "C:\\Users\\hguoq\\Desktop\\classloader\\war5\\";

        String name = "com.example3.User";
        ClassLoader classLoader = new CustomClassLoader3(root1);
        Class aClass1 = classLoader.loadClass(name);
        System.out.println(aClass1.getClassLoader());
        Object obj1 = aClass1.newInstance();
        Method say = aClass1.getMethod("say");
        say.invoke(obj1,null);


        ClassLoader classLoader2 = new CustomClassLoader3(root2);
        Class aClass2 = classLoader2.loadClass(name);
        System.out.println(aClass2.getClassLoader());

        Object obj2 = aClass2.newInstance();
        Method say2 = aClass2.getMethod("say");
        say2.invoke(obj2,null);

    }
}
