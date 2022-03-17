import java.util.List;

/**
 * @author zhuyc
 * @date 2022/03/06 20:21
 **/
public class GenericTest <T>{

    T t;

//    static T get() {
//
//    }


    public  <E extends  Number> E get(E e) {
        return e;
    }

    public  int get(List<? extends Number> e) {
        return 1;
    }
}
