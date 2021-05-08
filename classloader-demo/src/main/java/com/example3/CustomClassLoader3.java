package com.example3;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-25 17:18
 **/
public class CustomClassLoader3 extends ClassLoader {
    private String rootPath;


    public CustomClassLoader3(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();

                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();

                if (name.startsWith("com.example3")) {
                    c = findClass(name);
                } else {
                    c = this.getParent().loadClass(name);
                }

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();

            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
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
