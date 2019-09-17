package net.dgg.yk.platform.backend.flow.delegates;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.mapping.ints.Process;

import java.util.*;
import java.util.function.Function;

public class Support {

    public static Map<String, Object> copy(JsonObject context, JsonArray currentTaskSet) {
        Map<String, Object> returned = new LinkedHashMap<>();
        for (int i = 0; i < currentTaskSet.size(); i++) {
            JsonObject task = currentTaskSet.get(i).getAsJsonObject();
            String taskType = task.get("task").getAsString();
            String from;
            String to;
            String fromStrVal;
            JsonObject fromJsonVal;
            JsonArray subTasks;
            JsonArray fromJsonArray;
            Object toVal = null;
            String apply;
            switch (taskType) {
                case "copy":
                    from = task.get("from").getAsString();
                    to = task.get("to").getAsString();
                    fromStrVal = Toolkit.JSON.getDeepValueOrNull(context, splitByDot(from), String.class);
                    toVal = fromStrVal;
                    apply = Toolkit.JSON.getDeepValueOrNull(task, new String[]{"apply"}, String.class);
                    String defaultValue = Toolkit.JSON.getDeepValueOrNull(task, new String[]{"defaultValue"}, String.class);
                    if (apply != null && apply.length() > 0) {
                        try {
                            Class function = Class.forName(apply);
                            Function fun = (Function) function.newInstance();
                            toVal = fun.apply(fromStrVal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Toolkit.MapHelper.putDeepValue(returned, splitByDot(to), toVal != null ? toVal : defaultValue);
                    break;
                case "map2list":
                    from = task.get("from").getAsString();
                    to = task.get("to").getAsString();
                    fromJsonVal = Toolkit.JSON.getDeepValueOrNull(context, splitByDot(from), JsonObject.class);
                    if (fromJsonVal != null) {
                        subTasks = task.get("tasks").getAsJsonArray();
                        List<Map<String, Object>> list = map2list(fromJsonVal, subTasks);
                        Toolkit.MapHelper.putDeepValue(returned, splitByDot(to), list);
                    }
                    break;
                case "list2list":
                    from = task.get("from").getAsString();
                    to = task.get("to").getAsString();
                    fromJsonArray = Toolkit.JSON.getDeepValueOrNull(context, splitByDot(from), JsonArray.class);
                    if (fromJsonArray != null) {
                        subTasks = task.get("tasks").getAsJsonArray();
                        List<Map<String, Object>> list = list2list(fromJsonArray, subTasks);
                        Toolkit.MapHelper.putDeepValue(returned, splitByDot(to), list);
                    }
                    break;
                case "parser":
                    from = task.get("from").getAsString();
                    to = task.get("to").getAsString();
                    apply = task.get("apply").getAsString();
                    try {
                        Function function = (Function) Class.forName(apply).newInstance();
                        Object source = Toolkit.JSON.getDeepValueOrNull(context, splitByDot(from), Object.class);
                        Object target = function.apply(source);
                        Toolkit.MapHelper.putDeepValue(returned, splitByDot(to), target);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                    break;
                case "process":
                    to = task.get("to").getAsString();
                    apply = task.get("apply").getAsString();
                    try {
                        Process proce = (Process) Class.forName(apply).newInstance();
                        Object target = proce.process(task, context);
                        Toolkit.MapHelper.putDeepValue(returned, splitByDot(to), target);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                    break;
            }
        }
        return returned;
    }

    public static List<Map<String, Object>> list2list(JsonArray arrayCtx, JsonArray taskSet) {
        List<Map<String, Object>> returned = new ArrayList<>();
        for (int i = 0; i < arrayCtx.size(); i++) {
            JsonElement element = arrayCtx.get(i);
            if (element != null && element.isJsonObject()) {
                Map<String, Object> copied = copy(element.getAsJsonObject(), taskSet);
                returned.add(copied);
            }
        }
        return returned;
    }

    public static List<Map<String, Object>> map2list(JsonObject mapContext, JsonArray taskSet) {
        List<Map<String, Object>> returned = new ArrayList<>();
        Set<Map.Entry<String, JsonElement>> objs = mapContext.entrySet();
        for (Map.Entry<String, JsonElement> obj : objs) {
            Map<String, Object> item = new LinkedHashMap<>();
            String key = obj.getKey();
            JsonElement valElement = obj.getValue();
            for (int i = 0; i < taskSet.size(); i++) {
                JsonObject task = taskSet.get(i).getAsJsonObject();
                String taskType = task.get("task").getAsString();
                String from;
                String to;
                String fromStrVal;
                JsonObject fromJsonVal;
                JsonArray fromJsonArray;
                JsonArray subTasks;
                String apply;
                Object toVal = null;
                String defaultValue;
                switch (taskType) {
                    case "keyCopy":
                        to = task.get("to").getAsString();
                        fromStrVal = key;
                        toVal = fromStrVal;
                        apply = Toolkit.JSON.getDeepValueOrNull(task, new String[]{"apply"}, String.class);
                        defaultValue = Toolkit.JSON.getDeepValueOrNull(task, new String[]{"defaultValue"}, String.class);
                        if (apply != null && apply.length() > 0) {
                            try {
                                Class function = Class.forName(apply);
                                Function fun = (Function) function.newInstance();
                                toVal = fun.apply(fromStrVal);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Toolkit.MapHelper.putDeepValue(item, splitByDot(to), toVal != null ? toVal : defaultValue);
                        break;
                    case "valueCopy":
                        if (valElement != null && valElement.isJsonObject()) {
                            from = task.get("from").getAsString();
                            to = task.get("to").getAsString();
                            fromStrVal = Toolkit.JSON.getDeepValueOrNull(valElement.getAsJsonObject(), splitByDot(from), String.class);
                            toVal = fromStrVal;
                            apply = Toolkit.JSON.getDeepValueOrNull(task, new String[]{"apply"}, String.class);
                            defaultValue = Toolkit.JSON.getDeepValueOrNull(task, new String[]{"defaultValue"}, String.class);
                            if (apply != null && apply.length() > 0) {
                                try {
                                    Class function = Class.forName(apply);
                                    Function fun = (Function) function.newInstance();
                                    toVal = fun.apply(fromStrVal);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Toolkit.MapHelper.putDeepValue(item, splitByDot(to), toVal!=null?toVal:defaultValue);
                        }
                        break;
                    case "map2list":
                        from = task.get("from").getAsString();
                        to = task.get("to").getAsString();
                        if (valElement != null && valElement.isJsonObject()) {
                            fromJsonVal = Toolkit.JSON.getDeepValueOrNull(valElement.getAsJsonObject(), splitByDot(from), JsonObject.class);
                            if (fromJsonVal != null) {
                                subTasks = task.get("tasks").getAsJsonArray();
                                List<Map<String, Object>> list = map2list(fromJsonVal, subTasks);
                                Toolkit.MapHelper.putDeepValue(item, splitByDot(to), list);
                            }
                        }
                        break;
                    case "list2list":
                        from = task.get("from").getAsString();
                        to = task.get("to").getAsString();
                        if (valElement != null) {
                            fromJsonArray = Toolkit.JSON.getDeepValueOrNull(valElement.getAsJsonObject(), splitByDot(from), JsonArray.class);
                            if (fromJsonArray != null) {
                                subTasks = task.get("tasks").getAsJsonArray();
                                List<Map<String, Object>> list = list2list(fromJsonArray, subTasks);
                                Toolkit.MapHelper.putDeepValue(item, splitByDot(to), list);
                            }
                        }
                        break;
                    case "valParser":
                        from = task.get("from").getAsString();
                        to = task.get("to").getAsString();
                        apply = task.get("apply").getAsString();
                        if (valElement != null) {
                            try {
                                Function function = (Function) Class.forName(apply).newInstance();
                                Object source = Toolkit.JSON.getDeepValueOrNull(valElement.getAsJsonObject(), splitByDot(from), Object.class);
                                Object target = function.apply(source);
                                Toolkit.MapHelper.putDeepValue(item, splitByDot(to), target);
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        }
                        break;
                    case "keyParser":
                        from = task.get("from").getAsString();
                        to = task.get("to").getAsString();
                        apply = task.get("apply").getAsString();
                        if (key != null) {
                            try {
                                Function function = (Function) Class.forName(apply).newInstance();
                                Object target = function.apply(key);
                                Toolkit.MapHelper.putDeepValue(item, splitByDot(to), target);
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        }
                        break;
                }
            }
            returned.add(item);
        }
        return returned;
    }

    public static String[] splitByDot(String paths) {
        return paths.split("\\.");
    }

}
