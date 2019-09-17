package net.dgg.zqq.utils;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lee on 2018/10/9.
 */
public class DateUtils {

    @SneakyThrows
    public static String getCurrentByFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

}
