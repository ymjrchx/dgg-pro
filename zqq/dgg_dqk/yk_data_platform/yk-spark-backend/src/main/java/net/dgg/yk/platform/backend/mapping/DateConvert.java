package net.dgg.yk.platform.backend.mapping;

import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.constants.TimeMapVal;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.function.Function;

public class DateConvert implements Function<String, Date> {

    @Override
    public Date apply(String s) {
        Long time = Toolkit.DateUtils.fromDate(s);
        if (time == null) {
            Date usn = new Date();
            usn.setTime(TimeMapVal.min);
            return usn;
        } else {
            if (time < TimeMapVal.min) {
                time = TimeMapVal.min;
            } else if (time > TimeMapVal.max) {
                time = TimeMapVal.year2000;
            }
            Date date = new Date();
            date.setTime(time);
            return date;
        }
    }
}
