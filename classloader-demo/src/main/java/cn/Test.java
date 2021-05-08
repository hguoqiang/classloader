package cn;

import java.lang.reflect.Method;
import java.util.UUID;

public class Test {

    /**
     * 对一个有静态方法和静态代码块的类只进行装载，不进行初始化
     *
     * 通过反射获取其静态方法，并invoke这个静态方法
     *
     * 因为静态代码块在初始化的时候执行，所以如果invoke这个静态方法的时候，静态代码块执行了，说明了Java调用静态方法时已经完成了类的初始化
     * @param args
     */
   /* public static void main(String[] args) {
        Test test = new Test();

        try {
            Class clazz = Class.forName("cn.Child", false, test.getClass().getClassLoader());

            Method method = clazz.getMethod("say");

            method.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }*/
    public static void main(String[] args) throws Exception{
        Child child = new Child();


        //int i = Child.i;

        // Child.i =10;

        //Child.say();


//        Class.forName("cn.Child");
//        Class.forName("cn.Child",false,Test.class.getClassLoader());//无输出

    }

}
