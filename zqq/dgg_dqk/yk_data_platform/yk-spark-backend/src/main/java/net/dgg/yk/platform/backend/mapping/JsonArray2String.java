package net.dgg.yk.platform.backend.mapping;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.dgg.yk.common.Toolkit;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonArray2String implements Function<JsonElement, String> {

    @Override
    public String apply(JsonElement jsonElement) {
        if (jsonElement == null) {
            return null;
        } else {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Optional<String> optional = Lists.newArrayList(jsonArray.iterator()).stream().map(jsonElement1 -> jsonElement1.toString()).reduce((a, b) -> a + b);
            if (optional.isPresent()) {
                return optional.get();
            } else {
                return null;
            }
        }
    }
}
