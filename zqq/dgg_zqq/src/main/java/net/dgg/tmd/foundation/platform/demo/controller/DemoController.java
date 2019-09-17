package net.dgg.tmd.foundation.platform.demo.controller;

import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nature
 * @create 2018-02-05 10:13
 */
@Controller
@RequestMapping("demo")
public class DemoController extends BaseController {

	@RequestMapping("index")
	public ModelAndView index(Map<String,Object> map){
//		map=new HashMap<String,Object>();
//		map.put("name", "HelloController");
//		return "index";

		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}

	@RequestMapping("show_success")
	@ResponseBody
	public RestResponse<Map<String,String>> showSuccess(){
		Map<String,String> result=new HashMap<>();
		result.put("msg","请求成功返回数据，该实体可以为任意实体，也可以为null");
		return this.getSuccessResponse(result);
	}

	@RequestMapping("show_fail")
	@ResponseBody
	public RestResponse<Map<String,String>> showFail(){
		Map<String,String> result=new HashMap<>();
		return this.getFailResponse("演示错误信息");
	}

	@RequestMapping("build_response")
	@ResponseBody
	public RestResponse<Map<String,String>> showBuildResponse(){
		Map<String,String> result=new HashMap<>();
		result.put("msg","演示构建返回值，该实体可以为任意实体，也可以为null");
		return this.buildResponse(RestResponse.FAIL_CODE,"演示构建返回值",result);
	}
}
