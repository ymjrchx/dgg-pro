package net.dgg.dqc.backservice.framework.mongo;

import com.mongodb.DB;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * Created by jiangsh on 2018/5/18 10:33
 */
public class MongoFactory {

    private static Jongo getJonge(DB db) {
        return new Jongo(db);
    }

    public static DB getDb(String dbNaqme) {
        return MongoConfig.getMongoClient().getDB(dbNaqme);
    }

    public static MongoCollection getCol(DB db, String col) {
        return getJonge(db).getCollection(col);
    }

    public static MongoCollection getColByDb(String dbName, String col) {
        return getCol(getDb(dbName), col);
    }

}
