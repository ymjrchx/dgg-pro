package net.dgg.tmd.foundation.platform;

import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.framework.redis.RedisUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 李程 on 2018/11/8.
 */
@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class RedisURITest {

    @Test
    public void testRedis() {

        String url = RedisUtils.lpop("page_uri");
        while (url != null) {
            System.out.println(url);
            url = RedisUtils.lpop("page_uri");
        }

    }

}
