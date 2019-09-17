package net.dgg.zqq.services;

import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.dao.UserExtDao;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.utils.StringUtils;
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
        if (StringUtils.isNullOrEmpty(status)) {
            this.userExtDao.updateStatus1(userId);
        } else if (status.equals(StatusConstant.ENABLE)) {
            this.userExtDao.updateStatus2(userId);
            RedisUtils.del(userId);
        } else if (status.equals(StatusConstant.DISABLE)) {
            this.userExtDao.updateStatus1(userId);
        }
    }

    @Transactional
    public void updateStatus(String userId, String status) {
        this.userExtDao.updateStatus(userId, status);
    }


}
