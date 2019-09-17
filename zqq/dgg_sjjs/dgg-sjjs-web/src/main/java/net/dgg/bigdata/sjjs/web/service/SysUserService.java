package net.dgg.bigdata.sjjs.web.service;

import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.entity.dto.UserDto;
import net.dgg.core.message.common.exception.DggMessageExeption;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface SysUserService {
    SysUser findByUsername(String phoneNo);

    void regist(String token, UserDto userDto) throws DggMessageExeption;

    Map login(UserDto userDto, HttpServletRequest request);

    void changePwd(SysUser user, String oldPwd, String newPwd);

    void changePhone(SysUser sysUser, String oldPhone, String newPhone);

    void update(SysUser sysUser, String nickname, Integer sex);

    void logout(SysUser sysUser);

    void userBind(UserDto userDto, SysUser sysUser);

    SysUser findByIbossCode(String code);

    String gettoken();

    void setPwd(SysUser user, String pwd);

    Map getUser(SysUser user);

    Boolean checkBind(SysUser user);

    List<String> getAllUserId();

    List<SysUser> getAll();

    void changeLegenPerson(String name, SysUser user);
}
