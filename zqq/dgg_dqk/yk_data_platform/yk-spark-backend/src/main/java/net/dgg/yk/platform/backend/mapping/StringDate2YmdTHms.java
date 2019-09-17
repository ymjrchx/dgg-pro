package net.dgg.yk.platform.backend.mapping;

import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.constants.TimeMapVal;

import java.util.function.Function;

public class StringDate2YmdTHms implements Function<String, String> {

    @Override
    public String apply(String s) {
        Long unix = Toolkit.DateUtils.fromDate(s);
        if (unix == null) {
            unix = TimeMapVal.min;
        } else if (unix < TimeMapVal.min) {
            unix = TimeMapVal.min;
        } else if (unix > TimeMapVal.max) {
            unix = TimeMapVal.max;
        }
        return String.format("%sT%s", Toolkit.DateUtils.format(unix, "yyyy-MM-dd"), Toolkit.DateUtils.format(unix, "HH:mm:ss"));
    }
}
