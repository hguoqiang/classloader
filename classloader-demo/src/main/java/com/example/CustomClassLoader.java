package com.example;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 自定义类加载器，打破双亲委派模式
 */
public class CustomClassLoader extends URLClassLoader {
    private URL[] urls;//扫描的jar包路径

    public CustomClassLoader(URL[] urls) {
        //指定搜索路径和父加载器(AppClassLoader)
        super(urls, CustomClassLoader.class.getClassLoader());
        this.urls = urls;
    }

    /**
     * 重写loadClass方法 ，打破双亲委派
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (urls != null && urls.length>0) {
            Class<?> c = null;
            try {
                //先在插件本身或自己的第三方库中找
                c = findClass(name);
                //该方法存在于URLClassLoader中，如果加载不到指定类，会报ClassNotFoundException
            }catch (ClassNotFoundException e){
                //e.printStackTrace();

                //找不到就调用父类
                c = super.loadClass(name);
            }
            return c;
        }
        return super.loadClass(name);
    }
}
