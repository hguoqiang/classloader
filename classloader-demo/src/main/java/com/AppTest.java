package com;

import sun.misc.Launcher;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-24 13:29
 **/
public class AppTest {
    public static void main(String[] args) throws IOException {
        System.out.println(Launcher.class.getClassLoader());
        System.out.println(ClassLoader.class.getClassLoader());

        Launcher launcher = Launcher.getLauncher();
        System.out.println(launcher);
        ClassLoader appClassLoader = launcher.getClassLoader();
        System.out.println(appClassLoader);
        System.out.println(appClassLoader.getParent());

        System.out.println("--------------------------");
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemLoader);
        Enumeration<URL> em1 = systemLoader.getResources("");
        while (em1.hasMoreElements()) {
            System.out.println(em1.nextElement());
        }


        System.out.println("--------------------------");

        System.out.println(appClassLoader.getClass().getClassLoader());
        System.out.println(appClassLoader.getParent().getClass().getClassLoader());



    }
}
