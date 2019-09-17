package net.dgg.bigdata.sjjs.web.service;

import net.dgg.bigdata.sjjs.web.entity.User;
import net.dgg.bigdata.sjjs.web.entity.dto.UserDto;
import net.dgg.bigdata.sjjs.web.entity.dto.UserLoginDto;
import net.dgg.bigdata.sjjs.web.entity.dto.UserRegisterListDto;
import net.dgg.bigdata.sjjs.web.entity.dto.UserSysListDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wu on 2017/7/31.
 */
@Service
public interface UserService extends BaseService<User> {

    String login(UserDto user, String token);

    String logout(String token);

    String gettoken();

    String addUser(User user);

    Map<String, Object> getAllSysUser(UserSysListDto userSysListDto);

    //后台用户登录
    String SysUserlogin(UserDto user, String token);

    //修改用户登录密码
    int updateSysUserPwd(UserDto userDto);

    //管理员修改用户密码
    int updateUserPwdBySys(UserDto userDto);

    //管理员启用禁用用户状态0，禁用。1，启用
    Map<String, Object> updateUserStatus(UserDto userDto);

    //查询所有注册用户
    Map<String, Object> getAllRejsterUsers(UserRegisterListDto userRegisterListDto);

    //管理员启用禁用注册用户
    Map<String, Object> updateRejsterUserStatus(UserDto userDto);


    List<User> findUser(User u);

    void updateUserByName(User u);

    //查询用户详情
    Map<Object, Object> getUserDetilById(UserDto userDto);

    //app端登录接口(云办公)
    Map<String, Object> appLogin(UserLoginDto userLoginDto);

    //app端登出接口（云办公）
    int appLogout(String token);

    String crmlogin(String username, String password, String token);

    //获取到期的会员信息,并且修改
    void upateUserMemberExpired();

    /**
     * 查找用户
     *
     * @param key
     * @return
     */
    User findUserByKey(String key);

    /**
     * 创建用户key
     *
     * @return
     */
    String createUserKey(String userId);

    /**
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 添加注册用户
     *
     * @param user
     */
    void addRegistUser(User user);

}
