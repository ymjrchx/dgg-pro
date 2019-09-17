package net.dgg.bigdata.sjjs.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.constant.StatusConstant;
import net.dgg.bigdata.sjjs.web.dao.UserDao;
import net.dgg.bigdata.sjjs.web.entity.User;
import net.dgg.bigdata.sjjs.web.entity.dto.*;
import net.dgg.bigdata.sjjs.web.service.UserService;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.utils.DESPlus;
import net.dgg.core.utils.DggJsonUtils;
import net.dgg.core.utils.DggStringUtils;
import net.dgg.core.utils.PrimaryKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wu on 2017/7/31.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userMapper;

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final String LOGIN_FLAG = "0";

    @Autowired
    private DggRedisService redisService;

    private static final int redisTokenExpire = 3600;

    private static final int redisTokenExpireDay = 30;

    private static final int redisTokenExpireApp = 1800;

    @Override
    public List<User> findAll(Map condition) {
        return null;
    }

    /*
     * 根据用户ID查询用户信息
     */
    @Override
    public User findOne(String id) {
        if (DggStringUtils.isNullOrEmpty(id)) {
            throw new RuntimeException("id need");
        } else {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("id", id);
            return userMapper.selectByPrimaryKey(dataMap);
        }
        // return null;
    }

    @Override
    public int insert(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    @Transactional("xaTransactionManager")
    public int update(User user) {
        userMapper.updateUser(user);
        return 1;
    }

    @Override
    public int delete(String id) {
        // userMapper.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    public String login(UserDto user, String token) {
        // TODO Auto-generated method stub
        log.info("token:" + token);
        String result = null;
        String code = user.getImageVerifyCode();
        if (DggStringUtils.isNullOrEmpty(user.getPhoneNo())) {
            return DggJsonUtils.getJsonString(-2, "登录名不能为空", null);
        }

        if (DggStringUtils.isNullOrEmpty(user.getLoginPwd())) {
            return DggJsonUtils.getJsonString(-2, "登录密码不能为空", null);
        }

        // 如果type为0则不验证验证码，否则验证验证码。
        if (!LOGIN_FLAG.equals(user.getType())) {
            if (DggStringUtils.isNullOrEmpty(code)) {
                return DggJsonUtils.getJsonString(-2, "验证码不能为空", null);
            }
        }
        try {
            // JedisCluster cluster = RedisFactory.getJedisCluster();
            // //获取redis连接
            // JedisPool cluster = RedisFactory.getJedisPool();
            // Map<String, String> configMap =
            // ResourceUtils.getResource(PTConst.USER_CACHE).getMap();
            // //读取redis配置文件

            User u = new User();
            u.setUsername(user.getPhoneNo());
            List<User> list = userMapper.findUserByName(u);
            if (list.isEmpty()) {
                result = DggJsonUtils.getJsonString(-2, "用户不存在", null);
                return result;
            }

            if (!new DESPlus(PTConst.PWD_KEY).encrypt(user.getLoginPwd()).equals(list.get(0).getLoginPwd())) {
                result = DggJsonUtils.getJsonString(-2, "密码错误", null);
                return result;
            }

            if (!LOGIN_FLAG.equals(user.getType())) {
                String verifyCode = redisService.getJedisCluster().get(token + PTConst.VERIFY_IMG);
                if (DggStringUtils.isNullOrEmpty(verifyCode)) {
                    return DggJsonUtils.getJsonString(-2, "请获取验证码", null);
                }
                if (!code.equals(verifyCode)) {
                    return DggJsonUtils.getJsonString(-2, "验证码错误", null);
                }
            }

            // 暂时不想动

            if (list == null || list.isEmpty()) {
                result = DggJsonUtils.getJsonString(-2, "用户不存在", null);
            } else {
                /*
                 * //非注册用户不能登录 if (list.get(0).getType() != null &&
                 * list.get(0).getType()!=0){ return
                 * result=JsonUtils.getJsonString(-2,"登录失败，非法登录",null); }
                 */
                if (!new DESPlus(PTConst.PWD_KEY).encrypt(user.getLoginPwd()).equals(list.get(0).getLoginPwd())) {
                    result = DggJsonUtils.getJsonString(-2, "密码错误", null);
                } else {
                    // 密码正确
                    if (list.get(0).getEnabled() == 0 || !StatusConstant.ENABLE.equals(list.get(0).getStatus())) {
                        // 用户禁用，不能登录
                        Map<String, Object> Failmap = new HashMap<String, Object>();
                        Failmap.put("phoneNo", user.getPhoneNo());
                        Failmap.put("userId", list.get(0).getUserId());
                        // 删除验证码
                        redisService.getJedisCluster().del(token + PTConst.VERIFY_SMS);
                        redisService.getJedisCluster().get(token + PTConst.VERIFY_IMG);
                        return DggJsonUtils.getJsonString(-2, "登录失败，用户已经禁用", Failmap);
                    } else {
                        // 登录成功
                        JSONObject json = JSONObject.parseObject(DggJsonUtils.getJsonString(0, "成功", null));
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("phoneNo", DggStringUtils.encryptionNumber(user.getPhoneNo()));
                        map.put("userId", list.get(0).getUserId());
                        map.put("isMember", list.get(0).getIsMember());
                        map.put("type", list.get(0).getType());
                        map.put("isInner", list.get(0).getIsInner());
                        map.put("memberExpirationdate", list.get(0).getMemberExpirationdate());
                        json.put("data", map);
                        result = json.toJSONString();
                        token = list.get(0).getUserId(); //userId
                        redisService.getJedisCluster().set(token, user.getPhoneNo());
                        // 删除验证码
                        redisService.getJedisCluster().del(token + PTConst.VERIFY_SMS);
                        redisService.getJedisCluster().get(token + PTConst.VERIFY_IMG);
                        log.info("-------------------------------------->" + token);
                        if (user.getTime() == 0)
                            redisService.getJedisCluster().expire(token, redisTokenExpire); //1小时
                        else
                            redisService.getJedisCluster().expire(token, redisTokenExpireDay * user.getTime()); //几天
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = DggJsonUtils.getJsonString(-3, "失败", null);
        }
        return result;
    }


    // 后台用户登录
    @Override
    public String SysUserlogin(UserDto user, String token) {
        // TODO Auto-generated method stub
        log.info("token:" + token);
        String result = null;
        String code = user.getImageVerifyCode();
        if (DggStringUtils.isNullOrEmpty(user.getUsername())) {
            return DggJsonUtils.getJsonString(-2, "登录名不能为空", null);
        }

        if (DggStringUtils.isNullOrEmpty(user.getLoginPwd())) {
            return DggJsonUtils.getJsonString(-2, "登录密码不能为空", null);
        }

        if (DggStringUtils.isNullOrEmpty(code)) {
            return DggJsonUtils.getJsonString(-2, "验证码不能为空", null);
        }
        try {
            // JedisCluster cluster = RedisFactory.getJedisCluster();
            // //获取redis连接
            // JedisPool cluster = RedisFactory.getJedisPool();
            // Map<String, String> configMap =
            // ResourceUtils.getResource(PTConst.USER_CACHE).getMap();
            // //读取redis配置文件
            String verifyCode = redisService.getJedisCluster().get(token + PTConst.VERIFY_IMG);
            if (DggStringUtils.isNullOrEmpty(verifyCode)) {
                return DggJsonUtils.getJsonString(-2, "请获取验证码", null);
            }
            if (!code.equals(verifyCode)) {
                return result = DggJsonUtils.getJsonString(-2, "验证码错误", null);
            }
            User u = new User();
            u.setUsername(user.getUsername());
            List<User> list = userMapper.findUserByName(u);
            if (list == null || list.size() == 0) {
                return result = DggJsonUtils.getJsonString(-2, "用户不存在", null);
            } else {
                // 非后台用户不能登录
                if (list.get(0).getType() != 1) {
                    return result = DggJsonUtils.getJsonString(-2, "登录失败，非法登录", null);
                }
                if (!new DESPlus(PTConst.PWD_KEY).encrypt(user.getLoginPwd()).equals(list.get(0).getLoginPwd())) {
                    result = DggJsonUtils.getJsonString(-2, "密码错误", null);
                } else {
                    // 用户禁用
                    if (list.get(0).getEnabled() == 0 || !StatusConstant.ENABLE.equals(list.get(0).getStatus())) {
                        Map<String, Object> loginFail = new HashMap<>();
                        loginFail.put("userId", list.get(0).getUserId());
                        loginFail.put("username", list.get(0).getUsername());
                        return result = DggJsonUtils.getJsonString(-2, "登录失败，用户已禁用", loginFail);
                    } else {
                        // 登录成功
                        // 返回userid，username,employeeNo,remark
                        Map<String, Object> loginMsg = new HashMap<>();
                        loginMsg.put("userId", list.get(0).getUserId());
                        loginMsg.put("username", list.get(0).getUsername());
                        loginMsg.put("employeeNo", list.get(0).getEmployeeNo());
                        loginMsg.put("remark", list.get(0).getRemark());
                        result = DggJsonUtils.getJsonString(0, "登录成功", loginMsg);
                    }
                }
            }
            redisService.getJedisCluster().set(token, user.getUsername());
            redisService.getJedisCluster().expire(token, redisTokenExpire);
            // cluster.expire(token,
            // Integer.parseInt(configMap.get(PTConst.USER_TIMEOUT))); //设置超时时间
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = DggJsonUtils.getJsonString(-3, "失败", null);
        }
        return result;
    }

    @Override
    public String logout(String token) {
        // TODO Auto-generated method stub
        String result = null;
        if (DggStringUtils.isNullOrEmpty(token)) {
            return DggJsonUtils.getJsonString(-2, "token不能为空", null);
        }
        try {
            JedisCluster cluster = redisService.getJedisCluster();
            cluster.del(token);
            result = DggJsonUtils.getJsonString(0, "成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            result = DggJsonUtils.getJsonString(-3, "失败", null);
        }
        return result;
    }

    @Override
    public String gettoken() {
        // TODO Auto-generated method stub
        String token = UUID.randomUUID().toString(); // 使用uuid作为token值
        return token;
        /*String result = null;
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("token", token);
            result = JsonUtils.getJsonString(0, "成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonUtils.getJsonString(-3, "失败", null);
        }
        return result;*/
    }

    /**
     * gene01,未用到 后台添加用户
     *
     * @param user
     * @return
     */
    @Override
    public String addUser(User user) {
        String result = null;
        // 判断用户名是否存在
        List<User> users = userMapper.findUserByName(user);
        if (users != null && users.size() > 0) {
            // 存在
            result = DggJsonUtils.getJsonString(-2, "请求失败，用户名已经存在", null);
        } else {
            try {
                user.setUserId(PrimaryKeyUtils.getId());
                int i = userMapper.insertUser(user);
                if (i == 1) {
                    // 返回成功,返回用户id
                    Map<String, Object> data = new HashMap<>();
                    data.put("userId", user.getUserId());
                    result = DggJsonUtils.getJsonString(0, "成功", data);
                } else {
                    // 数据库插入失败
                    result = DggJsonUtils.getJsonString(-2, "添加用户失败", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result = DggJsonUtils.getJsonString(-3, "请求失败", null);
            }
        }
        return result;
    }

    /**
     * gene01 查询用户所有后台用户
     *
     * @param userSysListDto
     * @return
     */
    @Override
    public Map<String, Object> getAllSysUser(UserSysListDto userSysListDto) {
        Map<String, Object> info = new HashMap<>();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("startCount", userSysListDto.getStartCount() - 1);
        int count = userSysListDto.getEndCount() - userSysListDto.getStartCount();
        condition.put("endCount", PTConst.PAGE_SIZE);
        condition.put("employeeNo", userSysListDto.getEmployeeNo());
        condition.put("username", userSysListDto.getUsername());
        condition.put("status", userSysListDto.getStatus());
        // 调用
        List<UserSysListReturnDto> users = userMapper.findAllSysUser(condition);

        // 需要条数
        if (users.size() >= count) {
            info.put("sysUserList", users.subList(0, count));
        } else {
            info.put("sysUserList", users);
        }
        // Integer allCount = userMapper.findAllCount(condition);

        info.put("totalCount", users.size());
        info.put("startCount", userSysListDto.getStartCount());
        info.put("endCount", userSysListDto.getStartCount() + count);
        return info;
    }

    /**
     * gene01 修改用户登录密码
     *
     * @param userDto
     * @return
     */
    @Override
    public int updateSysUserPwd(UserDto userDto) {
        HashMap params = new HashMap();
        params.put("id", userDto.getUserId());
        try {
            // 判断用户类型
            User user = userMapper.selectByPrimaryKey(params);
            if (user != null && user.getType() == 1) {
                if (user.getUseErpPwd() == 1) {
                    // erp用户，不允许修改
                    return -1;
                }
                if (!user.getLoginPwd().equals(new DESPlus(PTConst.PWD_KEY).encrypt(userDto.getOldLoginPwd()))) {
                    // 旧密码数据错误
                    return -3;
                }
                // 新密码和旧密码不能相同
                if (userDto.getOldLoginPwd().equals(userDto.getLoginPwd())) {
                    return -4;
                }
                // 非epr用户，旧密码数据正确，密码需要加密
                if (user.getUseErpPwd() == 0) {
                    user.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(userDto.getLoginPwd()));
                    userMapper.updateUser(user);
                }
            } else {
                // 后台用户不存在
                return -2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 发生异常，失败
            return 0;
        }
        return 1;
    }

    /**
     * gene02 管理员修改用户密码
     *
     * @param userDto
     * @return
     */
    @Override
    public int updateUserPwdBySys(UserDto userDto) {
        HashMap params = new HashMap();
        params.put("id", userDto.getUserId());
        try {
            // 判断用户类型
            User user = userMapper.selectByPrimaryKey(params);
            if (user != null && user.getType() == 1) {
                if (user.getUseErpPwd() == 1) {
                    // erp用户，不允许修改
                    return -1;
                }
                // 非epr用户，旧密码数据正确，密码需要加密
                if (user.getUseErpPwd() == 0) {
                    user.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(userDto.getLoginPwd()));
                    userMapper.updateUser(user);
                }
            } else {
                // 后台用户不存在
                return -2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 发生异常，失败
            return 0;
        }
        return 1;
    }

    /**
     * gene01 管理员启用/禁用后台用户
     *
     * @param userDto
     * @return
     */
    @Override
    public Map<String, Object> updateUserStatus(UserDto userDto) {
        Map<String, Object> result = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", userDto.getUserId());
        // 判断用户是否后台用户
        User user1 = userMapper.selectByPrimaryKey(params);
        try {

            if (user1.getType() == 1) {
                User user = new User();
                user.setUserId(userDto.getUserId());
                user.setEnabled(userDto.getStatus());
                // 调用修改
                userMapper.updateUser(user);
                result.put("result", 1);
                result.put("employeeNo", user1.getEmployeeNo());
            } else {
                result.put("result", 2);
                result.put("employeeNo", user1.getEmployeeNo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("result", 0);
            result.put("employeeNo", user1.getEmployeeNo());
        }
        return result;
    }

    /**
     * 查询所有注册用户
     *
     * @param userRegisterListDto
     * @return
     */
    @Override
    public Map<String, Object> getAllRejsterUsers(UserRegisterListDto userRegisterListDto) {
        Map<String, Object> info = new HashMap<>();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("startCount", userRegisterListDto.getStartCount() - 1);
        int count = userRegisterListDto.getEndCount() - userRegisterListDto.getStartCount() + 1;
        // 开始条数为负数
        condition.put("endCount", PTConst.PAGE_SIZE);
        if (!DggStringUtils.isNullOrEmpty(userRegisterListDto.getKeyWord()))
            condition.put("keyWord", "%" + userRegisterListDto.getKeyWord() + "%");
        // 调用
        List<UserRegisterListDto> users = userMapper.findAllRegisterUsers(condition);

        // 需要条数
        // int
        // count=userRegisterListDto.getEndCount()-userRegisterListDto.getStartCount();
        if (users != null && users.size() > count) {
            info.put("sysUserList", users.subList(0, count));
        } else {
            info.put("sysUserList", users);
        }
        // info.put("sysUserList",users);
        info.put("totalCount", users.size());
        info.put("startCount", userRegisterListDto.getStartCount());
        info.put("endCount", userRegisterListDto.getStartCount() + count);
        return info;
    }

    /**
     * gene02 查看详情
     *
     * @param userDto
     * @return
     */
    @Override
    public Map<Object, Object> getUserDetilById(UserDto userDto) {
        // 查询结果
        Map<Object, Object> userDetil = userMapper.selectUserDetilById(userDto.getUserId());
        // 保证查询的字段返回
        if (userDetil.get("username") == null) {
            userDetil.put("username", null);
        }
        if (userDetil.get("remark") == null) {
            userDetil.put("remark", null);
        }
        if (userDetil.get("email") == null) {
            userDetil.put("email", null);
        }
        if (userDetil.get("employeeNo") == null) {
            userDetil.put("employeeNo", null);
        }
        if (userDetil.get("userPosition") == null) {
            userDetil.put("userPosition", null);
        }
        if (userDetil.get("userCompany") == null) {
            userDetil.put("userCompany", null);
        }
        if (userDetil.get("mobilePhoneNumber") == null) {
            userDetil.put("mobilePhoneNumber", null);
        }
        if (userDetil.get("userPosition") == null) {
            userDetil.put("userPosition", null);
        }
        if (userDetil.get("area") == null) {
            userDetil.put("area", null);
        }
        if (userDetil.get("userSex") == null)
            userDetil.put("userSex", null);

        if (userDetil.get("deptName") == null)
            userDetil.put("deptName", null);

        Map<Object, Object> obj = new HashMap<Object, Object>();
        obj.put("data", userDetil);
        // JSONObject jsonObject=new JSONObject();
        // jsonObject.put("data",userDetil);
        // System.out.println("查询数据："+jsonObject.toJSONString());
        return obj;
    }

    /**
     * gene 云办公ap登录
     *
     * @param userLoginDto
     * @return
     */
    @Override
    public Map<String, Object> appLogin(UserLoginDto userLoginDto) {
        Map<String, Object> loginInfo = null;
        try {
            // 验证用户名,只允许erp用户
            User user = new User();
            user.setUsername(userLoginDto.getUsername());
            List<User> usersByName = userMapper.findUserByName(user);
            if (usersByName == null || usersByName.size() == 0 || usersByName.get(0).getUseErpPwd() == null
                    || usersByName.get(0).getType() != 1) {
                return DggJsonUtils.getResponseForMap(-2, "用户名或者密码错误", null);
            } else {
                // 验证密码
                if (!new DESPlus(PTConst.PWD_KEY).encrypt(userLoginDto.getLoginPwd()).equals(usersByName.get(0).getLoginPwd())) {
                    return DggJsonUtils.getResponseForMap(-2, "用户名或者密码错误", null);
                } else {
                    // 登录成功
                    loginInfo = new HashMap<>();
                    // JedisCluster jedisCluster =
                    // RedisFactory.getJedisCluster();
                    // token，手动生成
                    String token = UUID.randomUUID().toString();
                    loginInfo.put("token", token);
                    loginInfo.put("username", usersByName.get(0).getUsername());
                    loginInfo.put("trueName", usersByName.get(0).getRemark());
                    loginInfo.put("userId", usersByName.get(0).getUserId());
                    log.info("==>" + usersByName.get(0).getUsername() + "登录......");
                    loginInfo.put("employeeNo", usersByName.get(0).getEmployeeNo());
                    // 存入redis
                    redisService.getJedisCluster().set(token, usersByName.get(0).getUsername());
                    redisService.getJedisCluster().expire(token, redisTokenExpireApp);// 时间30分钟
                    log.info("==>token:" + token);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return DggJsonUtils.getResponseForMap(0, "登录成功", loginInfo);
    }

    /**
     * gene app端登出
     *
     * @param token
     * @return 1, 成功。0，异常，失败
     */
    @Override
    public int appLogout(String token) {
        // 删除token
        try {
            // JedisCluster jedisCluster = RedisFactory.getJedisCluster();
            String username = redisService.getJedisCluster().get(token);
            redisService.getJedisCluster().del(token);
            log.info("==>" + username + "注销......");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * gene01 管理员启用/禁用注册用户
     *
     * @param userDto
     * @return
     */
    @Override
    public Map<String, Object> updateRejsterUserStatus(UserDto userDto) {
        Map<String, Object> result = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", userDto.getUserId());
        // 判断用户是否后台用户
        User user1 = userMapper.selectByPrimaryKey(params);
        try {
            if (user1.getType() == 0) {
                User user = new User();
                user.setUserId(userDto.getUserId());
                user.setEnabled(userDto.getStatus());
                // 调用修改
                userMapper.updateUser(user);
                result.put("result", 1);
                result.put("employeeNo", user1.getEmployeeNo());
            } else {
                result.put("result", 2);
                result.put("employeeNo", user1.getEmployeeNo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", 0);
            result.put("employeeNo", user1.getEmployeeNo());
        }
        return result;
    }

    @Override
    public List<User> findUser(User u) {
        // TODO Auto-generated method stub
        return userMapper.findUserByName(u);
    }

    @Override
    public void updateUserByName(User u) {
        // TODO Auto-generated method stub
        userMapper.updateUserByName(u);
    }

    @Override
    public User findEndThousand(Map map) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int findCount(Map map) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<User> searchPage(Map map) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String crmlogin(String username, String password, String token) {

        try {
            // JedisCluster cluster = RedisFactory.getJedisCluster();
            // //获取redis连接
            // JedisPool cluster = RedisFactory.getJedisPool();

            User u = new User();
            u.setUsername(username);
            List<User> list = userMapper.findUserByName(u);
            if (list == null || list.size() == 0) {
                return DggJsonUtils.getJsonString(-2, "用户不存在", null);
            } else {
                /*
                 * //非注册用户不能登录 if (list.get(0).getType() != null &&
                 * list.get(0).getType()!=0){ return
                 * result=JsonUtils.getJsonString(-2,"登录失败，非法登录",null); }
                 */
                if (!password.equals(list.get(0).getLoginPwd())) {
                    return DggJsonUtils.getJsonString(-2, "密码错误", null);
                } else {
                    // 密码正确
                    if (list.get(0).getEnabled() == 0 || !StatusConstant.ENABLE.equals(list.get(0).getStatus())) {
                        // 用户禁用，不能登录
                        Map<String, Object> Failmap = new HashMap<String, Object>();
                        Failmap.put("phoneNo", username);
                        Failmap.put("userId", list.get(0).getUserId());
                        return DggJsonUtils.getJsonString(-2, "登录失败，用户已经禁用", Failmap);
                    } else {
                        // 登录成功
                        JSONObject json = JSONObject.parseObject(DggJsonUtils.getJsonString(0, "成功", null));
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("phoneNo", username);
                        map.put("userId", list.get(0).getUserId());
                        json.put("data", map);
                        redisService.getJedisCluster().set(token, username);
                        log.info("-------------------------------------->" + token);
                        redisService.getJedisCluster().expire(token, redisTokenExpire);
                        return json.toJSONString();
                    }
                }
            }
            // cluster.expire(token,
            // Integer.parseInt(configMap.get(PTConst.USER_TIMEOUT))); //设置超时时间
        } catch (Exception e) {
            e.printStackTrace();
            return DggJsonUtils.getJsonString(-3, "失败", null);
        }
    }

    @Override
    public void upateUserMemberExpired() {
        userMapper.updateAndfindUserMemberExpired();
    }

    @Override
    public User findUserByKey(String key) {
        return this.userMapper.findUserByKey(key);
    }

    /**
     * 创建用户key
     *
     * @return
     */
    public String createUserKey() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    @Override
    @Transactional
    public String createUserKey(String userId) {
//        Assert.hasText(userId, "用户ID不能为空！");
//        Assert.isTrue(redisService.getJedisCluster().exists(userId), "登录过期，请重新登录！");
//        User curUser = this.userMapper.findUserById(userId);
//        Assert.notNull(curUser, "未查询到用户");
        String key = createUserKey();
//        Integer num = this.userMapper.countUserByKey(key);
//        Assert.notNull(num, "用户KEY验证失败,请重试！");
//        Assert.isTrue(num.intValue() == 0, "用户KEY验证失败,请重试！");
//        curUser.setKey(key);
//        curUser.setLastUpdate(new Date());
//        this.userMapper.updateUser(curUser);
//        // 创建记录
//        UserKeyRecord userKeyRecord = new UserKeyRecord();
//        userKeyRecord.setId(KeyWorker.nextId());
//        userKeyRecord.setKey(key);
//        userKeyRecord.setUserId(userId);
//        userKeyRecord.setCreateTime(new Date());
//        this.userKeyRecordDao.save(userKeyRecord);
        return key;
    }


    public User findUserById(String id) {
        return this.userMapper.findUserById(id);
    }

    @Override
    @Transactional
    public void addRegistUser(User user) {
        Assert.notNull(user, "用户信息不能为空！");
        Assert.hasText(user.getUsername(), "用户名不能为空！");
        List<User> users = this.userMapper.findUserByName(user);
        Assert.isTrue(users.size() <= 1, "此用户名已被注册！");

        if (users.size() == 1 && users.get(0) != null) {
            User sysUser = users.get(0);
            Assert.isTrue(sysUser.getRegist() == null || sysUser.getRegist().intValue() != 1, "此用户名已被注册！");
            user.setUserId(sysUser.getUserId());
            this.userMapper.updateUser(user);
            return;
        }

        user.setUserId(DggStringUtils.isEmpty(user.getUserId()) ? PrimaryKeyUtils.getId() : user.getUserId());
        this.userMapper.insertUser(user);

    }


}
