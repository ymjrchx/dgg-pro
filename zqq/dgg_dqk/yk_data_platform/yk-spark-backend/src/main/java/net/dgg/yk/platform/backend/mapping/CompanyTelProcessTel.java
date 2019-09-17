package net.dgg.yk.platform.backend.mapping;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.flow.delegates.Support;
import net.dgg.yk.platform.backend.mapping.ints.Process;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CompanyTelProcessTel implements Process {

    @Override
    public Object process(JsonObject task, JsonObject context) {
        String tels = Toolkit.JSON.getDeepValueOrNull(context, Support.splitByDot("tels"), String.class);
        if (StringUtils.isEmpty(tels)) {
            return null;
        } else {
            JsonArray arr = Toolkit.JSON.parseJsonArray(tels);
            StringBuilder stringBuilder = new StringBuilder();
            for (JsonElement element : Lists.newArrayList(arr.iterator())) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(element.getAsString());
            }
            return stringBuilder.toString();
        }
    }
}
