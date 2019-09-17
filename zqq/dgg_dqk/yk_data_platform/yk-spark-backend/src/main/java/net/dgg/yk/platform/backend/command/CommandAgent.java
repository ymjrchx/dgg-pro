package net.dgg.yk.platform.backend.command;

import com.google.gson.JsonObject;
import net.dgg.yk.common.Toolkit;
import net.dgg.yk.platform.backend.flow.delegates.Support;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public class CommandAgent implements Command {

    JsonObject commandBody;
    SparkSession session;
    JavaSparkContext sc;
    Map<String, Object> env;

    CommandAgent(Map<String, Object> env, String body, SparkSession session, JavaSparkContext sc) {
        this.commandBody = Toolkit.JSON.parseJsonObject(body);
        this.session = session;
        this.sc = sc;
        this.env = env;
    }

    @Override
    public JsonObject getCommandOrigin() {
        return commandBody;
    }

    @Override
    public String getCommand() {
        return getParameter("command");
    }

    @Override
    public String getParameter(String name) {
        return Toolkit.JSON.getDeepValueOrNull(commandBody, Support.splitByDot(name), String.class);
    }

    @Override
    public SparkSession getSession() {
        return session;
    }

    @Override
    public JavaSparkContext getSc() {
        return sc;
    }

    @Override
    public Map<String, Object> getEnv() {
        return env;
    }

    @Override
    public <T> T getEnvParameter(String path) {
        return (T) Toolkit.MapHelper.getDeepValue(env, Support.splitByDot(path));
    }
}
