package net.dgg.dqc.backservice.framework.mongo;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class AbstractModel implements DTO, IdIModel{
    @MongoId // auto
    @MongoObjectId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}