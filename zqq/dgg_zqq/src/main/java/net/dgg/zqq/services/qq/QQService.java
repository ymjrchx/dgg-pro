package net.dgg.zqq.services.qq;

import com.google.gson.Gson;
import net.dgg.zqq.constant.NickNameConstant;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.dao.UserDao;
import net.dgg.zqq.dao.qq.QQExtUserDao;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.entity.qq.QQExtUser;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.utils.BeanUtils;
import net.dgg.zqq.utils.HttpUtility;
import net.dgg.zqq.utils.PrimaryKeyUtils;
import net.fblock.core.common.KeyWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <QQService>
 * @despriction QQ登录
 * @create 2018/10/18 16:14
 **/
@Service
public class QQService {
    /**
     * 通过code获取access_token
     */
    private static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取openId
     */
    private static String GET_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";

    /**
     * 获取用户信息
     */
    private static String GET_USER_INFO_URL = "https://graph.qq.com/user/get_user_info";

    @Autowired
    private QQExtUserDao qqExtUserDao;
    @Autowired
    private UserDao userDao;


    @Transactional
    public Map login(String accessToken) {
        Assert.hasText(accessToken, "accessToken不能为空！");
        OpenIdResult openIdResult = this.getGetOpenId(accessToken);
        QQExtUser qqExtUser = this.getUserInfo(accessToken, openIdResult.getClient_id(), openIdResult.getOpenid());

        String nickName = qqExtUser.getNickname().replaceAll("[\\x{10000}-\\x{10FFFF}]", "");
        nickName = StringUtils.isNotEmpty(nickName) ? nickName : NickNameConstant.DEFAULT;
        qqExtUser.setNickname(nickName);

        List<QQExtUser> qqExtUserList = this.qqExtUserDao.query(new HashMap() {{
            put("openId", qqExtUser.getOpenId());
        }});

        Map map = new HashMap();

        QQExtUser qqExtUserSys = qqExtUserList.isEmpty() ? null : qqExtUserList.get(0);
        if (qqExtUserSys == null || StringUtils.isEmpty(qqExtUserSys.getUserId())) {
            qqExtUserSys = qqExtUserSys == null ? qqExtUser : qqExtUserSys;
            if (qqExtUserSys.getId() == null) {
                qqExtUserSys.setId(KeyWorker.nextId());
                qqExtUserSys.setUpdateTime(new Date());
                this.qqExtUserDao.save(qqExtUser);
            } else {
                qqExtUserSys.setUpdateTime(new Date());
                this.qqExtUserDao.update(qqExtUser);
            }
            map.put("userId", qqExtUserSys.getId());
            map.put("phoneBind", 0);
        } else {
            User user = this.userDao.findUserById(qqExtUserSys.getUserId());
            Assert.notNull(user, "未查询到用户！");
            Assert.isTrue(StatusConstant.ENABLE.equals(user.getStatus()), "该绑定电话用户已被禁用");
            Gson gson = new Gson();
            BeanUtils.copyPropertiesIgnoreNull(gson.fromJson(gson.toJson(qqExtUser), QQExtUser.class), qqExtUserSys);
            user.setLastLoginDate(new Date());
            this.qqExtUserDao.update(qqExtUserSys);
            this.userDao.updateUser(user);
            map.put("phoneNo", net.dgg.iboss.base.util.StringUtils.encryptionNumber(user.getUsername()));
            map.put("nickname", this.getUserShowName(user, qqExtUser));
            map.put("userId", user.getUserId());
            map.put("isMember", user.getIsMember());
            map.put("type", user.getType());
            map.put("isInner", user.getIsInner());
            map.put("memberExpirationdate", user.getMemberExpirationdate());
            map.put("phoneBind", 1);
            // 设置登录状态
            RedisUtils.set(user.getUserId(), this.getUserShowName(user, qqExtUser));
            RedisUtils.expire(user.getUserId(), Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE))); //1小时);
        }

        return map;
    }

    // h获取 openId
    private OpenIdResult getGetOpenId(String accessToken) {
        Assert.hasText(accessToken, "accessToken不能为空！");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        String resStr = null;
        try {
            resStr = HttpUtility.getRest(GET_OPEN_ID_URL, paramMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(resStr, "通过ACCESS_TOKEN获取openId无返回结果！");
        if (resStr.startsWith("callback")) {
            resStr = resStr.substring(resStr.indexOf("{"));
            resStr = resStr.substring(0, resStr.lastIndexOf("}") + 1);
        }
        OpenIdResult openIdResult = new Gson().fromJson(resStr, OpenIdResult.class);
        Assert.notNull(openIdResult, "通过ACCESS_TOKEN获取openId结果转换失败！");
        Assert.hasText(openIdResult.getOpenid(), "转换结果中openId为空！");
        Assert.hasText(openIdResult.getClient_id(), "转换结果中client_id为空！");
        return openIdResult;
    }

    // 获取用户信息
    private QQExtUser getUserInfo(String accessToken, String appId, String openId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        paramMap.put("oauth_consumer_key", appId);
        paramMap.put("openid", openId);
        String resStr = null;
        try {
            resStr = HttpUtility.getRest(GET_USER_INFO_URL, paramMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(resStr, "通过openId获取用户信息无返回结果！");
        UserInfoResult userInfo = new Gson().fromJson(resStr, UserInfoResult.class);
        Assert.notNull(userInfo, "用户信息返回结果转换失败！");
        userInfo.setOpenId(openId);
        return userInfo;
    }

    /**
     * 绑定电话
     *
     * @param userId
     * @param phone
     * @param smsCode
     * @return
     */
    @Transactional
    public Map bindPhone(String token, Long userId, String phone, String smsCode) {
        Assert.notNull(token, "用户token不能为空！");
        Assert.notNull(userId, "用户ID不能为空！");
        Assert.hasText(phone, "电话不能为空！");
        Assert.hasText(smsCode, "短信验证码不能为空");
        String code = RedisUtils.get(token.concat(PTConst.VERIFY_SMS));
        Assert.hasText(code, "验证码已失效！");
        String[] codes = code.split("&");
        Assert.isTrue(codes.length > 1, "请获取验证码！");
        Assert.isTrue(phone.equals(codes[1]), "验证码错误！");
        Assert.isTrue(smsCode.equals(codes[0]), "验证码错误！");

        QQExtUser qqExtUser = this.qqExtUserDao.findById(userId);
        Assert.notNull(qqExtUser, "未查询到QQ用户!");

        // 查询系统用户
        List<User> userList = this.userDao.findUserByUserName(phone);
        User user = userList.isEmpty() ? null : userList.get(0);
        // 限制单一绑定
        if (user != null) {
            Map paraMap = new HashMap();
            paraMap.put("userId", user.getUserId());
            Integer count = this.qqExtUserDao.count(paraMap);
            Assert.isTrue(count.intValue() == 0, "此电话已被QQ绑定，无法重复绑定！");
        }

        // 未查询到 则创建
        if (user == null) {
            user = new User();
            user.setUserId(PrimaryKeyUtils.getId());
            user.setUsername(phone);
            user.setType(1);
            user.setIsMember(0);
            user.setMoney(new BigDecimal(0));
            user.setIsInner(0);
            user.setRegist(0);
            user.setStatus(StatusConstant.ENABLE);
            user.setFlag(1);
            user.setEnabled(1);
            user.setUseErpPwd(1);
            user.setCreatetime(new Date());
            user.setLastLoginDate(new Date());
            this.userDao.insertUser(user);
        }

        Assert.isTrue(StatusConstant.ENABLE.equals(user.getStatus()), "该用户已被禁用！");

        qqExtUser.setUserId(user.getUserId());
        qqExtUser.setUpdateTime(new Date());
        this.qqExtUserDao.update(qqExtUser);

        // 设置登录状态
        RedisUtils.set(user.getUserId(), this.getUserShowName(user, qqExtUser));
        RedisUtils.expire(user.getUserId(), Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE))); //1小时

        Map map = new HashMap();
        map.put("phoneNo", net.dgg.iboss.base.util.StringUtils.encryptionNumber(user.getUsername()));
        map.put("nickname", this.getUserShowName(user, qqExtUser));
        map.put("userId", user.getUserId());
        map.put("isMember", user.getIsMember());
        map.put("type", user.getType());
        map.put("isInner", user.getIsInner());
        map.put("memberExpirationdate", user.getMemberExpirationdate());
        map.put("phoneBind", 1);

        return map;
    }


    /**
     * openId返回结果
     */
    private static class OpenIdResult {
        private String openid;
        private String client_id;
        private String code;
        private String msg;

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getOpenid() {
            return openid;
        }

        public String getClient_id() {
            return client_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    /**
     * 返回的用户信息
     */
    private static class UserInfoResult extends QQExtUser {

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    /**
     * 获取用户 显示名
     *
     * @param user
     * @param qqExtUser
     * @return
     */
    private String getUserShowName(User user, QQExtUser qqExtUser) {
        if (!StringUtils.isEmpty(user.getNickname())) {
            return user.getNickname();
        }
        if (!StringUtils.isEmpty(qqExtUser.getNickname())) {
            return qqExtUser.getNickname();
        }
        if (!StringUtils.isEmpty(user.getUsername())) {
            return net.dgg.iboss.base.util.StringUtils.encryptionNumber(user.getUsername());
        }
        return null;
    }

    /**
     * @param userId
     * @param phone
     * @return
     */
    public int checkPhoneBind(Long userId, String phone) {
        Assert.notNull(userId, "userId不能为空！");
        Assert.hasText(phone, "phone不能为空！");
        Integer count = this.qqExtUserDao.count(new HashMap() {{
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
        Integer count = this.qqExtUserDao.count(new HashMap() {{
            put("userId", user.getUserId());
        }});
        return count;
    }


}
