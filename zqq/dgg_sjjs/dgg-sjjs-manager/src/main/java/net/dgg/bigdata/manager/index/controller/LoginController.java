package net.dgg.bigdata.manager.index.controller;

import net.dgg.bigdata.manager.index.IndexException;
import net.dgg.bigdata.manager.index.service.LoginService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
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
public class LoginController extends DggBaseController {

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
	public DggRestResponse<Map> doLogin(@RequestBody Map params) {
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
	public DggRestResponse doLogout() {
		try {
			loginService.logout();
		}catch (IndexException ie){
			logger.warn(ie.toString());
			return this.getFailResponse("退出异常");
		}

		return this.getSuccessResponse("退出成功");
	}
}
