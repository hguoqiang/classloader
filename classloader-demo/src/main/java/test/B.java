package test;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-05-08 18:11
 **/
public class B extends A{

    private String s;

    public B(String ss) {
        super(ss);
        this.s="2";
        System.out.println("B:"+s);
    }

    @Override
    public void test1(){
        System.out.println("B test1");
    }
}
