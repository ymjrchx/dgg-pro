package net.dgg.yk.platform.backend.mapping;

import net.dgg.yk.common.Toolkit;
import org.apache.commons.lang.StringUtils;

import java.util.function.Function;

public class NumConverter implements Function<String, Integer> {

    @Override
    public Integer apply(String s) {
        String intStr = Toolkit.StringHelper.getMatchFirst(s, "\\d+");
        if(StringUtils.isEmpty(intStr)){
            return 0;
        }else{
            return Integer.valueOf(intStr);
        }
    }
}
