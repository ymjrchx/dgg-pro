package net.dgg.yk.platform.backend.mapping.ints;

import com.google.gson.JsonObject;

public interface Process {

    Object process(JsonObject task, JsonObject context);

}
