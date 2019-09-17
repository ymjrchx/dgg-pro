package net.dgg.gspt.dqc.services;

import net.dgg.gspt.dqc.dao.UserDao;
import net.dgg.gspt.dqc.dao.UserExtDao;
import net.dgg.gspt.dqc.entity.business.User;
import net.dgg.gspt.dqc.framework.PTConst;
import net.dgg.gspt.dqc.framework.redis.RedisFactory;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.framework.security.DESPlus;
import net.dgg.gspt.dqc.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/9/30 15:13
 */

@Service
public class PersonInfoService {

    @Autowired
    UserDao userDao;


    @Autowired
    UserExtDao userExtDao;

    //根据userId查询个人信息
    public User findInfoById(String userId) {
        return this.userDao.selectPersonInfoById(userId);
    }

    //根据userId更新个人信息
    @Transactional
    public void updateInfoById(String userId, String nickname, int sex, java.sql.Date birthday) {
        Assert.hasText(nickname, "用户昵称不能为空");
        User user = new User();
        user.setUserId(userId);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setBirthday(birthday);
        this.userDao.updateUser(user);
    }

    //根据userId更新邮箱地址
    @Transactional
    public void updateEmailByid(String userId, String email) {
        User user = new User();
        user.setUserId(userId);
        user.setEmail(email);
        this.userDao.updateUser(user);
    }

    //根据userId更新登录密码
    @Transactional
    public void updateLoginPwdByid(String userId, String loginPwd) {
        User user = new User();
        user.setUserId(userId);
        user.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(loginPwd));
        this.userDao.updateUser(user);
    }

    //根据userId更新手机号码
    @Transactional
    public void updateUsernamedByid(String userId, String username) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        this.userDao.updateUser(user);
    }

    //验证码验证
    public String codeVerify(String userId, String verify, String token) {

        if (StringUtils.isNullOrEmpty(verify)) {
            return "验证码不能为空";
        }
        JedisCluster cluster = RedisFactory.getJedisCluster();
        String smsCode = cluster.get(token + PTConst.VERIFY_SMS);
        if (smsCode == null) {
            return "请获取验证码";
        }
        String code = smsCode.split("&")[0];
        String phoneNo = smsCode.split("&")[1];
        if (StringUtils.isNullOrEmpty(code)) {
            return "请获取验证码";
        }
        if (!verify.equals(code)) {
            return "验证码错误";
        }
        User user = this.userDao.findUserById(userId);
        String username = user.getUsername(); //获取用户电话号码
        if (!phoneNo.equals(username)) {
            return "验证码与手机号不匹配";
        } else return "success";
    }


    /**
     * @param userId
     * @param phone
     * @return
     */
    public int checkPhoneBind(String userId, String phone) {
        Assert.notNull(userId, "userId不能为空！");
        Assert.hasText(phone, "phone不能为空！");
        Integer count = this.userExtDao.count(new HashMap() {{
            put("id", userId);
        }});
        Assert.isTrue(count.intValue() > 0, "未查询到用户!");
        return this.checkBind(phone);
    }


    /**
     * 验证是否绑定过
     *
     * @param phone
     * @return
     */
    private int checkBind(String phone) {
        List<User> userList = this.userDao.findUserByUserName(phone);
        if (userList.isEmpty()) {
            return 0;
        }
        User user = userList.get(0);
        Integer count = this.userExtDao.count(new HashMap() {{
            put("userId", user.getUserId());
        }});
        return count;
    }

    /**
     * 修改昵称
     */
    @Transactional
    public void updateNicknameById(String userId, String nickname) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登陆超期，请重新登录！");
        Assert.hasText(nickname, "昵称不能为空");
        Assert.isTrue(nickname.length() < 16 && nickname.length() > 2, "昵称不符合规范！");
        User user = this.userDao.findUserById(userId);
        user.setNickname(nickname);
        this.userDao.updateUser(user);
    }

}
