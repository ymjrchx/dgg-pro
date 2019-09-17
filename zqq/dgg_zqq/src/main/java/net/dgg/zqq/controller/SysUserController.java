package net.dgg.zqq.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.dto.user.UserDto;
import net.dgg.zqq.dto.user.UserRegisterListDto;
import net.dgg.zqq.dto.user.UserSysListDto;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.framework.redis.RedisFactory;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.framework.security.DESPlus;
import net.dgg.zqq.framework.sms.PageUtil;
import net.dgg.zqq.services.UserService;
import net.dgg.zqq.utils.JsonUtils;
import net.dgg.zqq.utils.PrimaryKeyUtils;
import net.dgg.zqq.utils.StringUtils;
import net.fblock.web.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.dgg.iboss.base.util.StringUtils.encryptionNumber;


@Controller
public class SysUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "/registuser/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        String re = userService.login(user, token);
        return new Gson().fromJson(re, Map.class);
    }

    /**
     * 验证用户是否登录
     *
     * @return
     */
    @RequestMapping(value = "/user/isExsits", method = RequestMethod.GET)
    @ResponseBody
    public String isExsits() {
        return JsonUtils.getJsonString(0, "成功", null);
    }

    /**
     * 用户登出
     *
     * @return
     */
    @RequestMapping(value = "/registuser/logout", method = RequestMethod.GET)
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        return userService.logout(token);
    }

    /**
     * gene01
     * 后台用户登录
     *
     * @return
     */
    @RequestMapping(value = "/sysuser/login", method = RequestMethod.POST)
    @ResponseBody
    public String sysUserlogin(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        return userService.SysUserlogin(user, token);
    }

    /**
     * gene01
     * 后台用户登出
     *
     * @return
     */
    @RequestMapping(value = "/sysuser/logout", method = RequestMethod.GET)
    @ResponseBody
    public String sysUserlogout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        return userService.logout(token);
    }

    /**
     * 获取token
     *
     * @return
     */
    @RequestMapping(value = "/session/gettoken", method = RequestMethod.POST)
    @ResponseBody
    public Object gettoken() {
        return this.getSuccessResponse(userService.gettoken());
    }

    /**
     * 注册用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/registuser/regist", method = RequestMethod.POST)
    @ResponseBody
    public Map register(@RequestBody UserDto user, HttpServletRequest request) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        String result = JsonUtils.getJsonString(0, "成功", null);
        String username = user.getPhoneNo();
        String passwd = user.getLoginPwd();
        if (StringUtils.isNullOrEmpty(username)) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "登录名不能为空", null));
        }
        if (StringUtils.isNullOrEmpty(passwd)) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "登录密码不能为空", null));
        }

        if (StringUtils.isNullOrEmpty(user.getSmsVerifyCode())) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "验证码不能为空", null));
        }
        JedisCluster cluster = RedisFactory.getJedisCluster();
        String smsCode = cluster.get(token + PTConst.VERIFY_SMS);
        if (smsCode == null) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "请获取验证码", null));
        }
        String code = smsCode.split("&")[0];
        String phoneNo = smsCode.split("&")[1];
        if (StringUtils.isNullOrEmpty(code)) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "请获取验证码", null));
        }

        if (!user.getSmsVerifyCode().equals(code)) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "验证码错误", null));
        }
        if (!phoneNo.equals(username)) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "验证码与手机号不匹配", null));
        }
        try {
            User u = new User();
            u.setUsername(user.getPhoneNo());
            List<User> list = userService.findUser(u);
            if (list != null && (list.size() >= 2 || (list.size() == 1 && list.get(0) != null && list.get(0).getRegist() != null && list.get(0).getRegist().intValue() == 1))) {
                return this.jsonStringToMap(JsonUtils.getJsonString(-2, "用户已存在", null));
            }
            u.setRegist(1);
            u.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(user.getLoginPwd()));
            u.setCreatetime(new Date());
            u.setEnabled(1);
            u.setUseErpPwd(1);
            u.setType(0);
            u.setLastUpdate(new Date());
            String userId = PrimaryKeyUtils.getId();
            u.setUserId(userId);
            u.setFlag(1);
            u.setStatus(StatusConstant.ENABLE);
            userService.addRegistUser(u);
            // 删除验证码
            RedisUtils.del(token + PTConst.VERIFY_SMS);

            JSONObject json = JSONObject.parseObject(result);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", u.getUserId());
            map.put("phoneNo", username);
            json.put("data", map);
            return this.jsonStringToMap(json.toJSONString());
        } catch (Exception e) {
            result = JsonUtils.getJsonString(-3, "系统异常", null);
        }
        return this.jsonStringToMap(result);
    }

    /**
     * 注册用户找回密码
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/registuser/forgetpwd", method = RequestMethod.POST)
    @ResponseBody
    public Map resetPasswd(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        if (StringUtils.isNullOrEmpty(user.getPhoneNo())) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "手机号码不能为空", null));
        }

        if (StringUtils.isNullOrEmpty(user.getSmsVerifyCode())) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "验证码不能为空", null));
        }
        JedisCluster cluster = RedisFactory.getJedisCluster();
        String code = cluster.get(token + "sms");
        if (StringUtils.isNullOrEmpty(code)) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "请获取验证码", null));
        }

        if (!user.getSmsVerifyCode().equals(code.split("&")[0])) {
            return this.jsonStringToMap(JsonUtils.getJsonString(-2, "验证码错误", null));
        }

        String result = JsonUtils.getJsonString(0, "成功", null);
        User u = new User();
        u.setUsername(user.getPhoneNo());
        u.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(user.getNewLoginPwd()));
        u.setLastUpdate(new Date());
        try {
            List<User> list = userService.findUser(u);
            if (list == null || list.size() == 0)
                return this.jsonStringToMap(JsonUtils.getJsonString(-2, "用户不存在", null));
            userService.updateUserByName(u);
            // 删除验证码
            RedisUtils.del(token + PTConst.VERIFY_SMS);
        } catch (Exception e) {
            result = JsonUtils.getJsonString(-3, "系统异常", null);
        }
        return this.jsonStringToMap(result);
    }

    /**
     * 注册用户修改个人信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/registuser/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyUser(@RequestBody UserDto user, HttpServletRequest request, HttpServletResponse response) {
        String result = JsonUtils.getJsonString(0, "成功", null);
        try {
            User u = new User();
            String token = request.getHeader(PTConst.USER_TOKEN);
            //JedisCluster cluster  = RedisFactory.getJedisCluster();
            String username = RedisUtils.get(token);
            log.info("************************" + token);
            u.setUsername(username);
            List<User> list = userService.findUser(u);//查询当前登录用户
            //判断用户是否为注册用户
            if (list.get(0).getType() != 0) {
                //非注册用户，不允许任何修改
                return JsonUtils.getJsonString(-2, "失败，非法修改。用户非注册用户", null);
            } else {
                if (!StringUtils.isNullOrEmpty(user.getOldLoginPwd())) {
                    u.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(user.getNewLoginPwd()));
                    //u.setUsername(username);
                    if (!new DESPlus(PTConst.PWD_KEY).encrypt(user.getOldLoginPwd()).equals(list.get(0).getLoginPwd())) {
                        return JsonUtils.getJsonString(-2, "原密码输入不正确", null);
                    }
                    userService.updateUserByName(u);
                } else {
                    u.setNickname(user.getNickname());
                    u.setLastUpdate(new Date());
                    u.setUsername(username);//个人信息不能修改
                    u.setEmail(user.getEmail());
                    u.setUserCompany(user.getUserCompany());
                    u.setUserPosition(user.getUserPosition());
                    u.setIndustry(user.getIndustry());
                    userService.updateUserByName(u);
                }
                // 删除验证码
                RedisUtils.del(token + PTConst.VERIFY_SMS);
            }

        } catch (Exception e) {
            result = JsonUtils.getJsonString(-3, "系统异常", null);
        }
        return result;
    }

    /**
     * 管理员增加后台用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/sysuser/create", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody User user) {
        String result = JsonUtils.getJsonString(0, "成功", null);
        //判断用户名和密码是否为空
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            return result = JsonUtils.getJsonString(-2, "请输入用户名", null);
        }
        if (StringUtils.isNullOrEmpty(user.getLoginPwd())) {
            return result = JsonUtils.getJsonString(-2, "请输入密码", null);
        }
        List<User> list = userService.findUser(user);
        if (list != null && list.size() > 0) {
            return JsonUtils.getJsonString(-2, "用户已存在", null);
        }
        try {
            user.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(user.getLoginPwd()));
            user.setCreatetime(new Date());
            user.setLastUpdate(new Date());
            user.setUseErpPwd(0);
            user.setEnabled(1);
            user.setType(1);
            result = userService.addUser(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = JsonUtils.getJsonString(-3, "系统异常", null);
        }
        return result;
    }

    /**
     * gene01
     * 查询后台用户列表
     *
     * @param userSysListDto
     * @return
     */
    @RequestMapping(value = "/sysuser/search", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchAllUser(UserSysListDto userSysListDto) {
        Map<String, Object> result = new HashMap<>();
        //非法数据判断
        Map<String, Object> serarchContition = PageUtil.serarchContition(userSysListDto.getStartCount(), userSysListDto.getEndCount());
        if (serarchContition != null) {
            return serarchContition;
        }
        //调用
        Map<String, Object> info = userService.getAllSysUser(userSysListDto);
        List<UserSysListDto> users = (List<UserSysListDto>) info.get("sysUserList");
        if (users != null && users.size() > 0) {
            result.put("code", 0);
            result.put("msg", "请求成功");
            result.put("data", info);
        } else {
            result.put("code", -2);
            result.put("msg", "未查询到数据");
            result.put("data", info);
        }
        return result;
    }

    /**
     * gene01
     * 修改后台用户登录密码
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/sysuser/modify", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> modify(@RequestBody UserDto userDto) {
        int result = userService.updateSysUserPwd(userDto);
        Map<String, Object> map = new HashMap<>();
        switch (result) {
            case 0:
                map.put("code", -2);
                map.put("msg", "请求失败");
                break;
            case 1:
                map.put("code", 0);
                map.put("msg", "修改成功");
                break;
            case -1:
                map.put("code", -2);
                map.put("msg", "此用户不允许修改密码");
                break;
            case -2:
                map.put("code", -2);
                map.put("msg", "修改失败，用户异常,不存在");
                break;
            case -3:
                map.put("code", -2);
                map.put("msg", "旧密码输入错误");
                break;
            case -4:
                map.put("code", -2);
                map.put("msg", "新密码和旧密码不能相同");
                break;
            default:
                map.put("code", -2);
                map.put("msg", "请求失败");
                break;
        }
        return map;
    }


    /**
     * gene02
     * 管理员修改用户密码
     *
     * @return
     */
    @RequestMapping(value = "/sysUser/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePwd(@RequestBody UserDto userDto) {
        int result = userService.updateUserPwdBySys(userDto);
        Map<String, Object> map = new HashMap<>();
        switch (result) {
            case 0:
                map.put("code", -2);
                map.put("msg", "请求失败");
                break;
            case 1:
                map.put("code", 0);
                map.put("msg", "修改成功");
                break;
            case -1:
                map.put("code", -2);
                map.put("msg", "此用户不允许修改密码");
                break;
            case -2:
                map.put("code", -2);
                map.put("msg", "修改失败，用户异常,不存在");
                break;
            default:
                map.put("code", -2);
                map.put("msg", "请求失败");
                break;
        }
        return map;
    }

    /**
     * gene02
     * 查看用户详情
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/sysUser/getUserDetil", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserDetil(@RequestBody UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        try {
            //查询数据
            Map<Object, Object> userDetil = userService.getUserDetilById(userDto);
            //如果有数据
            if (userDetil != null && userDetil.size() > 0) {
                //判断是否有工号（有工号表示erp用户，不允许修改）
                if (userDetil.get("employeeNo") == null) {
                    //非erp用户，允许修改
                    map.put("code", 0);
                    map.put("msg", "请求成功");
                    map.put("modifyStatu", 0);  //是否允许修改密码标识，0，允许修改。1.不允许修改
                    map.put("data", userDetil);
                } else {
                    //erp用户，不允许修改
                    map.put("code", 0);
                    map.put("msg", "请求成功");
                    map.put("modifyStatu", 1);  //是否允许修改标识，0，允许修改。1.不允许修改
                    map.put("data", userDetil);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            map.put("code", -2);
            map.put("msg", "请求失败");
        }
        return map;
    }

    /**
     * gene01
     * 启用/禁用后台用户接口
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/sysuser/enable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> enable(@RequestBody UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        //判断状态
        if (userDto.getStatus() == null || (userDto.getStatus() != 0 && userDto.getStatus() != 1)) {
            map.put("code", -2);
            map.put("msg", "失败，状态非法");
            return map;
        }

        Map<String, Object> map1 = userService.updateUserStatus(userDto);
        Integer result = (Integer) map1.get("result");
        if (result == 1) {
            //成功
            map.put("code", 0);
            map.put("msg", "状态修改成功");
            map.put("employeeNo", map1.get("employeeNo"));
        } else if (result == 0) {
            map.put("code", -2);
            map.put("msg", "状态修改失败");
            map.put("employeeNo", map1.get("employeeNo"));
        } else if (result == 2) {
            map.put("code", -2);
            map.put("msg", "修改失败，非法修改");
            map.put("employeeNo", map1.get("employeeNo"));
        } else {
            map.put("code", -2);
            map.put("msg", "请求异常");
            map.put("employeeNo", map1.get("employeeNo"));
        }
        return map;
    }

    /**
     * gene01
     * 启用/禁用注册用户接口
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/registuser/enable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> enableRejster(@RequestBody UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        //判断状态
        if (userDto.getStatus() == null || (userDto.getStatus() != 0 && userDto.getStatus() != 1)) {
            map.put("code", -2);
            map.put("msg", "失败，状态非法");
            return map;
        }
        Map<String, Object> map1 = userService.updateRejsterUserStatus(userDto);
        Integer result = (Integer) map1.get("result");
        if (result == 1) {
            //成功
            map.put("code", 0);
            map.put("msg", "状态修改成功");
            map.put("employeeNo", map1.get("employeeNo"));
        } else if (result == 0) {
            map.put("code", -2);
            map.put("msg", "状态修改失败");
            map.put("employeeNo", map1.get("employeeNo"));
        } else if (result == 2) {
            map.put("code", -2);
            map.put("msg", "修改失败，非法修改");
            map.put("employeeNo", map1.get("employeeNo"));
        } else {
            map.put("code", -2);
            map.put("msg", "请求异常");
            map.put("employeeNo", map1.get("employeeNo"));
        }
        return map;
    }


    /**
     * gene01
     * 查询注册用户列表
     *
     * @param userRegisterListDto
     * @return
     */
    @RequestMapping(value = "/regtistuser/search", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchAllRegtisUser(UserRegisterListDto userRegisterListDto) {
        //前端非法数据判断
        Map<String, Object> serarchContition = PageUtil.serarchContition(userRegisterListDto.getStartCount(), userRegisterListDto.getEndCount());
        if (serarchContition != null) {
            return serarchContition;
        }
        Map<String, Object> result = new HashMap<>();
        //调用
        Map<String, Object> info = userService.getAllRejsterUsers(userRegisterListDto);
        List<UserRegisterListDto> users = (List<UserRegisterListDto>) info.get("sysUserList");
        if (users != null && users.size() > 0) {
            result.put("code", 0);
            result.put("msg", "请求成功");
            result.put("data", info);
        } else {
            result.put("code", -2);
            result.put("msg", "未查询到数据");
            result.put("data", info);
        }
        return result;
    }

    /**
     * 查询当前登录人信息详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "regtistuser/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PTConst.USER_TOKEN);
        String username = RedisUtils.get(token);
        User userDto = new User();
        userDto.setUsername(username);
        List<User> user = userService.findUser(userDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (null != user && user.size() > 0) {
            resultMap.put("userInfo", user.get(0));
        }
        return resultMap;
    }

    @RequestMapping(value = "regist/loginByCode", method = RequestMethod.POST)
    @ResponseBody
    public Object verifyCodeLogin(@RequestBody UserDto dto, HttpServletRequest request) {
        if (StringUtils.isNullOrEmpty(dto.getPhoneNo())) {
            /*return JsonUtils.getJsonString(-2, "手机号码不能为空！", null);*/
            return this.getFailResponse("手机号码不能为空");
        }
        if (StringUtils.isNullOrEmpty(dto.getSmsVerifyCode())) {
            /* return JsonUtils.getJsonString(-2, "验证码不能为空！", null);*/
            return this.getFailResponse("验证码不能为空!");
        }
        String tokenOld = request.getHeader(PTConst.USER_TOKEN);
        if (StringUtils.isEmpty(tokenOld)) {
            return this.getFailResponse("token 获取失败!");
        }
        if (!RedisUtils.exists(tokenOld + PTConst.VERIFY_SMS)) {
            /* return JsonUtils.getJsonString(-2, "验证码失效！", null);*/
            return this.getFailResponse("请获取验证码！");
        }
        String code = RedisUtils.get(tokenOld + PTConst.VERIFY_SMS);
        String[] codes = code.split("&");
        if (codes.length > 0) {
            code = codes[0];
            if (!dto.getPhoneNo().equals(codes[1])) {
                /* return JsonUtils.getJsonString(-2, "手机号与验证码不符！", null);*/
                return this.getFailResponse("验证码错误！");
            }
        } else {
            /* return JsonUtils.getJsonString(-2, "验证码失效！", null);*/
            return this.getFailResponse("验证码错误！");
        }
        if (!dto.getSmsVerifyCode().equals(code)) {
            /* return JsonUtils.getJsonString(-2, "验证码错误！", null);*/
            return this.getFailResponse("验证码错误！");
        }

        User u = new User();
        u.setUsername(dto.getPhoneNo());
        List<User> list = userService.findUser(u);
        if (!list.isEmpty() && !StatusConstant.ENABLE.equals(list.get(0).getStatus())) {
            return this.getFailResponse("该用户已被禁用！");
        }

        Map map = new HashMap<>();
        map.put("phoneNo", encryptionNumber(dto.getPhoneNo()));
        String token = "";
        if (list == null || list.size() == 0) {
            u.setCreatetime(new Date());
            u.setEnabled(1);
            u.setUseErpPwd(1);
            u.setType(0);
            u.setMoney(new BigDecimal(0));
            u.setLastUpdate(new Date());
            String userId = PrimaryKeyUtils.getId();
            u.setUserId(userId);
            u.setRegist(0);
            u.setFlag(1);
            u.setStatus(StatusConstant.ENABLE);
            userService.addUser(u);
            map.put("userId", u.getUserId());
            map.put("isMember", 0);
            map.put("nickname", u.getNickname());
            token = u.getUserId();
        } else {
            map.put("userId", list.get(0).getUserId());
            map.put("isMember", list.get(0).getIsMember());
            map.put("nickname", list.get(0).getNickname());
            token = list.get(0).getUserId();
        }
        // 删除验证码
        RedisUtils.del(tokenOld + PTConst.VERIFY_SMS);

        RedisUtils.set(token, dto.getPhoneNo());
        if (dto.getTime() == 0) {
            RedisUtils.expire(token, Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE))); //1小时
        } else {
            RedisUtils.expire(token, Integer.parseInt(RedisUtils.getRedisPriperty(PTConst.TOKEN_EXPIRE_DAY)) * dto.getTime()); //几天
        }
        /* return JsonUtils.getJsonString(0, "成功！", map);*/
        return this.getSuccessResponse(map);
    }

    @RequestMapping(value = "/crm/login", method = RequestMethod.GET)
    @ResponseBody
    public String crmlogin(String param, HttpServletRequest request) {
        if (StringUtils.isNullOrEmpty(param)) {
            return JsonUtils.getJsonString(-2, "参数为空", null);
        }
        String userInfo = "";
        try {
            userInfo = new DESPlus(PTConst.PWD_KEY).decrypt(param);
        } catch (Exception e) {
            return JsonUtils.getJsonString(-2, "参数错误", null);
        }
        if (userInfo.split(":").length != 2) {
            return JsonUtils.getJsonString(-2, "参数错误", null);
        }

        String username = userInfo.split(":")[0];
        String password = userInfo.split(":")[1];
        String token = request.getHeader(PTConst.USER_TOKEN);
        return userService.crmlogin(username, password, token);

    }


    private Map jsonStringToMap(String str) {
        return new Gson().fromJson(str, Map.class);
    }
}
