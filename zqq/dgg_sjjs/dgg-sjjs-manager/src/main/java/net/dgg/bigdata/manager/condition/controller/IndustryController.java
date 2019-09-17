package net.dgg.bigdata.manager.condition.controller;

import net.dgg.bigdata.common.entity.condition.IndustryModel;
import net.dgg.bigdata.manager.condition.service.IndustryModelService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/14 11:51
 * @Description: 行业分类管理
 */
@Controller
@RequestMapping("/industry/")
public class IndustryController extends DggBaseController {


    @Resource
    private IndustryModelService industryModelService;


    /**
     * 到首页
     *
     * @return
     */
    @GetMapping(value = "index")
    public String index(HttpServletRequest request) {
        Map map = new HashMap<>();
        List<IndustryModel> query = industryModelService.query(map);
        request.setAttribute("industrys", query);
        return "/industrymodel/index";
    }

    @PostMapping("add.do")
    @ResponseBody
    public DggRestResponse addIndustryModel(String applicableIndustryName, Integer sort) {
        try {
            if (StringUtils.isEmpty(applicableIndustryName)) {
                return this.getFailResponse("行业分类名称不能为空");
            }
            industryModelService.add(applicableIndustryName, sort);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    @PostMapping("modify.do")
    @ResponseBody
    public DggRestResponse modify(IndustryModel industryModel) {
        try {
            if (industryModel.getId() == null) {
                return this.getFailResponse("id不能为空");
            }
            if (StringUtils.isEmpty(industryModel.getApplicableIndustryName())) {
                return this.getFailResponse("行业分类名称不能为空");
            }
            industryModelService.modify(industryModel);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }

    }

    /**
     * 删除行业
     * @param id
     * @return
     */
    @PostMapping("delete.do")
    @ResponseBody
    public DggRestResponse delete(Long id) {
        try {
            if (id == null) {
                return this.getFailResponse("请选择要删除的行业");
            }
            int code = industryModelService.deleteIndustryModel(id);
            if (code == 0) {
                return this.getSuccessResponse(null);
            } else {
                return this.getFailResponse("该行业分类存在行业模板，请先删除行业模板");
            }
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }


    }


}
