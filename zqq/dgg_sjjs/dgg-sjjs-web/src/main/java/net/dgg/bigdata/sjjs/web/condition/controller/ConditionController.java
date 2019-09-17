package net.dgg.bigdata.sjjs.web.condition.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.dgg.bigdata.sjjs.web.condition.service.ConfigsService;
import net.dgg.bigdata.sjjs.web.condition.service.IndustryModelService;
import net.dgg.bigdata.sjjs.web.condition.dto.ConditionGroupDto;
import net.dgg.bigdata.sjjs.web.condition.dto.IndustryModelDto;
import net.dgg.bigdata.sjjs.web.condition.entity.Configs;
import net.dgg.bigdata.sjjs.web.condition.service.ConditionGroupsService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 10:32
 * @Description:
 */
@RestController
@RequestMapping("/condition/")
@Api(description = "高级搜索数据提供")
public class ConditionController extends DggBaseController {

    @Autowired
    private ConfigsService configsService;

    @Autowired
    private ConditionGroupsService conditionGroupsService;

    @Autowired
    private IndustryModelService industryModelService;


    /**
     * 获取分类信息
     *
     * @return
     */
    @GetMapping("configs.do")
    @ApiOperation(value = "获取分类信息")
    public DggRestResponse<Configs> getConfigs() {
        try {
            Configs configs = configsService.getConfigs();
            return this.getSuccessResponse(configs);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 获取历史条件组
     *
     * @return
     */
    @GetMapping("conditionGroups.do")
    @ApiOperation(value = "获取历史条件组")
    public DggRestResponse<List<ConditionGroupDto>> getConditionGroups(HttpServletRequest request) {
        try {
            List<ConditionGroupDto> conditionGroups = conditionGroupsService.getConditionGroups(request);
            return this.getSuccessResponse(conditionGroups);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }

    }


    /**
     * 增加历史条件组
     *
     * @return
     */
    @PostMapping("addConditionGroups.do")
    @ApiOperation(value = "增加历史条件组")
    public DggRestResponse addConditionGroups(String name, String filter, HttpServletRequest request) {
        try {
            ConditionGroupDto conditionGroupDto = new ConditionGroupDto();
            conditionGroupDto.setName(name);
            conditionGroupDto.setFilter(filter);
            conditionGroupsService.addConditionGroups(conditionGroupDto, request);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 修改历史条件组
     *
     * @return
     */
    @PostMapping("modifyConditionGroups.do")
    @ApiOperation(value = "修改历史条件组")
    public DggRestResponse modifyConditionGroups(String name, String filter, HttpServletRequest request) {
        try {
            ConditionGroupDto conditionGroupDto = new ConditionGroupDto();
            conditionGroupDto.setName(name);
            conditionGroupDto.setFilter(filter);
            conditionGroupsService.modifyConditionGroups(conditionGroupDto, request);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 增加条件组使用次数
     *
     * @param id 条件组id
     * @return
     */
    @PostMapping("modifyConditionGroupsCount.do")
    @ApiOperation(value = "增加条件组使用次数")
    public DggRestResponse modifyConditionGroupsCount(Long id, HttpServletRequest request) {
        try {
            conditionGroupsService.modifyConditionGroupsCounts(id, request);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 删除历史条件组
     *
     * @return
     */
    @PostMapping("deleteConditionGroups.do")
    @ApiOperation(value = "删除历史条件组")
    public DggRestResponse deleteConditionGroups(Long id, HttpServletRequest request) {
        try {
            conditionGroupsService.deleteConditionGroups(id, request);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }


    /**
     * 获取行业模版
     *
     * @return
     */
    @GetMapping("recommend.do")
    @ApiOperation(value = "获取行业模版")
    public DggRestResponse<List<IndustryModelDto>> getIndustry() {
        try {
            List<IndustryModelDto> industryModel = industryModelService.getRedisIndustryModel();
            return this.getSuccessResponse(industryModel);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }


}
