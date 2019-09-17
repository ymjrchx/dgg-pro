package dgg.net.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author: lm
 * @Date: 2019/3/15 13:26
 * @Description:
 */
public class SortList<E> {
    public  void sort(List<E> list, Callable<E,? extends Comparable> callable, final String sort) {
        Collections.sort(list, (a, b) -> {
            int ret = 0;
            try {
               // Method m1 = ((E) a).getClass().getMethod(method, null);
              //  Method m2 = ((E) b).getClass().getMethod(method, null);
                if (sort != null && "desc".equals(sort)) {// 倒序
                   ret = callable.getValue(a).compareTo(callable.getValue(b));
                    // ret = m2.invoke(((E) b), null).toString().compareTo(m1.invoke(((E) a), null).toString());
                }
                else {
                    ret = callable.getValue(b).compareTo(callable.getValue(a));
                    // 正序
                   // ret = m1.invoke(((E) a), null).toString().compareTo(m2.invoke(((E) b), null).toString());
                }
            } catch (Exception e){

                System.out.println("error");
                System.out.println(a);
                System.out.println(b);
            }
            return ret;
        });
    }


    public  interface Callable<T, V extends Comparable>{
        V getValue(T t);
    }

    public static void main(String[] args) {
        List<ProductionRankVo> list = new LinkedList<>();
        Random random = new Random();
        ProductionRankVo p1 = new ProductionRankVo();
        p1.setRateDouble(random.nextDouble()*100);
        list.add(p1);

        ProductionRankVo p2 = new ProductionRankVo();
        p2.setRateDouble(random.nextDouble()*100);
        list.add(p2);

        ProductionRankVo p3 = new ProductionRankVo();
        p3.setRateDouble(random.nextDouble()*100);
        list.add(p3);

        ProductionRankVo p4 = new ProductionRankVo();
        p4.setRateDouble(random.nextDouble()*100);
        list.add(p4);

        ProductionRankVo p5 = new ProductionRankVo();
        p5.setRateDouble(random.nextDouble()*100);
        list.add(p5);
        list.add(p1);
        list.add(new ProductionRankVo());
        list.stream().forEach(System.out::println);
        SortList<ProductionRankVo> sortList = new SortList();

        sortList.sort(list,ProductionRankVo::getRateDouble,"desc");
        System.out.println("eeeeeeeeeeeeeeeeeeee");
        list.stream().forEach(System.out::println);





    }
}
