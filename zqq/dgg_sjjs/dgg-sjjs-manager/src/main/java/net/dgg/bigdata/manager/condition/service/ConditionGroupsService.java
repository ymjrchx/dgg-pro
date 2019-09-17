package net.dgg.bigdata.manager.condition.service;

import net.dgg.base.baseService.BaseService;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.condition.ConditionGroups;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.utils.common.DggKeyWorker;
import net.dgg.core.utils.exception.DggBaseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 15:02
 * @Description:
 */
@Service
public class ConditionGroupsService extends BaseService<ConditionGroups> {

    @Resource
    private SessionManager sessionManager;

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 增加
     *
     * @param conditionGroups
     */
    public void add(ConditionGroups conditionGroups) {
        if (conditionGroups.getSort() == null || conditionGroups.getSort() < ConditionConstant.INT_0 || conditionGroups.getSort() > ConditionConstant.INT_999) {
            throw new DggBaseException("排序字段输入错误，请输入0-999");
        }
        conditionGroups.setId(DggKeyWorker.nextId());
        //设置type为行业模板对应的type值
        conditionGroups.setType(ConditionConstant.CONDITION_TYPE_1);

        //获取当前登录的用户的id
        Date date = new Date();
        Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
        //设置创建者和时间
        conditionGroups.setCreaterId(userId);
        conditionGroups.setCreateTime(date);
        save(conditionGroups);
    }

    /**
     * 修改
     *
     * @param conditionGroups
     */
    @Transactional
    public void modify(ConditionGroups conditionGroups) {
        if (conditionGroups.getSort() == null || conditionGroups.getSort() < ConditionConstant.INT_0 || conditionGroups.getSort() > ConditionConstant.INT_999) {
            throw new DggBaseException("排序字段输入错误，请输入0-999");
        }
        //获取当前登录的用户的id
        Date date = new Date();
        Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
        //设置修改时间
        conditionGroups.setUpdaterId(userId);
        conditionGroups.setUpdateTime(date);
        //设置创建者
        ConditionGroups oldConditionGroups = findById(conditionGroups.getId());
        conditionGroups.setCreaterId(oldConditionGroups.getCreaterId());
        conditionGroups.setCreaterName(oldConditionGroups.getCreaterName());
        conditionGroups.setCreaterOrgId(oldConditionGroups.getCreaterOrgId());
        conditionGroups.setCreateTime(oldConditionGroups.getCreateTime());
        conditionGroups.setType(oldConditionGroups.getType());
        update(conditionGroups);
    }

    /**
     * 删除redis行业模板缓存
     */
    public void deleteCache() {
        if (redisTemplate.hasKey(ConditionConstant.INDUSTRY_REDIS_KEY)) {
            redisTemplate.delete(ConditionConstant.INDUSTRY_REDIS_KEY);
        }
    }

    /**
     * 删除redis企业搜索缓存
     */
    public void deleteCompanyCache() {

        if (redisTemplate.hasKey(ConditionConstant.COM_SEARCH_TREEBOOK)) {
            redisTemplate.delete(ConditionConstant.COM_SEARCH_TREEBOOK);
        }
    }


}
