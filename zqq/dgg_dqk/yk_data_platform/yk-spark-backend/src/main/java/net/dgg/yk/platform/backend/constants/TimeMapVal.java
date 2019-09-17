package net.dgg.yk.platform.backend.constants;

import java.text.SimpleDateFormat;
import java.time.Clock;

public class TimeMapVal {

    public static final long min;
    public static final long max;
    public static final long year2000;
    static {
        try {
            min = 0;
            max = Clock.systemUTC().millis();
            year2000 = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01").getTime();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
