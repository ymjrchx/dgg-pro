package net.dgg.yk.platform.backend.mapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.flow.delegates.Support;
import net.dgg.yk.platform.backend.mapping.ints.Process;

import java.util.*;
import java.util.stream.Collectors;

public class ProductProcess implements Process {

    @Override
    public Object process(JsonObject task, JsonObject context) {
        try {
            JsonElement productInfo = (JsonElement) Toolkit.JSON.getDeepValueOrNull(context, Support.splitByDot("docs.businessStatus.productInfo"), Object.class);
            if (productInfo != null && productInfo.isJsonArray()) {
                Iterator<JsonElement> iterator = productInfo.getAsJsonArray().iterator();
                List<JsonObject> es = new ArrayList<>();
                while (iterator.hasNext()) {
                    JsonElement jsonElement = iterator.next();
                    if (jsonElement.isJsonObject()) {
                        es.add(jsonElement.getAsJsonObject());
                    }
                }
                Optional<String> ss = es.stream().map(jsonObject -> {
                    JsonElement pAllNameElement = jsonObject.get("pAllName");
                    return pAllNameElement.getAsString();
                }).reduce((a, b) -> a + "," + b);
                if (ss.isPresent()) {
                    return ss.get();
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}
