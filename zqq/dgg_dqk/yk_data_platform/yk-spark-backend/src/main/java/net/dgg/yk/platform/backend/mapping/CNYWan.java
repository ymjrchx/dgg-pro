package net.dgg.yk.platform.backend.mapping;

import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.constants.ExchangeRateVal;
import org.apache.commons.lang.StringUtils;

import java.util.function.Function;

public class CNYWan implements Function<String, Double> {

    @Override
    public Double apply(String s) {
        if (StringUtils.isEmpty(s)) {
            return 0D;
        } else {
            String found = Toolkit.StringHelper.getMatchFirst(s, "\\d+(\\.\\d+)?");
            if (StringUtils.isNotEmpty(found)) {
                double st = Float.valueOf(found);
                st = st * 1e-4D;
                if (s.matches(".*亿.*")) {
                    st = st * 1e8D;
                }
                if (s.matches(".*百.*")) {
                    st = st * 1e2D;
                }
                if (s.matches(".*千.*")) {
                    st = st * 1e3D;
                }
                if (s.matches(".*万.*")) {
                    st = st * 1e4D;
                }
                if (s.matches(".*美(元|金)?.*")) {
                    st = st * ExchangeRateVal.USD2CNY;
                }
                if (s.matches(".*欧元.*")) {
                    st = st * ExchangeRateVal.EUR2CNY;
                }
                if (s.matches(".*英镑.*")) {
                    st = st * ExchangeRateVal.GBP2CNY;
                }
                if (s.matches(".*香港.*") || s.matches(".*港(元|币)?.*")) {
                    st = st * ExchangeRateVal.HKD2CNY;
                }
                return Math.round(st * 100D) / 100D;
            }
        }
        return 0D;
    }
}
