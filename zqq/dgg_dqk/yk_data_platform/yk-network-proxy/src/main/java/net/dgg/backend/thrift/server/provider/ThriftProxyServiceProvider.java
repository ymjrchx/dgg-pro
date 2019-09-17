package net.dgg.backend.thrift.server.provider;

import com.google.gson.JsonArray;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import net.dgg.backend.thrift.business.MongoDao;
import net.dgg.backend.thrift.proxy.ThriftProxyService;
import net.dgg.yk.common.Toolkit;
import org.apache.thrift.TException;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ThriftProxyServiceProvider implements ThriftProxyService.Iface {

    private String[] hosts;
    private Integer[] ports;
    private String username;
    private String password;
    private MongoCollection<Document> collection;
    private MongoClient mongoClient;

    public ThriftProxyServiceProvider(String[] hosts, Integer[] ports, String username, String password) {
        this.hosts = hosts;
        this.ports = ports;
        this.username = username;
        this.password = password;
    }

    public MongoClient getClient() {
        if (mongoClient != null) {
            return mongoClient;
        } else {
            mongoClient = MongoDao.getMongoClient(hosts, ports, username, password);
            return mongoClient;
        }
    }

    @Override
    public String call(String request) throws TException {
        boolean tryDone = true;
        int count = 0;
        while (count++ < 20 && tryDone) {
            try {
                if (collection == null) {
                    collection = getClient().getDatabase("all_com").getCollection("all_results");
                }
                JsonArray arr = Toolkit.JSON.parseJsonArray(request);
                List<Bson> conditions = new ArrayList<>();
                arr.forEach(je -> {
                    if (je != null && je.isJsonPrimitive()) {
                        conditions.add(Filters.eq("_id", je.getAsString()));
                    }
                });
                Bson[] conds = conditions.toArray(new Bson[0]);
                System.out.println(String.format("request:%s", request));
                MongoCursor<Document> docCursor = collection.find(Filters.or(conds)).projection(new BasicDBObject("docs.background.baseInfo.companyTel", 1)).iterator();
                List<Document> docs = new LinkedList<>();
                while (docCursor.hasNext()) {
                    docs.add(docCursor.next());
                }
                tryDone = false;
                return Toolkit.JSON.toJson(docs);
            } catch (Exception e) {
                e.printStackTrace();
                tryDone = true;
            }
        }
        return "[]";
    }
}
