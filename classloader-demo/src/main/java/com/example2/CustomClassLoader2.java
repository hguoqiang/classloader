package com.example2;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @description:  重写 findClass(), 这种方式不打破双亲委派模式
 * @author: huangguoqiang
 * @create: 2021-04-24 19:34
 **/
public class CustomClassLoader2 extends ClassLoader{
      private String rootPath;
 
 
    public CustomClassLoader2(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //check if the class have been loaded
        Class<?> c = findLoadedClass(name);
        if (c != null) {
            return c;
        }
        //load the class
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            c = defineClass(name, classData, 0, classData.length);
            return c;
        }
    }
 
    private byte[] loadClassData(String className) {
        String path = rootPath + "/" + className.replace('.', '/') + ".class";
 
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream(path);
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int temp = 0;
            while ((temp = is.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
        return null;
    }
}
