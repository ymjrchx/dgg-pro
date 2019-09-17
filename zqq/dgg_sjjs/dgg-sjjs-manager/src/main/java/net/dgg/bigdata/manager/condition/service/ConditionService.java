package net.dgg.bigdata.manager.condition.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.dgg.bigdata.common.entity.condition.Action;
import net.dgg.bigdata.common.entity.condition.Input;
import net.dgg.bigdata.common.entity.condition.Option;
import net.dgg.bigdata.manager.session.CommonSession;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.utils.common.DggKeyWorker;
import net.dgg.core.utils.exception.DggBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/13 14:40
 * @Description:
 */
@Service
public class ConditionService {

    @Autowired
    private ActionService actionService;
    @Autowired
    private InputService inputService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private SessionManager sessionManager;


    /**
     * 增加分类数据
     *
     * @param action
     * @param input
     * @param labelName
     * @param value
     */
    @Transactional
    public void addCondition(Action action, Input input, String[] labelName, String[] value) {

        //添加之前查询是否已经添加了
        List<Action> actionList = actionService.getActionByTreeBookId(action.getTreebookId());
        if (!actionList.isEmpty()) {
            throw new DggBaseException("已经添加过了");
        }

        //获取当前登录的用户的id
        Date date = new Date();
        Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
        //增加action
        Long actionID = DggKeyWorker.nextId();
        action.setId(actionID);
        action.setCreaterId(userId);
        action.setCreateTime(date);
        actionService.save(action);


        //增加input
        if (input.getInputType() != null) {
            input.setId(DggKeyWorker.nextId());
            input.setCreaterId(userId);
            input.setCreateTime(date);
            inputService.save(input);
        }

        //增加option
        int len = 0;
        if (labelName != null && value != null)
            len = labelName.length < value.length ? labelName.length : value.length;
        Option option = null;
        if (len > 0) {
            option = new Option();
            for (int i = 0; i < len; i++) {
                option.setId(DggKeyWorker.nextId());
                option.setActionId(actionID);
                option.setLabelName(labelName[i]);
                option.setValue(value[i]);
                option.setCreaterId(userId);
                option.setCreateTime(date);
                optionService.save(option);
            }
        }


    }

    /**
     * 根据分类查询数据
     *
     * @param treebookId
     * @return
     */
    public Map<String, Object> getCondition(Long treebookId) {
        Map<String, Object> result = new HashMap<>();


        List<Action> actionList = actionService.getActionByTreeBookId(treebookId);

        if (!actionList.isEmpty()) {
            List<Option> optionList = optionService.getOptionByTreeBookId(actionList.get(0).getId());
            if (!optionList.isEmpty())
                result.put("option", optionList.get(0));
        }


        List<Input> inputList = inputService.getInputByTreeBookId(treebookId);

        if (!actionList.isEmpty())
            result.put("action", actionList.get(0));


        if (!inputList.isEmpty())
            result.put("input", inputList.get(0));

        return result;
    }


    /**
     * 修改分类对应的数据
     *
     * @param action
     * @param input
     * @param labelName
     * @param value
     */
    @Transactional
    public void modifyCondition(Action action, Input input, String[] labelName, String[] value) {
        //修改之前查询是否存在
        List<Action> actionList = actionService.getActionByTreeBookId(action.getTreebookId());
        if (actionList.isEmpty()) {
            throw new DggBaseException("还没有添加，请添加");
        }
        //先删除分类下的数据
        deleteCondition(action.getTreebookId());
        //再增加
        addCondition(action, input, labelName, value);
    }


    /**
     * 删除分类对应的数据
     *
     * @param treebookId 分类id
     */
    @Transactional
    public void deleteCondition(Long treebookId) {
        //修改之前查询是否存在
        List<Action> actionList = actionService.getActionByTreeBookId(treebookId);
        if (actionList.isEmpty()) {
            throw new DggBaseException("还没有添加，请添加");
        }
        for (Action action : actionList) {
            List<Option> optionList = optionService.getOptionByTreeBookId(action.getId());
            for (Option option : optionList) {
                optionService.delete(option.getId());
            }
            actionService.delete(action.getId());
        }

        List<Input> inputLists = inputService.getInputByTreeBookId(treebookId);
        for (Input input : inputLists) {
            inputService.delete(input.getId());
        }
    }


}
