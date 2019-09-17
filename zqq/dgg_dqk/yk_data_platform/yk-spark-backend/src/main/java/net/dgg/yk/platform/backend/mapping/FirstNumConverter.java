package net.dgg.yk.platform.backend.mapping;

import net.dgg.yk.common.Toolkit;

import java.util.function.Function;

public class FirstNumConverter implements Function<String, String> {

    @Override
    public String apply(String s) {
        return Toolkit.StringHelper.getMatchFirst(s, "\\d+");
    }
}
