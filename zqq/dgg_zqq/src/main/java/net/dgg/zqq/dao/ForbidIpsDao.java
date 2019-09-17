package net.dgg.zqq.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * ip封禁Dao
 * Created by zyw on 2018/10/12.
 */
@Repository
public interface ForbidIpsDao {
    void insert(Map record);
}
