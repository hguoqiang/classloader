package cn.p1;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-02 12:25
 **/
public class ExtendTest {
    public static void main(String[] args) {
        //C继承B，B继承A，C依赖D，当创建C时候，加载继承的B和依赖的D，B是继承A，所有加载A。
        //只有A加载完成，才加载B。BD加载完成，才加载C。
        //静态成员与普通成员类的加载区别，在类的加载过程中，静态成员类的对象，会优先加载；而普通成员类的对象则是使用的时候才回去加载。

        new C();
    }
}

class A {
    static {
        System.out.println("A static代码块");
    }
    public A() {
        System.out.println("A 构造函数");
    }
}

class B extends A {
    static {
        System.out.println("B static代码块");
    }
    public B() {
        System.out.println("B 构造函数 ");
    }
}

class C extends B {
    private static D d = new D();

    private E e = new E();

    static {
        System.out.println("C static代码块");
    }

    public C() {
        System.out.println("C 构造函数 ");
    }
}

class D {
    public D() {
        System.out.println("D 构造函数");
    }

    static {
        System.out.println("D static代码块");
    }
}

class E {
    public E() {
        System.out.println("E 构造函数");
    }

    static {
        System.out.println("E static代码块");
    }
}