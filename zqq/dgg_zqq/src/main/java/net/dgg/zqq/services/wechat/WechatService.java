package net.dgg.zqq.services.wechat;

import com.google.gson.Gson;
import net.dgg.zqq.constant.NickNameConstant;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.controller.wechat.WechatConfig;
import net.dgg.zqq.dao.UserDao;
import net.dgg.zqq.dao.wechat.WechatExtUserDao;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.entity.wechat.WechatExtUser;
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
 * @ClassName <WechatService>
 * @despriction 微信服务
 * @create 2018/09/17 15:58
 **/
@Service
public class WechatService {
    /**
     * 通过code获取access_token
     */
    private static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 获取用户信息
     */
    private static String GET_WECHAT_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    private static String GRANT_TYPE = "authorization_code";

    @Autowired
    private WechatConfig wechatConfig;
    @Autowired
    private WechatExtUserDao wechatExtUserDao;
    @Autowired
    private UserDao userDao;


    @Transactional
    public Map login(String code, String state) {
        Assert.hasText(code, "code不能为空！");
        Assert.hasText(state, "state不能为空！");
        // 获取用户信息
        GetAccessTokenResult getAccessTokenResult = this.getAccessToken(wechatConfig.getAppId(), wechatConfig.getAppSecret(), code);
        WechatExtUser wechatExtUser = this.getWechatUser(getAccessTokenResult.getAccess_token(), getAccessTokenResult.getOpenid());
        // 验证 本地数据库
        List<WechatExtUser> wechatExtUserList = wechatExtUserDao.query(new HashMap() {{
            put("unionId", wechatExtUser.getUnionId());
        }});

        String nickName = wechatExtUser.getNickName().replaceAll("[\\x{10000}-\\x{10FFFF}]", "");
        nickName = StringUtils.isNotEmpty(nickName) ? nickName : NickNameConstant.DEFAULT;
        wechatExtUser.setNickName(nickName);

        Map map = new HashMap();
        WechatExtUser wechatExtUserSys = wechatExtUserList.isEmpty() ? null : wechatExtUserList.get(0);
        if (wechatExtUserSys == null || StringUtils.isEmpty(wechatExtUserSys.getUserId())) {
            wechatExtUserSys = wechatExtUserSys == null ? wechatExtUser : wechatExtUserSys;
            if (wechatExtUserSys.getId() == null) {
                wechatExtUserSys.setId(KeyWorker.nextId());
                wechatExtUserSys.setUpdateTime(new Date());
                this.wechatExtUserDao.save(wechatExtUserSys);
            } else {
                wechatExtUserSys.setUpdateTime(new Date());
                this.wechatExtUserDao.update(wechatExtUserSys);
            }
            map.put("userId", wechatExtUserSys.getId());
            map.put("phoneBind", 0);
        } else {
            User user = this.userDao.findUserById(wechatExtUserSys.getUserId());
            Assert.notNull(user, "未查询到用户！");
            Assert.isTrue(StatusConstant.ENABLE.equals(user.getStatus()), "该绑定电话用户已被禁用");
            Gson gson = new Gson();
            BeanUtils.copyPropertiesIgnoreNull(gson.fromJson(gson.toJson(wechatExtUser), WechatExtUser.class), wechatExtUserSys);
            user.setLastLoginDate(new Date());
            this.wechatExtUserDao.update(wechatExtUserSys);
            this.userDao.updateUser(user);
            map.put("phoneNo", net.dgg.iboss.base.util.StringUtils.encryptionNumber(user.getUsername()));
            map.put("nickname", this.getUserShowName(user, wechatExtUserSys));
            map.put("userId", user.getUserId());
            map.put("isMember", user.getIsMember());
            map.put("type", user.getType());
            map.put("isInner", user.getIsInner());
            map.put("memberExpirationdate", user.getMemberExpirationdate());
            map.put("phoneBind", 1);
            // 设置登录状态
            RedisUtils.set(user.getUserId(), this.getUserShowName(user, wechatExtUser));
            RedisUtils.expire(user.getUserId(), Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE))); //1小时
        }

        return map;
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

        WechatExtUser wechatExtUser = this.wechatExtUserDao.findById(userId);
        Assert.notNull(wechatExtUser, "未查询到QQ用户!");

        // 查询系统用户
        List<User> userList = this.userDao.findUserByUserName(phone);
        User user = userList.isEmpty() ? null : userList.get(0);
        // 限制单一绑定
        if (user != null) {
            Assert.isTrue(this.checkBind(phone) == 0, "此电话已被微信绑定，无法重复绑定！");
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

        wechatExtUser.setUserId(user.getUserId());
        wechatExtUser.setUpdateTime(new Date());
        this.wechatExtUserDao.update(wechatExtUser);

        // 设置登录状态
        RedisUtils.set(user.getUserId(), this.getUserShowName(user, wechatExtUser));
        RedisUtils.expire(user.getUserId(), Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE))); //1小时

        Map map = new HashMap();
        map.put("phoneNo", net.dgg.iboss.base.util.StringUtils.encryptionNumber(user.getUsername()));
        map.put("nickname", this.getUserShowName(user, wechatExtUser));
        map.put("userId", user.getUserId());
        map.put("isMember", user.getIsMember());
        map.put("type", user.getType());
        map.put("isInner", user.getIsInner());
        map.put("memberExpirationdate", user.getMemberExpirationdate());
        map.put("phoneBind", 1);

        return map;
    }

    /**
     * 获取微信登录用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    private WechatExtUser getWechatUser(String accessToken, String openId) {
        Assert.hasText(accessToken, "access_token不能为空！");
        Assert.hasText(openId, "用户openId不能为空！");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        paramMap.put("openid", openId);
        String resStr = null;
        try {
            resStr = HttpUtility.getRest(GET_WECHAT_USER_INFO_URL, paramMap);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.isTrue(false, "获取用户信息失败！" + e.getMessage());
        }
        Assert.hasText(resStr, "获取用户信息返回为空！");
        GetUserResult getUserResult = new Gson().fromJson(resStr, GetUserResult.class);
        Assert.notNull(getUserResult, "用户信息转换失败！");
        Assert.notNull(getUserResult.getUnionId(), "用户信息获取失败！" + getUserResult.getErrmsg());
        return getUserResult;
    }


    /**
     * 获取Access token
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    private GetAccessTokenResult getAccessToken(String appId, String appSecret, String code) {
        Assert.hasText(appId, "appId不能为空！");
        Assert.hasText(appSecret, "appSecret不能为空！");
        Assert.hasText(code, "code不能为空！");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appId);
        paramMap.put("secret", appSecret);
        paramMap.put("code", code);
        paramMap.put("grant_type", GRANT_TYPE);
        String resStr = null;
        try {
            resStr = HttpUtility.getRest(GET_ACCESS_TOKEN_URL, paramMap);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.isTrue(false, "通过code获取ACCESS_TOKEN失败！" + e.getMessage());
        }
        Assert.hasText(resStr, "通过code获取ACCESS_TOKEN返回为空！");
        GetAccessTokenResult getAccessTokenResult = new Gson().fromJson(resStr, GetAccessTokenResult.class);
        Assert.notNull(getAccessTokenResult, "通过code获取ACCESS_TOKEN结果转换失败！");
        Assert.hasText(getAccessTokenResult.getAccess_token(), "通过code获取ACCESS_TOKEN失败！" + getAccessTokenResult.getErrmsg());
        return getAccessTokenResult;
    }


    /**
     * Access 返回结果
     */
    private static class GetAccessTokenResult {

        /**
         * access_token : ACCESS_TOKEN
         * errcode : 40029
         * refresh_token : REFRESH_TOKEN
         * openid : OPENID
         * scope : SCOPE
         * errmsg : invalid code
         * expires_in : 7200
         */
        private String access_token;
        private int errcode;
        private String refresh_token;
        private String openid;
        private String scope;
        private String errmsg;
        private int expires_in;

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public int getErrcode() {
            return errcode;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public String getOpenid() {
            return openid;
        }

        public String getScope() {
            return scope;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public int getExpires_in() {
            return expires_in;
        }
    }

    private static class GetUserResult extends WechatExtUser {

        /**
         * errcode : 40003
         * errmsg : invalid openid
         */
        private int errcode;
        private String errmsg;

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public int getErrcode() {
            return errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }
    }

    /**
     * 获取用户 显示名
     *
     * @param user
     * @param wechatExtUser
     * @return
     */
    private String getUserShowName(User user, WechatExtUser wechatExtUser) {
        if (!StringUtils.isEmpty(user.getNickname())) {
            return user.getNickname();
        }
        if (!StringUtils.isEmpty(wechatExtUser.getNickName())) {
            return wechatExtUser.getNickName();
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
        Integer count = this.wechatExtUserDao.count(new HashMap() {{
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
        Integer count = this.wechatExtUserDao.count(new HashMap() {{
            put("userId", user.getUserId());
        }});
        return count;
    }

}
