package net.dgg.bigdata.manager.condition.controller;

import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.TreeBook;
import net.dgg.bigdata.common.entity.condition.Action;
import net.dgg.bigdata.common.entity.condition.Input;
import net.dgg.bigdata.manager.condition.service.ConditionService;
import net.dgg.bigdata.manager.treebook.service.TreeBookService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/12 14:11
 * @Description: 分类管理
 */
@Controller
@RequestMapping("/condition/")
public class ConditionController extends DggBaseController {

    @Autowired
    private TreeBookService treeBookService;

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 到首页
     *
     * @return
     */
    @GetMapping(value = "/index")
    public String index() {
        return "/condition/index";
    }

    /**
     * 获取actiontype数据
     *
     * @return
     */
    @GetMapping(value = "getActionType.do")
    @ResponseBody
    public DggRestResponse<List<TreeBook>> getActionType() {
        try {
            Map parmMap = new HashMap();
            parmMap.put(ConditionConstant.PCODE, ConditionConstant.ACTION_TYPE_PCODE);
            List<TreeBook> treeBooks = treeBookService.getTreeBooksByCondition(parmMap);
            return this.getSuccessResponse(treeBooks);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 获取actiontype数据
     *
     * @return
     */
    @GetMapping(value = "getInputType.do")
    @ResponseBody
    public DggRestResponse<List<TreeBook>> getInputType() {
        try {
            Map parmMap = new HashMap();
            parmMap.put(ConditionConstant.PCODE, ConditionConstant.INPUT_TYPE_PCODE);
            List<TreeBook> treeBooks = treeBookService.getTreeBooksByCondition(parmMap);
            return this.getSuccessResponse(treeBooks);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 获取数据来源的数据
     *
     * @return
     */
    @GetMapping(value = "getOptionsFrom.do")
    @ResponseBody
    public DggRestResponse<List<TreeBook>> getOptionsFrom() {
        try {
            Map parmMap = new HashMap();
            parmMap.put(ConditionConstant.PCODE, ConditionConstant.INPUT_VALUES_CODE);
            List<TreeBook> treeBooks = treeBookService.getTreeBooksByCondition(parmMap);
            parmMap.put(ConditionConstant.PCODE, ConditionConstant.INPUT_VALUES_CODE1);
            List<TreeBook> pTreeBooks = treeBookService.getTreeBooksByCondition(parmMap);
            treeBooks.addAll(pTreeBooks);
            return this.getSuccessResponse(treeBooks);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 为分类增加对应数据信息
     *
     * @return
     */
    @PostMapping(value = "addCondition.do")
    @ResponseBody
    public DggRestResponse addCondition(Action action, Input input, String[] labelName, String[] value) {
        try {
            if (action.getActionType() == null) {
                return this.getFailResponse("请选择action type");
            }
            conditionService.addCondition(action, input, labelName, value);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }

    }

    /**
     * 根据分类id获取对应的数据
     *
     * @param treeBookId
     * @return
     */
    @GetMapping(value = "getCondition.do")
    @ResponseBody
    public DggRestResponse getCondition(Long treeBookId) {
        try {
            Map<String, Object> condition = conditionService.getCondition(treeBookId);
            return this.getSuccessResponse(condition);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 修改分类对应的数据
     *
     * @return
     */
    @PostMapping(value = "modifyCondition.do")
    @ResponseBody
    public DggRestResponse modifyCondition(Action action, Input input, String[] labelName, String[] value) {
        try {
            conditionService.modifyCondition(action, input, labelName, value);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 删除分类对应的数据
     *
     * @param treebookId
     * @return
     */
    @PostMapping(value = "deleteCondition.do")
    @ResponseBody
    public DggRestResponse deleteCondition(Long treebookId) {
        try {
            conditionService.deleteCondition(treebookId);
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }

    /**
     * 删除前台redis分类信息缓存
     * @return
     */
    @GetMapping(value = "deleteRedisCache.do")
    @ResponseBody
    public DggRestResponse delete() {
        try {
            if (redisTemplate.hasKey(ConditionConstant.CONFIGS_REDIS_KEY)) {
                redisTemplate.delete(ConditionConstant.CONFIGS_REDIS_KEY);
            }
            return this.getSuccessResponse(null);
        } catch (Exception e) {
            return this.getFailResponse(e.getMessage());
        }
    }


}
