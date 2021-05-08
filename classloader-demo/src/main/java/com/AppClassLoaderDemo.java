package com;

/**
 * 3. AppClassLoader          系统类加载器，又叫：应用类加载器
 * 它的搜索路径由java.class.path(CLASSPATH)来指定，默认是当前文件夹(java命令在哪个目录执行，那么这个目录就是classpath的值)
 *
 * 主要用来加载程序中的类文件
 * AppClassLoader父加载器是ExtClassLoader
 */
public class AppClassLoaderDemo {
    public static void main(String[] args) {

        String paths = System.getProperty("java.class.path");
        String[] arr = paths.split(";");
        for (String path : arr) {
            System.out.println(path);
        }

        System.out.println("--------------------------");

        System.out.println(AppClassLoaderDemo.class.getClassLoader());//
//        System.out.println(AppClassLoaderDemo.class.getClassLoader().getParent());
//        System.out.println(AppClassLoaderDemo.class.getClassLoader().getParent().getParent());



    }
}
