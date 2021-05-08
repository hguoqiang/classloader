package com.example;


import com.test.MyInterFace;
import com.test.ParseExcel;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * URLClassLoader
 * 是ClassLoader的子类，也是ExtClassLoader和 AppClassLoader 的父类，
 * 它可以从指定的 jar 文件和目录中加载类和资源。也就是说，可以动态加载jar包中的类。
 * 注意：父类，跟 父类加载器 是两个概念
 * <p>
 * 构造方法：
 * URLClassLoader(URL[] urls, ClassLoader parent)：使用指定的父加载器创建对象，从指定的urls路径来查询、并加载类。
 * <p>
 * URLClassLoader(URL[] urls)：使用默认的父亲类加载器(AppClassLoader)创建一个ClassLoader对象，从指定的urls路径来查询、并加载类。
 * <p>
 *
 * 需求：解析Excel，一般我们用poi，目前模板不确定，而且客户不希望每次改一种模板就重启应用。
 * 解决：把解析代码抽出来，打成一个jar包，每次上传模板的时候，就选用指定的jar包运行，因为需要动态加载jar包，所以需要用到类加载器。
 * 最终利用反射执行对应的方法，获取结果。
 *
 */
public class URLClassLoaderDemo {

    @Test
    public void test1() throws Exception {
        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-test-1.0-SNAPSHOT.jar");
        URL[] urls = new URL[]{file.toURI().toURL()};
        //这时候 hahaClassLoader 的 parent 属性 引用指向 appClassLoader
        URLClassLoader hahaClassLoader = new URLClassLoader(urls);
        System.out.println("hahaClassLoader.getParent(): "+hahaClassLoader.getParent());

        Class<?> aClass = hahaClassLoader.loadClass("com.test.ParseExcel");
        Object obj = aClass.newInstance();
        Method parse = aClass.getMethod("parse");
        parse.invoke(obj, null);


       /* System.out.println("--------------------------");
        ParseExcel parseExcel =(ParseExcel)obj;
        parseExcel.parse();*/
    }

