package net.dgg.yk.platform.backend.mapping;

import java.util.function.Function;

public class EmailFunction implements Function<String, String> {

    @Override
    public String apply(String s) {
        return s == null || s.length() == 0 ? null : s;
    }
}
