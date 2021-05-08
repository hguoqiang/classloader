package java.lang;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-04-02 17:40
 **/
public class Test {
    public static void main(String[] args) {
        int compare = Integer.compare(1, 2);
        System.out.println(compare);
        System.out.println(Integer.class.getClassLoader());
    }
}