    /**
     * test模块引用了jackson包，版本是2.11,
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-test-1.0-SNAPSHOT.jar");
        File file2 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\jackson-core-2.11.0.jar");

        URL[] urls = new URL[]{file.toURI().toURL(), file2.toURI().toURL()};
        //这时候 hahaClassLoader 的 parent 属性 引用指向 appClassLoader
        URLClassLoader hahaClassLoader = new URLClassLoader(urls);
        System.out.println("hahaClassLoader.getParent(): "+hahaClassLoader.getParent());

        Class<?> aClass = hahaClassLoader.loadClass("com.test.ParseExcel");
        Object obj = aClass.newInstance();
        Method parse = aClass.getMethod("parse");
        parse.invoke(obj, null);


        /**
         * hahaClassLoader 在加载 ParseExcel, 发现ParseExcel引用到JsonFactory,hahaClassLoader也会去加载JsonFactory，
         *
         * 遵循双亲委托模式，haha——>AppClassLoader ——>ExtClassLoader ——>BootStrapClassLoader,这三个都加载不成功，
         * 只能 hahaClassLoader 自己加载，能加载成功
         */
    }


    /**
     * test模块引用了jackson包，版本是2.11,
     *
     * 同时demo模块也引用了jackson包，但是版本是2.5.4,
     * 2.5.4版本和2.11有差距,2.5.4 版本 JsonFactory 没有 getFormatGeneratorFeatures()
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {

        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-test-1.0-SNAPSHOT.jar");
        File file2 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\jackson-core-2.11.0.jar");

        URL[] urls = new URL[]{file.toURI().toURL(), file2.toURI().toURL()};
        //这时候 hahaClassLoader 的 parent 属性 引用指向 appClassLoader
        URLClassLoader hahaClassLoader = new URLClassLoader(urls);
        System.out.println("hahaClassLoader.getParent(): "+hahaClassLoader.getParent());


        Class<?> aClass = hahaClassLoader.loadClass("com.test.ParseExcel");
        Object obj = aClass.newInstance();
        Method parse = aClass.getMethod("parse");
        parse.invoke(obj, null);


        /**
         * hahaClassLoader 在加载 ParseExcel, 发现ParseExcel引用到JsonFactory,hahaClassLoader 也会去加载JsonFactory，
         * 遵循双亲委托模式，hahaClassLoader ——> AppClassLoader ——> ExtClassLoader ——> BootStrapClassLoader,
         * BootStrapClassLoader先尝试加载，不成功，交给ExtClassLoader，ExtClassLoader尝试加载，不成功，
         * 交给 AppClassLoader,AppClassLoader 搜索当前classpath ,查找到D:\dev\maven\Repository\com\fasterxml\jackson\core\jackson-core\2.5.4\jackson-core-2.5.4.jar
         * 加载成功，hahaClassLoader就不会再次加载，
         * 而这个版本的 JsonFactory 是没有 getFormatGeneratorFeatures 方法的，这就导致运行报错
         *
         *
         * 要解决这个问题，就需要用到第二个构造方法：URLClassLoader(URL[] urls, ClassLoader parent)
         */

    }


    @Test
    public void test4() throws Exception {

        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-test-1.0-SNAPSHOT.jar");
        File file2 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\jackson-core-2.11.0.jar");

        URL[] urls = new URL[]{file.toURI().toURL(), file2.toURI().toURL()};

        ClassLoader extClassLoader = URLClassLoaderDemo.class.getClassLoader().getParent();
        //这时候 hahaClassLoader 的 parent 属性 引用指向 extClassLoader
        URLClassLoader hahaClassLoader = new URLClassLoader(urls, extClassLoader);
        System.out.println("hahaClassLoader.getParent(): "+hahaClassLoader.getParent());

        Class<?> aClass = hahaClassLoader.loadClass("com.test.ParseExcel");
        Object obj = aClass.newInstance();
        Method parse = aClass.getMethod("parse");
        parse.invoke(obj, null);

        /**
         * hahaClassLoader 在加载 ParseExcel, 发现ParseExcel引用到JsonFactory,hahaClassLoader 也会去加载JsonFactory，
         * 遵循双亲委托模式，haha——>ExtClassLoader ——>BootStrapClassLoader,
         * BootStrapClassLoader先尝试加载，不成功，交给ExtClassLoader，ExtClassLoader尝试加载，不成功，
         * 交给 hahaClassLoader,加载C:\Users\hguoq\Desktop\classloader\jackson-core-2.11.0.jar，成功，
         * 这时候，程序正常运行
         *
         * 但是这种方式在极端情况下还有问题，比如：判断ParseExcel是否实现了指定接口，只有实现了指定接口的类才能运行
         *
         */

    }


    /**
     * test模块加入 classloader-api 依赖
     * ParseExcel  implements MyInterFace
     *
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {


        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-test-1.0-SNAPSHOT.jar");
        File file2 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\jackson-core-2.11.0.jar");
        File file3 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-api-1.0-SNAPSHOT.jar");

        URL[] urls = new URL[]{file.toURI().toURL(), file2.toURI().toURL(), file3.toURI().toURL()};

        ClassLoader extClassLoader = URLClassLoaderDemo.class.getClassLoader().getParent();
        //这时候 hahaClassLoader 的 parent 属性 引用指向 extClassLoader
        URLClassLoader hahaClassLoader = new URLClassLoader(urls, extClassLoader);
        System.out.println("hahaClassLoader.getParent(): "+hahaClassLoader.getParent());

        Class<?> aClass = hahaClassLoader.loadClass("com.test.ParseExcel");


        //判断是否实现myInterface
        Class<MyInterFace> myInterFaceClass = MyInterFace.class;
        System.out.println("URLClassLoaderDemo myInterFaceClass: "+myInterFaceClass.hashCode());

        /*if (!myInterFaceClass.isAssignableFrom(aClass)) {
             System.out.println("该类没有实现 MyInterface 接口，不运行");
            return;
        }*/

        Object obj = aClass.newInstance();
        Method parse = aClass.getMethod("parse");
        parse.invoke(obj, null);

        //Class@345
        //interface com.test.MyInterFace

        /**
         *  hahaClassLoader 在加载 ParseExcel 时，会加载 MyInterFace，同时遵循双亲委托模式
         *  而 hahaClassLoader 的父亲是 ExtClassLoader ，所以 ExtClassLoader 会尝试加载 MyInterFace 这个类，
         *  不会加载成功，转而由 hahaClassLoader 自己去加载 MyInterFace
         *  hahaClassLoader 是去自己的扫描路径中搜索，加载成功，生成 MyInterFace 的 Class对象
         *
         *  我们回到这句代码：MyInterFace.class.isAssignableFrom(aClass)
         *  这句代码中的MyInterFace接口是被 appClassLoader 加载的，也生成了 MyInterFace 的 Class对象
         *
         *  这时候 appClassLoader 和 hahaClassLoader 都分别加载了MyInterFace接口，
         *  也就是说，内存中有两个MyInterFace的 class 对象
         *  它们俩互不干扰
         *
         *  所以 MyInterFace.class.isAssignableFrom(aClass) 返回 false
         *
         *  要解决这个问题，只能用自定义类加载器，
         *
         */

    }


    @Test
    public void test6() throws Exception {

        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-test-1.0-SNAPSHOT.jar");
        File file2 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\jackson-core-2.11.0.jar");
        //File file3 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-api-1.0-SNAPSHOT.jar");

        URL[] urls = new URL[]{file.toURI().toURL(), file2.toURI().toURL()};

        //这时候 hahaClassLoader 的 parent 属性 引用指向 appClassLoader
        CustomClassLoader hahaClassLoader = new CustomClassLoader(urls);
        System.out.println("hahaClassLoader.getParent(): "+hahaClassLoader.getParent());

        Class<?> aClass = hahaClassLoader.loadClass("com.test.ParseExcel");

        //自定义类加载器，打破双亲委派模式
        //判断是否实现myInterface
        Class<MyInterFace> myInterFaceClass = MyInterFace.class;
        System.out.println("URLClassLoaderDemo myInterFaceClass: "+myInterFaceClass.hashCode());

        if (!myInterFaceClass.isAssignableFrom(aClass)) {
            System.out.println("该类没有实现 MyInterface 接口，不运行");
            return;
        }

        Object obj = aClass.newInstance();
        Method parse = aClass.getMethod("parse");
        parse.invoke(obj, null);

        /**
         *  customClassLoader 父亲是 AppClassLoader, 不遵循 双亲委派
         *  customClassLoader先在自己的搜索路径中查找 MyInterFace，找不到（因为已经去掉了）
         *  所以让他的父亲 AppClassLoader 去加载，而AppClassLoader是可以加载成功的，
         *  生成了MyInterFace 的 Class 对象
         *
         *  同时在执行这句代码时：MyInterFace.class.isAssignableFrom(aClass)
         *  AppClassLoader还要去加载MyInterFace接口，但是因为已经加载成功，所以不需要重复加载
         *
         *  所以 MyInterFace.class.isAssignableFrom(aClass) 返回 true
         *
         */

    }



    @Test
    public void test7() throws Exception {

        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\classloader-test-1.0-SNAPSHOT.jar");

        URL[] urls = new URL[]{file.toURI().toURL()};


        CustomClassLoader hahaClassLoader = new CustomClassLoader(urls);
        Class<?> aClass = hahaClassLoader.loadClass("java.lang.Integer");

        //自定义类加载器，打破双亲委派模式
        Object obj = aClass.newInstance();
        Method parse = aClass.getMethod("compare");
        parse.invoke(obj, 1,2);

    }


    /*@Test
    public void test8() throws Exception {

        File file = new File("C:\\Users\\hguoq\\Desktop\\classloader\\parse1.jar");
        File file2 = new File("C:\\Users\\hguoq\\Desktop\\classloader\\parse2.jar");

        String name = "com.test.ParseExcel";

        URL[] urls = new URL[]{file.toURI().toURL()};

        CustomClassLoader customClassLoader1 = new CustomClassLoader(urls);
        Class<?> aClass1 = customClassLoader1.loadClass(name);

        //自定义类加载器，打破双亲委派模式
        Object obj1 = aClass1.newInstance();
        Method m1 = aClass1.getMethod("parse");
        m1.invoke(obj1, null);

        URL[] urls2 = new URL[]{file2.toURI().toURL()};

        CustomClassLoader customClassLoader2 = new CustomClassLoader(urls2);
        Class<?> aClass2 = customClassLoader2.loadClass(name);

        //自定义类加载器，打破双亲委派模式
        Object obj2 = aClass2.newInstance();
        Method m2 = aClass2.getMethod("parse");
        m2.invoke(obj2, null);

        System.out.println("--------------------------");

        ParseExcel parseExcel = (ParseExcel) obj2;
        parseExcel.parse();


    }*/

}
