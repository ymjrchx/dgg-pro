package net.dgg.tmd.foundation.platform.index.controller;

import net.dgg.tmd.foundation.platform.index.IndexException;
import net.dgg.tmd.foundation.platform.index.service.LoginService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by wu on 2018-02-22.
 */
@Controller
@RequestMapping("login")
public class LoginController extends BaseController {

	private Logger logger= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginService loginService;

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	/**
	 * 处理用户登录请求
	 *
	 * @return
	 */
	@RequestMapping("do_login")
	@ResponseBody
	public RestResponse<Map> doLogin(@RequestBody Map params) {
		Map result=null;
		try {
			result = loginService.doLogin(
					String.valueOf(params.get("username"))
					, String.valueOf(params.get("loginPwd"))
			);
		}catch (IndexException ie){
			logger.warn(ie.toString());
			return this.getFailResponse(ie.getMessage());
		}

		return this.getSuccessResponse(result);
	}
	/**
	 * 处理用户登录请求
	 *
	 * @return
	 */
	@RequestMapping("do_logout")
	@ResponseBody
	public RestResponse doLogout() {
		try {
			loginService.logout();
		}catch (IndexException ie){
			logger.warn(ie.toString());
			return this.getFailResponse("退出异常");
		}

		return this.getSuccessResponse("退出成功");
	}
}
