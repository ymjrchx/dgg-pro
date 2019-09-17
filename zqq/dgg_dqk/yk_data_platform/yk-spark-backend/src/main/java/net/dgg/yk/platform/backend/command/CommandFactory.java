package net.dgg.yk.platform.backend.command;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public class CommandFactory {

    public static Command buildCommand(Map<String, Object> env, String commandBody, SparkSession spark, JavaSparkContext sc) {
        return new CommandAgent(env, commandBody, spark, sc);
    }

}
