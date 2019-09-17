package net.dgg.yk.platform.backend.mapping;

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

public class CompanyTelProcess implements Process {

    @Override
    public Object process(JsonObject task, JsonObject context) {
        String _id = context.get("_id").getAsString();
        JsonElement reportInfo = (JsonElement) Toolkit.JSON.getDeepValueOrNull(context, Support.splitByDot("docs.background.reportInfo"), Object.class);
        JsonElement telElement = (JsonElement) Toolkit.JSON.getDeepValueOrNull(context, Support.splitByDot("docs.background.baseInfo.companyTel"), Object.class);
        LinkedList<String> telArr = new LinkedList<>();
        if (telElement != null && !telElement.isJsonNull()) {
            if (telElement.isJsonPrimitive()) {
                String telString = telElement.getAsString();
                if (StringUtils.isNotEmpty(telString)) {
                    for (String t : telString.split("[\\,\\;]+")) {
                        telArr.addLast(t);
                    }
                }
            } else if (telElement.isJsonArray()) {
                Iterator<JsonElement> iterator = telElement.getAsJsonArray().iterator();
                while (iterator.hasNext()) {
                    JsonElement ele = iterator.next();
                    if (ele != null && ele.isJsonPrimitive()) {
                        telArr.addLast(ele.getAsString());
                    }
                }
            }
        }
        try {
            JsonElement ro = context.get("refs");
            if (ro != null && ro.isJsonObject()) {
                JsonElement tex = (JsonElement) Toolkit.JSON.getDeepValueOrNull(ro.getAsJsonObject(), Support.splitByDot("docs.background.baseInfo.companyTel"), Object.class);
                if (tex != null && !tex.isJsonNull()) {
                    if (tex.isJsonPrimitive()) {
                        String telString = tex.getAsString();
                        if (StringUtils.isNotEmpty(telString)) {
                            for (String t : telString.split("[\\,\\;]+")) {
                                telArr.addLast(t);
                            }
                        }
                    } else if (tex.isJsonArray()) {
                        Iterator<JsonElement> iterator = tex.getAsJsonArray().iterator();
                        while (iterator.hasNext()) {
                            JsonElement ele = iterator.next();
                            if (ele != null && ele.isJsonPrimitive()) {
                                telArr.addLast(ele.getAsString());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LinkedList<String> rTels = new LinkedList<>();
        if (reportInfo != null && reportInfo.isJsonObject()) {
            Iterator<Map.Entry<String, JsonElement>> entries = reportInfo.getAsJsonObject().entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, JsonElement> entry = entries.next();
                String key = entry.getKey();
                JsonElement val = entry.getValue();
                if (val != null && val.isJsonObject()) {
                    String rTel = Toolkit.JSON.getDeepValueOrNull(val.getAsJsonObject(), Support.splitByDot("rBaseInfo.rTel"), String.class);
                    if (StringUtils.isNotEmpty(rTel)) {
                        rTels.addLast(rTel);
                    }
                }
            }
        }
        rTels.addAll(telArr);
        List<String> list = rTels.stream().distinct().collect(Collectors.toList());
        LinkedList<String> tels = new LinkedList<>();
        tels.addAll(list);
        if (telArr.isEmpty()) {
        } else {
            String first = telArr.getFirst();
            tels.remove(first);
            tels.addFirst(first);
        }
        List<String> finalTels = tels.stream().map(tel -> {
            if (tel != null) {
                String processChainObject = tel.replaceAll("[^\\d]+", "");
                if (processChainObject.length() > 11) {
                    processChainObject = processChainObject.substring(processChainObject.length() - 11);
                }
                return processChainObject;
            } else {
                return tel;
            }
        }).distinct().collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < finalTels.size(); i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(finalTels.get(i));
        }
        return stringBuilder.length() == 0 ? null : stringBuilder.toString();
    }
}
