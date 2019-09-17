package net.dgg.yk.platform.backend.flow;

import com.mongodb.BasicDBObject;
import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.command.Command;
import net.dgg.yk.platform.backend.command.CommandReceiver;
import org.apache.spark.api.java.JavaRDD;
import org.bson.Document;

public class ShowReceiver implements CommandReceiver {

    @Override
    public String getSupport() {
        return "show";
    }

    @Override
    public <T> T execute(Command command) {
        boolean isLocal = command.getSc().getConf().get("spark.master").contains("loc");
        String database = command.getParameter("database");
        String localRoot = command.getEnvParameter("fs.root");
        String collection = command.getParameter("collection");
        String root = command.getEnvParameter("hdfs.root");
        String origin = "origin";
        String path = root + database + "/" + collection + "/" + origin;
        Integer count = Integer.valueOf(command.getParameter("count"));
        JavaRDD<Object> originDataset = command.getSc().objectFile((isLocal ? "file:///"+ localRoot : "") + path);
        System.out.printf("下面是采样前%d条数据：\n", count);
        originDataset.take(count).stream().map(doc -> {
            if (doc instanceof BasicDBObject) {
                return ((BasicDBObject) doc).toJson();
            } else if (doc instanceof Document) {
                return ((Document) doc).toJson();
            } else {
                return Toolkit.JSON.toJson(doc);
            }
        }).forEach(System.out::println);
        return null;
    }
}
