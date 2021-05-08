package test;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-08 18:11
 **/
public class A {
    private String s;

    public A(String s1) {
        this.s=s1;
        this.test1();
        System.out.println("A:"+this);
        System.out.println("A:"+s);
    }

    public void test1(){
        System.out.println("A test1");
    }
}
