package dgg.net.utils;


import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxin
 * @date 2019/4/1 11:02
 */
public class CanalTest {
    @Test
    public void canalTest() throws InterruptedException {

        LocalDate ld1 = LocalDate.parse("2019-03-01");
        LocalDate ld2 = LocalDate.parse("2019-03-31");
        LocalDate ld3 = LocalDate.parse("2019-04-01");
        LocalDate ld4 = LocalDate.parse("2019-04-30");

        Period p1 = Period.between(ld1, ld2);
        Period p2 = Period.between(ld3, ld4);
        System.out.println(p1.getDays());
        System.out.println(p2.getDays());


        List<String> list = new ArrayList<>();
        for (int i = 0; i <100000; i++) {
            int ii = i;
            Runnable runnable = () -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("String " + ii);
            };
            new Thread(runnable).start();
        }
        TimeUnit.SECONDS.sleep(1000);
        System.out.println(list.size());




    }



    public static void main(String args[]) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("runnable complete..." );
                }
            });
        }

        pool.shutdown();
        System.out.println("pool shutdown:" + pool.isShutdown());
        while (!pool.isTerminated()) {
            pool.awaitTermination(1, TimeUnit.SECONDS);
        }

        System.out.println("all task complete");
     //   pool.shutdown();
        System.out.println("this is the end");
    }


    @Test
    public void testUrl() throws UnsupportedEncodingException {
        String url  = URLDecoder.decode("http://zzx.dgg.cn/ty9/?baiduxxl-px","utf8");
        System.out.println(url);
        System.out.println("****************");
        url = URLDecoder.decode("http%3A%2F%2Fzzx.dgg.cn%2Fty9%2F%3Fbaiduxxl-px","utf8");

        System.out.println(url);
        System.out.println("********************");
        url = URLEncoder.encode("http://zzx.dgg.cn/ty9/?baiduxxl-px","utf8");
        System.out.println(url);
        System.out.println("http%3A%2F%2Fzzx.dgg.cn%2Fty9%2F%3Fbaiduxxl-px");
    }


}
