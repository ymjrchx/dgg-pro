package net.dgg.zqq.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by 李程 on 2018/11/9.
 */
public class FreemarkerUtils {

    @SneakyThrows
    public static String renderBy(String name, String templateString, Map<String, Object> ctx) throws IOException {
        Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        Template template = new Template(name, templateString, conf);
        StringWriter out = new StringWriter();
        template.process(ctx, out);
        return out.toString();
    }

}
