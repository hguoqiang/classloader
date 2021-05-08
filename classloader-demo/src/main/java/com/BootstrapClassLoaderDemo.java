package com;

/**
 * 1. Bootstrap ClassLoader    启动类加载器
 * 主要用于加载JDK核心类库，C++语言实现的，加载时的搜索路径是由 sun.boot.class.path 所指定的，
 * 比如：%JRE_HOME%\jre\lib\下的rt.jar、resources.jar、charsets.jar等。
 *
 * 通过 -Xbootclasspath 指定配置其他目录
 * -Xbootclasspath:路径      指定的路径会完全取代jdk核心的搜索路径    坚决不要用
 * -Xbootclasspath/a:路径    指定的路径会在jdk核心类 后搜索           可用
 * -Xbootclasspath/p:路径    指定的路径会在jdk核心类 前搜索           可用，不建议使用
 * -Xbootclasspath/p: C:\Users\hguoq\Desktop\classloader\commons-io-2.8.0.jar
 */
public class BootstrapClassLoaderDemo {

    public static void main(String[] args) {

        String paths = System.getProperty("sun.boot.class.path");

        String[] arr = paths.split(";");//如果有多个路径，unix下用“:”分割，windows下用“;”分割

        for (String path : arr) {
            System.out.println(path);
        }

        System.out.println("-----------------------");

        /*URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }*/

        System.out.println("-----------------------");

//        System.out.println(String.class.getClassLoader());//输出null，表示Bootstrap ClassLoader
//        System.out.println(ByteOrderMark.class.getClassLoader());


    }
}
