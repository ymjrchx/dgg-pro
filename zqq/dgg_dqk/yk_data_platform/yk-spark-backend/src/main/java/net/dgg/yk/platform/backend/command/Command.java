package net.dgg.yk.platform.backend.command;

import com.google.gson.JsonObject;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public interface Command {

    String getCommand();

    JsonObject getCommandOrigin();

    String getParameter(String name);

    SparkSession getSession();

    JavaSparkContext getSc();

    Map<String, Object> getEnv();

    <T> T getEnvParameter(String path);

}
