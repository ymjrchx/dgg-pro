package net.dgg.gspt.dqc.services.msg.impl;

import net.dgg.gspt.dqc.constant.UserIdKeyConstant;
import net.dgg.gspt.dqc.dao.msg.MsgDao;
import net.dgg.gspt.dqc.entity.msg.Msg;
import net.dgg.gspt.dqc.services.msg.MsgService;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MsgDao msgDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;

    @Override
    @Transactional
    public void save(List<Msg> msgList) {

        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");
        Assert.notNull(msgList, "msg消息为空");

        for (Msg msg:msgList){
            Assert.notNull(msg.getContent(),"保存消息入参为空");
            Assert.notNull(msg.getFlag(),"保存消息入参为空");
            Assert.notNull(msg.getTitle(),"保存消息入参为空");
            Assert.notNull(msg.getType(),"保存消息入参为空");

            msg.setCreaterId(userId);
            msg.setRead(0);
            msg.setFlag(1);
            msg.setUserId(userId.toString());
            msg.setCreateUser(userEntity);
            msg.setCreaterName(userEntity.getRealName());

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(new Date());
            msg.setId(Long.valueOf(date.substring(3)).longValue());
            msgDao.save(msg);
        }
    }

    @Override
    @Transactional
    public void update(Msg msg) {
        Assert.notNull(msg,"更新入参为空");
        msgDao.update(msg);
    }

    @Override
    public Msg findById(Long id) {
        Assert.notNull(id,"查询条件id为空");
        return msgDao.findById(id);
    }

    @Override
    public List<Msg> query(Map map) {
        Assert.notNull(map,"query查询条件为空");
        return msgDao.query(map);
    }

    @Override
    public List<Map> queryMap(Map map) {
        Assert.notNull(map,"queryMap查询条件为空");
        return msgDao.queryMap(map);
    }

    @Override
    public Integer count(String id) {
        Map map = new HashMap();
        map.put("read",0);
        map.put("userId",id);
        return msgDao.counts(map);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id,"删除消息id为空");
        msgDao.deleteById(id);
    }

    @Override
    @Transactional
    public void updateMsgReaded(Long id) {
        Assert.notNull(id,"消息已读设置id为空");
        msgDao.updateMsgReaded(id);
    }

    @Override
    @Transactional
    public Map getPersonalMsg(int pageSize, int pageNum, String userId) {
        Map resultMap = new HashMap();
        Map params = new HashMap();
        params.put("start", (pageNum-1)*pageSize);
        params.put("limit", pageSize);
        params.put("userId",userId);

        List<Map> maps = msgDao.queryMap(params);
        Integer count = msgDao.count(params);
        resultMap.put("data",maps);
        resultMap.put("count",count);
        return resultMap;
    }
}
