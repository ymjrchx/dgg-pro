package net.dgg.yk.platform.backend.mapping;

import net.dgg.yk.common.Toolkit;
import org.apache.commons.lang.StringUtils;

import java.util.function.Function;

public class LongNumberConverter implements Function<String, Long> {

    @Override
    public Long apply(String s) {
        String intStr = Toolkit.StringHelper.getMatchFirst(s, "\\d+");
        if (StringUtils.isEmpty(intStr)) {
            return 0L;
        } else {
            return Long.valueOf(intStr);
        }
    }
}
