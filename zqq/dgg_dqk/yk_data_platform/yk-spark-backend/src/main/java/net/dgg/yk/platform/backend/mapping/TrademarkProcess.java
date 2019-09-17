package net.dgg.yk.platform.backend.mapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.flow.delegates.Support;
import net.dgg.yk.platform.backend.mapping.ints.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TrademarkProcess implements Process {

    @Override
    public Object process(JsonObject task, JsonObject context) {
        try {
            JsonElement productInfo = (JsonElement) Toolkit.JSON.getDeepValueOrNull(context, Support.splitByDot("docs.knowledgeProperty.trademarkInfo"), Object.class);
            if(productInfo!=null && productInfo.isJsonArray()) {
                Iterator<JsonElement> iterator = productInfo.getAsJsonArray().iterator();
                List<JsonObject> es = new ArrayList<>();
                while(iterator.hasNext()){
                    JsonElement jsonElement = iterator.next();
                    if(jsonElement.isJsonObject()){
                        es.add(jsonElement.getAsJsonObject());
                    }
                }
                Optional<String> ss = es.stream().map(jsonObject -> { JsonElement tmNameElement = jsonObject.get("tmName"); return tmNameElement.getAsString();}).reduce((a, b)->a+","+b);
                if(ss.isPresent()){
                    return ss.get();
                }else{
                    return null;
                }
            }else {
                return null;
            }
        }catch(Exception e){
            return null;
        }
    }
}
