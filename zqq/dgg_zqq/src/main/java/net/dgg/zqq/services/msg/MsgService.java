package net.dgg.zqq.services.msg;

import net.dgg.zqq.entity.msg.Msg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MsgService
{
    void save(List<Msg> msgList);

    void update(Msg msg);

    Msg findById(@Param("id") Long id);

    List<Msg> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(String id);

    void deleteById(@Param("id") Long id);

    void updateMsgReaded(@Param("id") Long id);

    Map getPersonalMsg(int pageSize, int pageNum, String userId);
}
