package net.dgg.bigdata.sjjs.web.service.impl;

import net.dgg.bigdata.sjjs.web.constant.StatusConstant;
import net.dgg.bigdata.sjjs.web.dao.UserExtDao;
import net.dgg.bigdata.sjjs.web.entity.User;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.utils.DggStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/10/11 16:01
 */
@Service
public class UserExtService {
    @Autowired
    UserExtDao userExtDao;
    @Autowired
    private DggRedisService redisService;

    /**
     * 根据 query查询用户
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.userExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.userExtDao.queryMap(query);
        }
        return Collections.emptyList();
    }

    /**
     * 根据userId修改用户状态
     */
    @Transactional
    public void updateStatus(String userId) {
        User user = this.userExtDao.findUserById(userId);
        String status = user.getStatus();
        if (DggStringUtils.isNullOrEmpty(status)) {
            this.userExtDao.updateStatus1(userId);
        } else if (status.equals(StatusConstant.ENABLE)) {
            this.userExtDao.updateStatus2(userId);
            redisService.getJedisCluster().del(userId);
        } else if (status.equals(StatusConstant.DISABLE)) {
            this.userExtDao.updateStatus1(userId);
        }
    }

    @Transactional
    public void updateStatus(String userId, String status) {
        this.userExtDao.updateStatus(userId, status);
    }


}
