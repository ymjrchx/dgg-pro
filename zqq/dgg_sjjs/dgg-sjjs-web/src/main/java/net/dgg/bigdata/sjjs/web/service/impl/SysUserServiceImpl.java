package net.dgg.bigdata.sjjs.web.service.impl;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.constant.StatusConstant;
import net.dgg.bigdata.sjjs.web.dao.CompanyDao;
import net.dgg.bigdata.sjjs.web.dao.SysUserDao;
import net.dgg.bigdata.sjjs.web.entity.Company;
import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.entity.dto.UserDto;
import net.dgg.bigdata.sjjs.web.service.SendMsgService;
import net.dgg.bigdata.sjjs.web.service.SysUserService;
import net.dgg.core.message.common.exception.DggMessageExeption;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.utils.DESPlus;
import net.dgg.core.utils.common.DggKeyWorker;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: SysUserServiceImpl
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/11 10:54
 */

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Value("${jwt.config.key}")
    private String key;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private SendMsgService sendMsgService;

    /**
     * 根据用户名查找
     *
     * @param phoneNo
     * @return
     */
    @Override
    public SysUser findByUsername(String phoneNo) {
        Assert.hasText(phoneNo, "用户名不能为空");
        return sysUserDao.findByAttr("username", phoneNo);
    }

    /**
     * 用户注册
     *
     * @param token
     * @param userDto
     */
    @Override
    public void regist(String token, UserDto userDto) throws DggMessageExeption {
        //验证用户数据
        this.checkUserDto(userDto);
        //this.checkCompany(company);
        //获得redis中的图片验证码
        String imgKey = token.concat(PTConst.VERIFY_IMG);
        String imgCode = DggRedisService.get(imgKey);
        Assert.hasText(imgCode, "请获得图片验证码");
        Assert.isTrue(userDto.getImageVerifyCode().equals(imgCode), "图片验证码不正确");

        //获得redis中的短信验证码
        String smsKey = token.concat(PTConst.VERIFY_SMS);
        String smsCode = DggRedisService.get(smsKey);
        Assert.hasText(smsCode, "请获得短信验证码");
        String[] smsCodes = smsCode.split("&");
        //Assert.isTrue(smsCode.length() == 2, "请获得短信验证码");
        if (smsCodes[0] != null) {
            String smsCodeRedis = smsCodes[0];
            Assert.isTrue(userDto.getSmsVerifyCode().equals(smsCodeRedis), "短信验证码不正确");
        }
        if (smsCodes[1] != null) {
            String phoneNoRedis = smsCodes[1];
            Assert.isTrue(userDto.getPhoneNo().equals(phoneNoRedis), "验证码与手机号不匹配");
        }

        //判断数据库中是否存在该用户
        SysUser sysUser = sysUserDao.findByAttr("username", userDto.getPhoneNo());
        Assert.isTrue(sysUser == null, "该用户账号已经被注册");
        //没有注册过
        sysUser = new SysUser();
        sysUser.setEmail(userDto.getEmail());
        sysUser.setUserId(DggKeyWorker.nextId() + "");
        sysUser.setUsername(userDto.getPhoneNo());
        sysUser.setCreatetime(new Date());
        sysUser.setFlag(1);
        sysUser.setStatus(StatusConstant.ENABLE);
        sysUser.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(userDto.getLoginPwd()));
        //sysUser.setIsFirstLogin(0);
        //系统设置初始密码 并以短信的方式发给用户
        //String code = RandomStringUtils.randomAlphanumeric(6);
        //String code = RandomStringUtils.randomNumeric(6);

        //添加用户
        sysUserDao.save(sysUser);

        /*//发送短信
        StringBuffer smsContent = new StringBuffer();
        smsContent.append("【顶企客】" + "您的初始密码：").append(code);
        smsContent.append("，如非本人操作，请尽快处理。");
        sendMsgService.sendSmsMsg(smsContent.toString(), userDto.getPhoneNo());*/

        //添加公司
        /*company.setId(DggKeyWorker.nextId());
        company.setFlag(1);
        company.setStatus(StatusConstant.ENABLE);
        company.setIsAuth(1);
        company.setUserId(sysUser.getUserId());
        company.setCreateTime(new Date());
        companyDao.save(company);*/

    }

    /**
     * 用户登录
     *
     * @param userDto
     * @return
     */
    @Override
    public Map login(UserDto userDto, HttpServletRequest request) {
        //验证
        Assert.hasText(userDto.getPhoneNo(), "用户名不能为空");
        Assert.hasText(userDto.getLoginPwd(), "密码不能为空");
        Assert.notNull(userDto.getTime(), "登录多久时间不能为空");
        Assert.isTrue(userDto.getTime() == 0 || userDto.getTime() == 7, "按照规定传递登陆时间");

        //判断
        SysUser sysUser = sysUserDao.findByAttr("username", userDto.getPhoneNo());
        Assert.notNull(sysUser, "该号码还未注册");
        Assert.isTrue(new DESPlus(PTConst.PWD_KEY).encrypt(userDto.getLoginPwd()).equals(sysUser.getLoginPwd()), "用户密码错误");

        //登录成功
        //1.1判断是否是第一次登录
        sysUser.setIsFirstLogin(sysUser.getIsFirstLogin() == null ? 0 : sysUser.getIsFirstLogin());
        /*if (sysUser.getIsFirstLogin() == 0) {
            //第一次登陆
            sysUser.setIsFirstLogin(1);
            sysUserDao.update(sysUser);
        } else if (sysUser.getIsFirstLogin() != null && sysUser.getIsFirstLogin() == 1) {
            //不是第一次登陆
            sysUser.setIsFirstLogin(2);
            sysUserDao.update(sysUser);
        }*/

        //生成token
        String token = getToken(sysUser, userDto.getTime());
       /* Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        System.out.println(claims.getExpiration());*/
        //封装返回前段的数据
        Map<String, Object> mapResult = new HashMap<String, Object>();
        if (sysUser.getIbossCode() != null && sysUser.getIbossCode() != ""
                && sysUser.getLegenPerson() != null && sysUser.getLegenPerson() != ""
                && sysUser.getLegenDept() != null && sysUser.getLegenDept() != "") {
            mapResult.put("isBind", sysUser.getLegenPerson());
        } else {
            mapResult.put("isBind", 0);
        }
        mapResult.put("username", formatNumber(sysUser.getUsername(), 3, 6, "*"));
        //mapResult.put("isFirstLogin", sysUser.getIsFirstLogin());
        mapResult.put("loginToken", token);
        mapResult.put("userId", sysUser.getUserId());

        //向redis中存储一份
        DggRedisService.set(sysUser.getUserId(), token);

        return mapResult;
    }

    /**
     * 生成token
     *
     * @param sysUser
     * @param time
     * @return
     */
    private String getToken(SysUser sysUser, int time) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(sysUser.getUserId())
                .claim("roles", "user")
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);

        if (time == 7) {
            builder.setExpiration(new Date(nowMillis + 1000 * 60 * 60 * 24 * 7));
        } else {
            builder.setExpiration(new Date(nowMillis + 1000 * 60 * 60 * 2));
        }
        return builder.compact();
    }

    /**
     * 用户登出
     *
     * @param sysUser
     */
    @Override
    public void logout(SysUser sysUser) {
        Assert.notNull(sysUser, "请先登录");
        if (DggRedisService.exists(sysUser.getUserId())) {
            DggRedisService.del(sysUser.getUserId());
        }
    }

    /**
     * 绑定用户信息
     *
     * @param userDto
     * @param sysUser
     */
    @Override
    public void userBind(UserDto userDto, SysUser sysUser) {
        Assert.notNull(sysUser, "请先登录");
        Assert.hasText(userDto.getIbossCode(), "工号不能为空");
        Assert.hasText(userDto.getLegenPerson(), "名称不能为空");
        Assert.hasText(userDto.getLegenDept(), "部门不能为空");
        if (!userDto.getIbossCode().equals(sysUser.getIbossCode())) {
            Assert.isTrue(sysUserDao.findByAttr("ibossCode", userDto.getIbossCode()) == null, "该工号已经被绑定");
            sysUser.setIbossCode(userDto.getIbossCode());
        }
        sysUser.setLegenPerson(userDto.getLegenPerson());
        sysUser.setLegenDept(userDto.getLegenDept());
        sysUserDao.update(sysUser);
    }


    /**
     * 设置密码
     * @param user
     * @param pwd
     */
    @Override
    public void setPwd(SysUser user, String pwd) {
        Assert.notNull(user, "请先登录");
        Assert.hasText(pwd, "密码不能为空");
        user.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(pwd));
        sysUserDao.update(user);
    }

    /**
     * 获得当前用户基本信息
     *
     * @param user
     * @return
     */
    @Override
    public Map getUser(SysUser user) {
        Map map = new HashMap();
        map.put("username", this.formatNumber(user.getUsername(), 3, 6, "*"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("createtime", user.getCreatetime() != null ? formatter.format(user.getCreatetime()) : null);
        map.put("sex", user.getSex());
        map.put("legenPerson", user.getLegenPerson());
        map.put("legenDept", user.getLegenDept());
        map.put("ibossCode", user.getIbossCode());
        return map;
    }



    /**
     * 修改密码
     *
     * @param user
     * @param oldPwd
     * @param newPwd
     */
    @Override
    public void changePwd(SysUser user, String oldPwd, String newPwd) {
        Assert.notNull(user, "请先登录");
        Assert.hasText(oldPwd, "原密码不能为空");
        Assert.hasText(newPwd, "新密码不能为空");
        Assert.isTrue(new DESPlus(PTConst.PWD_KEY).encrypt(oldPwd).equals(user.getLoginPwd()), "原密码输入不正确");
        user.setLoginPwd(new DESPlus(PTConst.PWD_KEY).encrypt(newPwd));
        sysUserDao.update(user);
        if (DggRedisService.exists(user.getUserId())) {
            DggRedisService.del(user.getUserId());
        }
    }

    /**
     * 解绑手机
     *
     * @param sysUser
     * @param oldPhone
     * @param newPhone
     */
    @Override
    public void changePhone(SysUser sysUser, String oldPhone, String newPhone) {
        Assert.notNull(sysUser, "请先登录");
        Assert.isTrue(StringUtils.hasText(oldPhone) && oldPhone.equals(sysUser.getUsername()), "非法更改手机号码");
        Assert.hasText(newPhone, "新号码不能为空");
        sysUser.setUsername(newPhone);
        sysUserDao.update(sysUser);

        if (DggRedisService.exists(sysUser.getUserId())) {
            DggRedisService.del(sysUser.getUserId());
        }
    }

    /**
     * 修改昵称和性别
     *
     * @param sysUser
     * @param nickname
     * @param sex
     */
    @Override
    public void update(SysUser sysUser, String nickname, Integer sex) {
        Assert.notNull(sysUser, "请先登录");
        Assert.hasText(nickname, "昵称不能为空");
        Assert.notNull(sex, "性别不能为空");
        sysUser.setNickname(nickname);
        sysUser.setSex(sex);
        sysUserDao.update(sysUser);
    }

    /**
     * 判断是否有绑定过编号
     *
     * @param user
     * @return
     */
    @Override
    public Boolean checkBind(SysUser user) {
        Assert.notNull(user, "请先登录");
        if (user.getIbossCode() != null && user.getIbossCode() != ""
                && user.getLegenPerson() != null && user.getLegenPerson() != ""
                && user.getLegenDept() != null && user.getLegenDept() != "") {
            return true;
        }
        return false;
    }

    /**
     * 获得所有用户
     *
     * @return
     */
    @Override
    public List<String> getAllUserId() {
        List<String> list = new ArrayList<>();
        sysUserDao.query(null).stream().forEach(user -> add(user, list));
        return list;
    }

    /**
     * 获得所有用户
     *
     * @return
     */
    @Override
    public List<SysUser> getAll() {
        return sysUserDao.query(null);
    }

    /**
     * 修改姓名
     *
     * @param name
     * @param user
     */
    @Override
    public void changeLegenPerson(String name, SysUser user) {
        Assert.notNull(user, "请先登录");
        Assert.hasText(name, "姓名不能为空");
        user.setLegenPerson(name);
        sysUserDao.update(user);
    }

    private void add(SysUser user, List<String> list) {
        if (user.getLegenPerson() != null) {
            list.add(user.getUserId());
        }
    }


    /**
     * 根据用户编号查询用户
     *
     * @param code
     * @return
     */
    @Override
    public SysUser findByIbossCode(String code) {
        Assert.notNull(code, "编号不能为空");
        return sysUserDao.findByAttr("ibossCode", code);
    }

    @Override
    public String gettoken() {
        String token = UUID.randomUUID().toString(); // 使用uuid作为token值
        return token;
    }

    private void checkCompany(Company company) {
        Assert.hasText(company.getCompanyName(), "企业全称不能为空");
        Assert.hasText(company.getContactName(), "联系人名称不能为空");
        Assert.hasText(company.getContactEmail(), "邮件不能为空");
    }

    private void checkUserDto(UserDto userDto) {
        Assert.hasText(userDto.getPhoneNo(), "号码不能为空");
        Assert.hasText(userDto.getLoginPwd(), "密码不能为空");
        //Assert.hasText(userDto.getEmail(), "邮箱不能为空");
        Assert.hasText(userDto.getSmsVerifyCode(), "短信验证码不能为空");
        Assert.hasText(userDto.getImageVerifyCode(), "图片验证码不能为空");
    }

    private String formatNumber(String number, int start, int end, String format) {
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(number.substring(0, start));
        for (int i = start; i <= end; i++) {
            stringBuilder.append("*");
        }
        stringBuilder.append(number.substring(end + 1, number.length()));
        return stringBuilder.toString();
    }

}