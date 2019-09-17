package net.dgg.bigdata.manager.index.service;

import net.dgg.bigdata.common.entity.UserEntity;
import net.dgg.bigdata.manager.index.IndexException;
import net.dgg.bigdata.manager.index.util.DESPlus;
import net.dgg.bigdata.manager.session.CommonSession;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.bigdata.manager.user.service.UserManager;
import net.dgg.core.utils.DggValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录服务
 *
 * @author nature
 * @create 2018-02-26 14:41
 */
@Service
public class LoginService {

	private static final String LOGING_PWD_KEY = "DGGERP962540ADMIN";

	private Logger logger= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserManager userManager;

	@Autowired
	private SessionManager sessionManager;

	/**
	 *
	 * @param username
	 * @param loginPwd
	 * @return
	 */
	public Map<String,String> doLogin(String username, String loginPwd){

		String commoneExpMessage="用户名或者密码不正确";

		//用户名不可为空
		DggValidateUtil.strNotEmpty(username,IndexException.class,commoneExpMessage);
		//密码不可为空
		DggValidateUtil.strNotEmpty(loginPwd,IndexException.class,commoneExpMessage);

		//获取用户
		UserEntity userEntity=userManager.findUserByLoginName(username);

		//验证用户状态
		//用户是否存在
		DggValidateUtil.notNull(userEntity, IndexException.class,commoneExpMessage);
		DggValidateUtil.isTrue(userEntity.getLocked()==0, IndexException.class,"用户当前状态不可登录");
		DggValidateUtil.isTrue(
				userEntity.getDimissiontime()==null||userEntity.getDimissiontime().after(Calendar.getInstance().getTime())
				, IndexException.class
				,"用户已离职");
		DggValidateUtil.isTrue(userEntity.getOrgId()>0, IndexException.class,"用户不属于任何一个组织架构，不可登陆");
		//验证用户身份
		//用户名密码是否匹配
		DggValidateUtil.isTrue(this.verifyUserLoginPwd(userEntity.getLoginPwd(),loginPwd), IndexException.class,commoneExpMessage);

		//处理会话状态
		CommonSession session= sessionManager.getCurrentSession();
		session.putValue("userId",userEntity.getUserId());
		//返回登录信息
		Map resopnseData=new HashMap();
		resopnseData.put("userId",userEntity.getUserId());
		resopnseData.put("loginName",userEntity.getLoginName());
		resopnseData.put("phoneNo",userEntity.getPhone());
		resopnseData.put("email",userEntity.getEmail());
		return resopnseData;
	}

	/**
	 * 登出
	 */
	public void logout(){
		sessionManager.clear(String.valueOf(sessionManager.getCurrentSessionId()));
	}

	/**
	 * 校验用户密码是否匹配
	 * @param pwd 存储的密码密文
	 * @param input 用户的输入
	 * @return
	 */
	private boolean verifyUserLoginPwd(String pwd,String input){
		if(StringUtils.isEmpty(pwd)){
			logger.warn("用户密码为空");
			return false;
		}
		return pwd.equals(new DESPlus(LoginService.LOGING_PWD_KEY).encrypt(input));
	}

}
