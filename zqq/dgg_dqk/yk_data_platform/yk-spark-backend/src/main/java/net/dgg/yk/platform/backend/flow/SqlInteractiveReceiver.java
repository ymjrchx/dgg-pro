package net.dgg.yk.platform.backend.flow;

import com.mongodb.BasicDBObject;
import net.dgg.yk.platform.backend.command.Command;
import net.dgg.yk.platform.backend.command.CommandReceiver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.bson.Document;

public class SqlInteractiveReceiver implements CommandReceiver {

    @Override
    public String getSupport() {
        return "sql";
    }

    @Override
    public <T> T execute(Command command) {
        String sql = command.getParameter("sql");
        //注册数据集为表
        try {
            SQLContext sqlCtx = command.getSession().sqlContext();
            Configuration configuration = new Configuration();
            FileSystem fs = FileSystem.get(configuration);
            FileStatus[] databases = fs.listStatus(new Path((String) command.getEnvParameter("hdfs.root")));
            for (FileStatus database : databases) {
                FileStatus[] collections = fs.listStatus(database.getPath());
                String databaseName = database.getPath().getName();
                for (FileStatus collection : collections) {
                    String collectionPath = collection.getPath().toUri().getPath();
                    String collectionName = collection.getPath().getName();
                    String originPath = collectionPath + "/" + "origin";
                    if (fs.exists(new Path(originPath))) {
                        JavaRDD<Object> mongoRDD = command.getSc().objectFile(originPath);
                        Dataset<Row> dataset = command.getSession().read().json(mongoRDD.map(doc -> {
                            if (doc instanceof Document) {
                                return ((Document) doc).toJson();
                            } else if (doc instanceof BasicDBObject) {
                                return ((BasicDBObject) doc).toJson();
                            } else {
                                throw new Exception();
                            }
                        }));
                        sqlCtx.registerDataFrameAsTable(dataset, databaseName + "_" + collectionName);
                    }
                }
            }
            JavaRDD<String> rs = sqlCtx.sql(sql).toJSON().toJavaRDD();
            System.out.println(String.format("SQL:%s", sql));
            System.out.println("采样10条结果记录");
            rs.take(10).forEach(System.out::println);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) new Object();
    }
}
