package net.dgg.backend.thrift.business;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoDao {

    private static MongoClient client;

    public static MongoClient getMongoClient(String[] mongoHost, Integer[] mongoPort, String mongoUser, String mongoPasswd) {
        MongoClientOptions options = MongoClientOptions.builder().readPreference(ReadPreference.secondaryPreferred()).build();
        List<ServerAddress> addresses = new ArrayList<>();
        for (int i = 0; i < mongoHost.length; i++) {
            ServerAddress serverAddress = new ServerAddress(mongoHost[i], mongoPort[i]);
            addresses.add(serverAddress);
        }
        MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", mongoUser, mongoPasswd.toCharArray());
        client = new MongoClient(addresses, Arrays.asList(credential), options);
        return client;
    }

    public static MongoClient getNormalMongoClient(String[] mongoHost, Integer[] mongoPort, String mongoUser, String mongoPasswd) {
        MongoClientOptions options = MongoClientOptions.builder().build();
        List<ServerAddress> addresses = new ArrayList<>();
        for (int i = 0; i < mongoHost.length; i++) {
            ServerAddress serverAddress = new ServerAddress(mongoHost[i], mongoPort[i]);
            addresses.add(serverAddress);
        }
        MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", mongoUser, mongoPasswd.toCharArray());
        client = new MongoClient(addresses, Arrays.asList(credential), options);
        return client;
    }

}
