package com.example;

import sun.misc.VM;

/**
 * 双亲委派模式
 * 即：如果一个类加载器要加载某个类，它并不会自己先去加载，而是让父加载器去加载，如果父加载器还有父亲，则进一步向上委托，依次递归，
 * 最终将到达顶层的启动类加载器(Bootstrap ClassLoader)。
 *
 * 如果父加载器可以完成加载任务，就成功返回。
 * 如果父加载器无法完成此加载任务，子加载器才会自己去加载。
 */
public class DoubleParentDelegation {

    public static void main(String[] args) {
        DoubleParentDelegation doubleParentDelegation = new DoubleParentDelegation();
        ClassLoader classLoader = doubleParentDelegation.getClass().getClassLoader();

        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());

    }
}
