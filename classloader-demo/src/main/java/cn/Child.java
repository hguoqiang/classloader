package cn;


/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-02 11:41
 **/
public class Child extends Parent

{

    public static int i =5;
    {
        System.out.println("Child 普通代码块");
    }
    static {
        System.out.println("Child 静态代码块");

    }

    public static void say() {
        System.out.println("Child 静态方法");

    }

    public Child() {
        System.out.println("Child 构造函数");
    }
}
