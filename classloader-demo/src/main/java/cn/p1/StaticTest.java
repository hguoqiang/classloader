package cn.p1;

public class StaticTest {
    public static void main(String[] args) {
        //静态成员与普通成员类的加载区别，在类的加载过程中，静态成员类的对象，会优先加载；而普通成员类的对象则是使用的时候才回去加载。
        A3 a3 = new A3();
    }
}

class A3 {
    B3 b3 = new B3();
    static C3 c4 = new C3();

    static {
        System.out.println("A3 static");
    }
}

class B3 {
    static {
        System.out.println("B3 static");
    }
}

class C3 {
    static {
        System.out.println("C3 static");
    }
}
