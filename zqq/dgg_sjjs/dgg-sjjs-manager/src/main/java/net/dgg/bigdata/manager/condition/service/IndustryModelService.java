package net.dgg.bigdata.manager.condition.service;

import net.dgg.base.baseService.BaseService;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.condition.IndustryModel;
import net.dgg.bigdata.manager.session.SessionManager;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggKeyWorker;
import net.dgg.core.utils.exception.DggBaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 15:02
 * @Description:
 */
@Service
public class IndustryModelService extends BaseService<IndustryModel> {


    @Resource
    private ConditionGroupsService conditionGroupsService;

    @Resource
    private SessionManager sessionManager;
    /**
     * 增加行业分类
     *
     * @param name
     */
    @Transactional
    public void add(String name, Integer sort) {
        if (sort == null || sort < ConditionConstant.INT_0 || sort > ConditionConstant.INT_999){
            throw new DggBaseException("排序字段输入错误，请输入0-999");
        }
        Map map = new HashMap();
        map.put("applicableIndustryName",name);
        List query = query(map);
        if(!query.isEmpty()){
            throw new DggBaseException("已存在相同行业分类");
        }
        IndustryModel industryModel = new IndustryModel();
        industryModel.setId(DggKeyWorker.nextId());
        industryModel.setApplicableIndustryName(name);
        industryModel.setSort(sort);
        //获取当前登录的用户的id
        Date date = new Date();
        Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
        industryModel.setCreaterId(userId);
        industryModel.setCreateTime(date);

        save(industryModel);
    }

    /**
     * 修改
     *
     * @param industryModel
     */
    @Transactional
    public void modify(IndustryModel industryModel) {
        if (industryModel.getSort()==null || industryModel.getSort() < ConditionConstant.INT_0 || industryModel.getSort() > ConditionConstant.INT_999){
            throw new DggBaseException("排序字段输入错误，请输入0-999");
        }
        Map map = new HashMap();
        map.put("applicableIndustryName",industryModel.getApplicableIndustryName());
        List<IndustryModel> query = query(map);
        for(IndustryModel old : query){
            if(!old.getId().equals(industryModel.getId())){
                throw new DggBaseException("已存在相同行业分类");
            }

        }

        //获取当前登录的用户的id
        Date date = new Date();
        Long userId = sessionManager.getCurrentSession().getValue("userId", Long.class);
        industryModel.setUpdaterId(userId);
        industryModel.setUpdateTime(date);
        //获取创建者
        IndustryModel oldIndustryModel = findById(industryModel.getId());
        industryModel.setCreaterId(oldIndustryModel.getCreaterId());
        industryModel.setCreaterName(oldIndustryModel.getCreaterName());
        industryModel.setCreaterOrgId(oldIndustryModel.getCreaterOrgId());
        industryModel.setCreateTime(oldIndustryModel.getCreateTime());

        update(industryModel);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Transactional
    public int deleteIndustryModel(Long id) {
        Map map = new HashMap();
        map.put(ConditionConstant.INDUSTRY_ID, id);
        List<IndustryModel> query = conditionGroupsService.query(map);
        if (query.isEmpty()) {
            delete(id);
            return ConditionConstant.INT_0;
        } else {
            return ConditionConstant.INT_1;
        }

    }

}
