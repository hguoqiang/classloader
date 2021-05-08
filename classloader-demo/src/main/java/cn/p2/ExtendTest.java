package cn.p2;

public class ExtendTest {
    public static void main(String[] args) {
        //首先会调用基类的构造方法
        //其次，调用成员的构造方法
        //最后，调用自己的构造方法
        new C();
    }
}

class A {
    static {
        System.out.println("A static代码块");
    }
    public A() {
        System.out.println("A()");
    }
}

class B extends A {
    static {
        System.out.println("B static代码块");
    }
    public B() {
        System.out.println("B()");
    }
}

class C extends B {

    private D d1 = new D("d1");
    private D d2 = new D("d2");
    static {
        System.out.println("C static代码块");
    }
    public C() {
        System.out.println("C()");
    }
}

class D {
    static {
        System.out.println("D static代码块");
    }
    public D(String str) {
        System.out.println("D " + str);
    }
}

