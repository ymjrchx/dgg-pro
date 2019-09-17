package net.dgg.yk.platform.backend.mapping;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

public class BusinessState implements Function<String, String> {

    @Override
    public String apply(String s) {
        if (StringUtils.isEmpty(s)) {
            return "其它";
        } else {
            if (s.contains("存续")) {
                return "存续";
            } else if (s.contains("在业")) {
                return "在业";
            } else if (s.contains("吊销")) {
                return "吊销";
            } else if (s.contains("在营")) {
                return "在营";
            } else if (s.contains("注销")) {
                return "注销";
            } else if (s.contains("正常")) {
                return "正常";
            } else if (s.contains("废止")) {
                return "废止";
            } else if (s.contains("核准设立")) {
                return "核准设立";
            } else if (s.contains("解散")) {
                return "解散";
            } else if (s.contains("迁出")) {
                return "迁出";
            } else if (s.contains("撤销")) {
                return "撤销";
            } else if (s.contains("仍注册")) {
                return "仍注册";
            } else if (s.contains("已告解散")) {
                return "已告解散";
            } else if (s.contains("暂无")) {
                return "暂无";
            } else {
                return "其它";
            }
        }
    }

}
