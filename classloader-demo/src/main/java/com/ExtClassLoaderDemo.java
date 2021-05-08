package com;

import sun.net.spi.nameservice.dns.DNSNameService;

/**
 * 2.  ExtClassLoader           扩展类类加载器
 * 扩展类加载器，加载 -Djava.ext.dirs 选项指定的目录，比如：%JRE_HOME%\jre\lib\ext目录下的jar包和class文件。
 *
 * 注意：如果配置-Djava.ext.dirs会覆盖Java默认的ext设置。
 * -Djava.ext.dirs=C:\Users\hguoq\Desktop\classloader
 */
public class ExtClassLoaderDemo {
    public static void main(String[] args) {
        System.out.println(DNSNameService.class.getClassLoader());
        //System.out.println(DNSNameService.class.getClassLoader().getParent());
        System.out.println("-----------------------");

        String paths = System.getProperty("java.ext.dirs");
        String[] arr = paths.split(";");
        for (String path : arr) {
            System.out.println(path);
        }
    }
}
