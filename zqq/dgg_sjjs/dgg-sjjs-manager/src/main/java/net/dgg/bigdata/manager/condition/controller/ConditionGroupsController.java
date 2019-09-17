package net.dgg.bigdata.manager.condition.controller;

import com.alibaba.fastjson.JSON;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.condition.ConditionGroups;
import net.dgg.bigdata.common.entity.condition.IndustryModel;
import net.dgg.bigdata.manager.condition.service.ConditionGroupsService;
import net.dgg.bigdata.manager.condition.service.IndustryModelService;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import net.dgg.core.utils.common.DggKeyWorker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/14 15:22
 * @Description: 行业模板
 */
@Controller
@RequestMapping("/conditiongroup/")
public class ConditionGroupsController extends DggBaseController {

    @Resource
    private IndustryModelService industryModelService;

    @Resource
    private ConditionGroupsService conditionGroupsService;

    @Resource
    private SessionManager sessionManager;

    /**
     * 到首页
     *
     * @return
     */
    @GetMapping(value = "index")
    public String index(HttpServletRequest request) {
        try {
            Map map = new HashMap<>();
            map.put("type",1);
            List<IndustryModel> query = industryModelService.query(map);
            request.setAttribute("industrys", query);
            List<ConditionGroups> groups = conditionGroupsService.query(map);
            request.setAttribute("groups", JSON.toJSON(groups));
        } catch (Exception e) {

        }
        return "/conditiongroup/index";
    }

    /**
     * 增加
     *
     * @param conditionGroups
     * @return
     */
    @PostMapping("add.do")
    @ResponseBody
    public DggRestResponse add(ConditionGroups conditionGroups) {
        try {
            conditionGroupsService.add(conditionGroups);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }


    /**
     * 修改
     *
     * @param conditionGroups
     * @return
     */
    @PostMapping("modify.do")
    @ResponseBody
    public DggRestResponse modify(ConditionGroups conditionGroups) {

        try {
            conditionGroupsService.modify(conditionGroups);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param conditionGroups
     * @return
     */
    @PostMapping("delete.do")
    @ResponseBody
    public DggRestResponse delete(ConditionGroups conditionGroups) {
        try {
            conditionGroupsService.delete(conditionGroups.getId());
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }

    }

    /**
     * 更新缓存
     *
     * @return
     */
    @GetMapping("deleteCache.do")
    @ResponseBody
    public DggRestResponse deleteCache() {
        try {
            conditionGroupsService.deleteCache();
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }

    }

    /**
     * 更新企业搜索缓存
     *
     * @return
     */
    @GetMapping("deleteCompanyCache.do")
    @ResponseBody
    public DggRestResponse deleteCompanyCache() {
        try {
            conditionGroupsService.deleteCompanyCache();
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }

    }

}
