package net.dgg.bigdata.manager.index.controller;

import net.dgg.bigdata.common.entity.UserEntity;
import net.dgg.bigdata.manager.menu.service.MenuService;
import net.dgg.bigdata.manager.session.CommonSession;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.bigdata.manager.user.service.UserManager;
import net.dgg.core.utils.DggJsonUtils;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wu on 2018-02-22.
 */
@Controller
@RequestMapping("/")
public class IndexController extends DggBaseController {

    @Resource
    private MenuService menuService;

    @Resource
    private SessionManager sessionManager;

    @Resource
    private UserManager userManager;

    @Value("${site.name}")
    private String siteName;

    /**
     * 进入系统首页
     *
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("siteName", siteName);
        //获取当前登录用户
        CommonSession session = sessionManager.getCurrentSession();
        if (session != null) {
            Long userId = session.getValue("userId", Long.class);
            UserEntity user = userManager.findUserById(userId);
            modelAndView.addObject("user", user);
            modelAndView.addObject("menus", menuService.findMenuTree(userId, 1l));
            modelAndView.setViewName("index");
            return modelAndView;
        } else {
            return new ModelAndView("login");
        }
    }

    /**
     * 根据父节点查询子菜单
     *
     * @param pId
     * @return
     */
    @RequestMapping("find_sub_menu_tree")
    @ResponseBody
    public DggRestResponse findSubMenu(long pId) {
        Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
        return this.getSuccessResponse(menuService.findMenuTree(userId, pId));
    }


    /**
     * 无权限页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("403")
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response) {
        // 判断是否ajax请求
        if (!(request.getHeader("accept").indexOf("application/json") > -1
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))
        ) {
            // 如果不是ajax，JSP格式返回
            // 为安全起见，只有业务异常我们对前端可见，否则否则统一归为系统异常
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            map.put("msg", "没有权限");
            map.put("code", 1);
            return new ModelAndView("/error/403", map);

        } else {
            // 如果是ajax请求，JSON格式返回
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("success", false);
                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
                map.put("msg", "没有权限");
                map.put("code", 1);
                writer.write(DggJsonUtils.obj2Json(map));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
