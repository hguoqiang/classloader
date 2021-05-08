package cn.p1;

public class VarTest {
    public static void main(String[] args) {
        // 所有的变量初始化完，才会执行构造方法,在类的加载过程中，只有内部的变量创建完，才会去执行这个类的构造方法。
        A2 a2 = new A2();
    }
}

class A2 {
    B2 b2 = new B2();

    static {
        System.out.println("A2 static");
    }

    public A2() {
        System.out.println("A2()");
    }
}

class B2 {
    C2 c2 = new C2();
    D2 d2 = new D2();

    static {
        System.out.println("B2 static");
    }

    public B2() {
        System.out.println("B2()");
    }
}

class C2 {
    static {
        System.out.println("C2 static");
    }

    public C2() {
        System.out.println("C2()");
    }
}

class D2 {
    static {
        System.out.println("D2 static");
    }

    public D2() {
        System.out.println("D2()");
    }
}
