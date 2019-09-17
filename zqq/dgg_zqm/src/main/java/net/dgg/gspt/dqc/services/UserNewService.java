package net.dgg.gspt.dqc.services;

import net.dgg.gspt.dqc.dao.UserDao;
import net.dgg.gspt.dqc.dao.UserExtDao;
import net.dgg.gspt.dqc.dto.user.UserNewDto;
import net.dgg.gspt.dqc.entity.business.User;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.utils.PrimaryKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static net.dgg.iboss.base.util.StringUtils.encryptionNumber;

/**
 * Created by wu on 2017/7/31.
 */
@Service
public class UserNewService {

    private static String msgTemplate = "【智企名】欢迎注册！";

    @Autowired
    private UserService userService;
    @Autowired
    private UserExtDao userExtDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SmsNewService smsNewService;


    /**
     * 新注册登陆
     *
     * @param user
     * @return
     */
    @Transactional
    public User newRegister(String token, UserNewDto user) {
        Assert.notNull(user, "用户信息不能为空！");
        Assert.hasText(token, "token不能为空！");
        Assert.hasText(user.getNickname(), "联系人不能为空！");
        Assert.hasText(user.getUsername(), "电话不能为空！");
        Integer count = this.userExtDao.count(new HashMap() {{
            put("username", user.getUsername());
        }});
        Assert.isTrue(count.intValue() == 0, "用户已注册！");
        user.setUserId(PrimaryKeyUtils.getId());
        user.setCreatetime(new Date());
        user.setLastLoginDate(new Date());

        // 无法区分空号 干掉 2018年12月28日 10:06:05
        /// boolean is = this.smsNewService.sendMsg(user.getUsername(), msgTemplate);
        //user.setFlag(is ? 1 : 0);

        this.userExtDao.insertUser(user);
        RedisUtils.set(user.getUserId(), encryptionNumber(user.getUsername()));
        RedisUtils.expire(user.getUserId(), Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE))); //1小时
        return user;
    }


    /**
     * 行登陆
     *
     * @param user
     * @return
     */
    @Transactional
    public User newLogin(User user) {
        Assert.notNull(user, "用户信息不能为空！");
        Assert.hasText(user.getUsername(), "电话不能为空！");
        List<User> userList = this.userDao.findUserByName(user);

        User userSys = userList.isEmpty() ? null : userList.get(0);
        Assert.notNull(userSys, "用户未注册");

        RedisUtils.set(userSys.getUserId(), encryptionNumber(userSys.getUsername()));
        RedisUtils.expire(userSys.getUserId(), Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE))); //1小时

        return userSys;

    }

    public void logout(String userId) {
        Assert.hasText(userId, "userId不能为空！");
        RedisUtils.del(userId);
    }

    /**
     * 验证
     *
     * @param phone
     * @return
     */
    public Integer exit(String phone) {
        Assert.hasText(phone, "电话不能为空！");
        Integer count = this.userExtDao.count(new HashMap() {{
            put("username", phone);
        }});
        return count;
    }

}
