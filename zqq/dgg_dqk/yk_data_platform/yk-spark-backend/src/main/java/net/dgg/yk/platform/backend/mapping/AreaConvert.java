package net.dgg.yk.platform.backend.mapping;

import net.dgg.yk.platform.backend.flow.thirdPart.ProvinceService;

import java.util.function.Function;

public class AreaConvert implements Function<String, String> {

    private static ProvinceService provinceService = new ProvinceService();

    @Override
    public String apply(String s) {
        try {
            return provinceService.getAreaString(s);
        } catch (Exception e) {
            return null;
        }
    }
}
