package net.dgg.bigdata.sjjs.web.condition.service;

import net.dgg.base.baseService.BaseService;
import net.dgg.bigdata.common.constant.ConditionConstant;
import net.dgg.bigdata.common.entity.condition.ConditionGroups;
import net.dgg.bigdata.sjjs.web.condition.dao.ConditionGroupsDao;
import net.dgg.bigdata.sjjs.web.condition.dto.ConditionGroupDto;
import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.util.SysUserUtils;
import net.dgg.core.utils.common.DggKeyWorker;
import net.dgg.core.utils.exception.DggBaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class ConditionGroupsService extends BaseService<ConditionGroups> {


    @Resource
    private ConditionGroupsDao conditionGroupsDao;

    /**
     * 获取历史条件组
     */
    public List<ConditionGroupDto> getConditionGroups(HttpServletRequest request) {

        //获取用户
        SysUser sysUser = SysUserUtils.getUser(request);
        if (sysUser == null) {
            throw new DggBaseException("请登录");
        }
        Long userId = Long.parseLong(sysUser.getUserId());
        //根据用户获取历史条件组
        List<ConditionGroupDto> conditionGroups = conditionGroupsDao.getConditionGroups(userId);
        return conditionGroups;
    }

    /**
     * 增加历史条件组
     */
    @Transactional
    public void addConditionGroups(ConditionGroupDto conditionGroupDto, HttpServletRequest request) {

        if (StringUtils.isEmpty(conditionGroupDto.getName())) {
            throw new DggBaseException("输入名称");
        }
        if (StringUtils.isEmpty(conditionGroupDto.getFilter())) {
            throw new DggBaseException("请输入正确的条件组");
        }
        SysUser sysUser = SysUserUtils.getUser(request);
        if (sysUser == null) {
            throw new DggBaseException("请登录");
        }

        //添加之前先查询添加数量 最多只能添加20条，超过20条报错
        Map parmMap = new HashMap();
        parmMap.put(ConditionConstant.CREATERID, sysUser.getUserId());
        List query = query(parmMap);
        if (query.size() >= ConditionConstant.INT_20) {
            throw new DggBaseException("最多只能添加20条历史记录，请删除后添加");
        }

        ConditionGroups conditionGroups = new ConditionGroups();
        conditionGroups.setId(DggKeyWorker.nextId());
        conditionGroups.setName(conditionGroupDto.getName());
        //conditionGroups.setUsedCount(conditionGroupDto.getUsed_count());
        //conditionGroups.setSort(conditionGroupDto.getOrder());
        conditionGroups.setFilter(conditionGroupDto.getFilter());
        //conditionGroups.setInfo(conditionGroupDto.getInfo());

        //获取用户, 设置用户信息

        conditionGroups.setCreaterId(Long.parseLong(sysUser.getUserId()));
        conditionGroups.setCreaterName(sysUser.getUsername());
        conditionGroups.setCreateTime(new Date());

        //增加历史条件组
        conditionGroupsDao.addConditionGroups(conditionGroups);
    }

    /**
     * 修改 历史条件组
     *
     * @param conditionGroupDto
     */
    @Transactional
    public void modifyConditionGroups(ConditionGroupDto conditionGroupDto, HttpServletRequest request) {

        if (StringUtils.isEmpty(conditionGroupDto.getName())) {
            throw new DggBaseException("输入名称");
        }
        if (StringUtils.isEmpty(conditionGroupDto.getFilter())) {
            throw new DggBaseException("请输入正确的条件组");
        }
        //获取用户，查看用户是否登录
        SysUser sysUser = SysUserUtils.getUser(request);
        if (sysUser == null) {
            throw new DggBaseException("请登录");
        }

        //先查询旧的数据
        ConditionGroups oldConditionGroups = findById(conditionGroupDto.getId());
        if (oldConditionGroups.getCreaterId() == Long.parseLong(sysUser.getUserId())) {
            oldConditionGroups.setId(Long.parseLong(conditionGroupDto.getId()));
            oldConditionGroups.setName(conditionGroupDto.getName());
            oldConditionGroups.setSort(conditionGroupDto.getOrder());
            oldConditionGroups.setFilter(conditionGroupDto.getFilter());
            oldConditionGroups.setInfo(conditionGroupDto.getInfo());
            //设置修改者
            oldConditionGroups.setUpdaterId(Long.parseLong(sysUser.getUserId()));
            oldConditionGroups.setUpdaterName(sysUser.getUsername());
            oldConditionGroups.setUpdateTime(new Date());
            update(oldConditionGroups);
        } else {
            throw new DggBaseException("登录错误");
        }


    }

    /**
     * 删除 历史条件组
     *
     * @param id
     * @param request
     */
    @Transactional
    public void deleteConditionGroups(Long id, HttpServletRequest request) {

        //先查询旧的数据看是不是同一个人的
        ConditionGroups oldConditionGroups = findById(id);
        //获取用户，并设置修改者
        SysUser sysUser = SysUserUtils.getUser(request);
        if (sysUser == null) {
            throw new DggBaseException("请登录");
        }
        if (oldConditionGroups.getCreaterId() == Long.parseLong(sysUser.getUserId())) {
            delete(id);
        } else {
            throw new DggBaseException("登录错误");
        }

    }

    /**
     * 修改使用次数
     */
    @Transactional
    public void modifyConditionGroupsCounts(Long id, HttpServletRequest request) {

        //查询条件组
        ConditionGroups condition = findById(id);
        //设置使用次数
        ConditionGroups conditionGroups = new ConditionGroups();
        conditionGroups.setId(id);
        conditionGroups.setUsedCount((condition.getUsedCount() == null ? 0 : condition.getUsedCount()) + ConditionConstant.INT_1);
        update(conditionGroups);
    }

    /**
     * 根据行业模板获取条件组
     *
     * @return
     */
    public List<ConditionGroupDto> getConditionGroupDtoForIndustry(Long industryId) {
        return conditionGroupsDao.getConditionGroupDtoForIndustry(industryId);
    }


}
