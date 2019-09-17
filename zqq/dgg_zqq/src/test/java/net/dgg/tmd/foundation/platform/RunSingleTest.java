package net.dgg.tmd.foundation.platform;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.utils.FreemarkerUtils;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 李程 on 2018/11/12.
 */
@Slf4j
public class RunSingleTest {

    @Test
    @SneakyThrows
    public void testFreemarker() {

        Map<String, Object> map = new TreeMap<>();
        map.put("a", "ab");
        String result = FreemarkerUtils.renderBy("sss", "${a}", map);
        log.info(result);

    }

}
