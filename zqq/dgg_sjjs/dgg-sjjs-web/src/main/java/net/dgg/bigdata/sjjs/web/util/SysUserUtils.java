package net.dgg.bigdata.sjjs.web.util;

import net.dgg.bigdata.sjjs.web.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: SysuserUtils
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/25 16:08
 */
public class SysUserUtils {

    public static final String KEY = "user";

    /**
     * 获得当前登录人信息
     *
     * @param request
     * @return
     */
    public static SysUser getUser(HttpServletRequest request) {
        return request != null ? (SysUser) request.getAttribute(SysUserUtils.KEY) : null;
    }
}