package net.dgg;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by Administrator on 2018/11/15.
 */
@Slf4j
public class ThreadTst {

    @Test
    @SneakyThrows
    public void testThreadInt() {

        Thread t = new Thread(() -> {
            while (true)
                try {
                    for (long i = 0; i < 10000000L; i++) {
                        Thread.yield();
                    }
                    Thread.sleep(10000000L);
                    log.info("ok");
                } catch (Exception e) {
                    log.info("interrupted!");
                }
        });
        t.start();

        t.interrupt();
        t.interrupt();
        log.info("已经发送中断");
        Thread.sleep(10000000000L);
    }

}
