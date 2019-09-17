package net.dgg.dqc.backservice.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author 刘阳
 * @ClassName <MongoBaseEntity>
 * @despriction mongo基础实体类
 * @create 2018/07/27 17:45
 **/
public class MongoBaseEntity implements Serializable {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
