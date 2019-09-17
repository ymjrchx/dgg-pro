package net.dgg.tmd.foundation.platform.user.service;

import net.dgg.tmd.foundation.platform.common.http.HttpUtils;
import net.dgg.tmd.foundation.platform.common.util.DateUtils;
import net.dgg.tmd.foundation.platform.role.service.RoleService;
import net.dgg.tmd.foundation.platform.user.dao.InitTableDAO;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserDTO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.tmd.foundation.platform.user.exception.UserModuleException;
import net.fblock.cachemodule.AbstractMongoCacheModule;
import net.fblock.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Service
public class UserManager extends AbstractMongoCacheModule<UserEntity> {
    @Autowired
    UserRecorderDAO userRecorderDAO;

    @Autowired
    InitTableDAO initTableDAO;

    @Autowired
    RoleService roleService;

    @Value("${usercenter.url}/${usercenter.getuser.url}")
    private String USERCENTER_USER_APIURL;

    @Value("${usercenter.apptoken}")
    private String USERCENTER_APPTOKEN;

    @Value("${table_schema}")
    private String TABLESCHEMA;

    /**
     * 闲置过期时间间隔，单位秒
     */
    private long userExpireInterval = 60 * 5;

    public UserManager() {
        this.setDataSecondDefault(userExpireInterval);
        this.setCacheCollectionKey("net.dgg.tmd.foundation.platform.user.service.UserManager");
    }

    /**
     * 判断用户数据表是否存在，如果不存在则自动创建
     */
    public void hasUserModuleTable() {
        Map<String, String> map = new HashMap<>();
        map.put("tableName", "user_info");
        map.put("tableSchema", TABLESCHEMA);
        if (initTableDAO.hasTable(map) == 0) {
            initTableDAO.createUserModuleTables();
        }
    }

    /**
     * 通过用户ID从缓存读取用户实体
     *
     * @param userId
     * @return
     */
    public UserEntity findUserById(long userId) {
        return super.get("userId:" + Long.toString(userId));
    }

    /**
     * 通过用户LoginName从缓存读取用户实体
     *
     * @param userLoginName
     * @return
     */
    public UserEntity findUserByLoginName(String userLoginName) {
        if (null == userLoginName) {
            return null;
        } else {
            return super.get("userLoginName:" + userLoginName);
        }
    }

    /**
     * 通过关键字从数据库中查找用户实体
     *
     * @param keyWords 可以使用如下属性进行查找:
     *                 "locked":参数 0代表正常；1代表锁定；2代表离职；默认为0正常
     *                 "login_name":登录名 为员工个人工号
     * @return
     */
    public List<UserEntity> queryUser(Map keyWords) {
        if (null == keyWords || keyWords.isEmpty()) {
            throw new UserModuleException("用户查询关键字不允许为空！");
        } else {
            List<UserEntity> userList = new ArrayList<>();
            userList = userRecorderDAO.findUserEntityListByKeyWords(keyWords);
            return userList;
        }
    }

    /**
     * 从数据库加载用户信息
     *
     * @param field
     * @return
     */
    @Override
    protected UserEntity loadValue(String field) {
        UserEntity userEntity = new UserEntity();
        if (null == field || "".equals(field)) {
            return null;
        } else {
            String[] params = field.split(":");
            if ("userId".equals(params[0])) {
                userEntity = userRecorderDAO.findUserEntityByUserId(Long.valueOf(params[1]));
                return userEntity;
            } else if ("userLoginName".equals(params[0])) {
                userEntity = userRecorderDAO.findUserEntityByUserLoginName(params[1]);
                return userEntity;
            } else {
                return null;
            }
        }
    }

    /**
     * 通过部门ID查询用户DTO实体
     *
     * @param keyWords
     * @return
     */
    public List<UserDTO> findUserDTOByOrgIdWithPage(Map keyWords) {
        return userRecorderDAO.findUserDTOByOrgIdWithPage(keyWords);
    }

    /**
     * 统计指定部门下用户的个数
     *
     * @param orgId
     * @return
     */
    public int findUserTotalByOrgId(long orgId) {
        return userRecorderDAO.findUserTotalByOrgId(orgId);
    }

    /**
     * 根据模糊关键字查询用户名称及工号并返回总数量
     *
     * @param keyWords
     * @return
     */
    public int findUserTotalByKeyWords(Map keyWords) {
        return userRecorderDAO.findUserTotalByKeyWords(keyWords);
    }

    /**
     * 根据条件搜索用户DTO并返回
     *
     * @param keyWords
     * @return
     */
    public List<UserDTO> findUserDTOByKeyWordsWithPage(Map keyWords) {
        List<UserDTO> userList = userRecorderDAO.findUserDTOByKeyWordsWithPage(keyWords);
        for (UserDTO userDTO : userList) {
            userDTO.setRoleName(roleService.findOwnedRoleNameByUserId(userDTO.getId()));
        }
        return userList;
    }

    /**
     * 从用户中心服务器加载并保存用户实体
     *
     * @param userId
     * @return
     */
    public boolean loadAndSaveUserFromServer(long userId) {
        boolean flag = false;
        HashMap responseData = JsonUtil.json2Obj(HttpUtils.sendPost(USERCENTER_USER_APIURL, USERCENTER_APPTOKEN, "userId=" + userId), HashMap.class);
        HashMap userFromServer = JsonUtil.json2Obj(responseData.get("dataobject").toString(), HashMap.class);
        UserEntity userEntity = new UserEntity();
        try {
            userEntity.setUserId(Long.parseLong(String.valueOf(userFromServer.get("id"))));
            if (null != String.valueOf(userFromServer.get("locked"))) {
                userEntity.setLocked(Integer.parseInt(String.valueOf(userFromServer.get("locked"))));
            }
            userEntity.setLoginName(String.valueOf(userFromServer.get("loginName")));
            userEntity.setLoginPwd(String.valueOf(userFromServer.get("loginPwd")));
            userEntity.setRealName(String.valueOf(userFromServer.get("name")));
            userEntity.setPhone(String.valueOf(userFromServer.get("phone")));
            if (null != String.valueOf(userFromServer.get("orgId"))) {
                userEntity.setOrgId(Long.parseLong(String.valueOf(userFromServer.get("orgId"))));
            }
            userEntity.setSex(String.valueOf(userFromServer.get("sex")));
            userEntity.setDescription(String.valueOf(userFromServer.get("description")));
            userEntity.setWorkarea(String.valueOf(userFromServer.get("workArea")));
            userEntity.setEmail(String.valueOf(userFromServer.get("email")));
            userEntity.setDimissiontime(DateUtils.formatyyyyMMDDHHmmss(String.valueOf(userFromServer.get("dismissionTime"))));
            userEntity.setUpdatetime(new Date());
            userEntity.setEntrydate(DateUtils.formatyyyyMMDDHHmmss(String.valueOf(userFromServer.get("entryDate"))));
            if (null != String.valueOf(userFromServer.get("workAge"))) {
                userEntity.setWorkage(Integer.parseInt(String.valueOf(userFromServer.get("workAge"))));
            }
            if (null == userRecorderDAO.findUserEntityByUserId(userEntity.getUserId())) {
                userRecorderDAO.insertUserEntity(userEntity);
            } else {
                userRecorderDAO.updateUserEntity(userEntity);
            }
            super.clear("userId:" + userId);
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}
