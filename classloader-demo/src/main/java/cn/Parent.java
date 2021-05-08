package cn;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-02 11:41
 **/
public class Parent {
    {
        System.out.println("Parent 普通代码块");
    }
    static {
        System.out.println("Parent  静态代码块");

    }


    public static void par() {
        System.out.println("Parent  静态方法");

    }

    public Parent() {
        System.out.println("Parent 构造函数");
    }
}
