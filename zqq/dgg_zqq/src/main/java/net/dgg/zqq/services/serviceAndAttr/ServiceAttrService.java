package net.dgg.zqq.services.serviceAndAttr;

import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrDao;
import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrExtDao;
import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrRelationDao;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttr;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttrRelation;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <ServiceAttrService>
 * @despriction 服务属性service
 * @create 2018/09/28 20:27
 **/
@Service
public class ServiceAttrService {
    @Autowired
    private ServiceAttrDao serviceAttrDao;
    @Autowired
    private ServiceAttrExtDao serviceAttrExtDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;
    @Autowired
    private ServiceAttrRelationDao serviceAttrRelationDao;


    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.serviceAttrExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.serviceAttrExtDao.queryMap(query);
        }
        return Collections.emptyList();
    }

    public Integer queryCode(String code) {
        Assert.hasText(code, "code不能为空！");
        Integer count = this.serviceAttrDao.count(new HashMap() {{
            put("code", code);
            put("flag", 1);
        }});
        return count;
    }

    @Transactional
    public void saveList(List<ServiceAttr> serviceAttrList) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");
        Assert.notEmpty(serviceAttrList, "数据不能为空！");
        for (ServiceAttr serviceAttr : serviceAttrList) {
            Assert.hasText(serviceAttr.getCode(), "code不能为空！");
            Assert.hasText(serviceAttr.getName(), "name不能为空！");
            Assert.hasText(serviceAttr.getParentCode(), "parentCode不能为空!");
            Assert.hasText(serviceAttr.getParentName(), "parentName不能为空!");
            Assert.notNull(serviceAttr.getServicePrice(), String.format("%s服务费不能为空", serviceAttr.getName()));
            Assert.notNull(serviceAttr.getOfficialPrice(), String.format("%s官费不能为空", serviceAttr.getName()));

            Integer count = this.queryCode(serviceAttr.getCode());
            Assert.isTrue(count.intValue() == 0, String.format("服务属性 %s 数据库中已存在！", serviceAttr.getName()));

            serviceAttr.setFlag(1);
            serviceAttr.setStatus(StatusConstant.ENABLE);
            serviceAttr.setId(KeyWorker.nextId());
            serviceAttr.setCreateUser(userEntity);
        }
        for (ServiceAttr serviceAttr : serviceAttrList) {
            this.serviceAttrDao.save(serviceAttr);
        }

    }

    public ServiceAttr findById(Long id) {
        return this.serviceAttrDao.findById(id);
    }

    @Transactional
    public void update(ServiceAttr serviceAttr) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.notNull(serviceAttr, "数据不能为空！");
        Assert.notNull(serviceAttr.getId(), "id不能为空！");
        Assert.notNull(serviceAttr.getOfficialPrice(), "价格不能为空！");
        Assert.notNull(serviceAttr.getStatus(), "状态不能为空！");

        ServiceAttr serviceAttrSys = this.serviceAttrDao.findById(serviceAttr.getId());
        Assert.notNull(serviceAttrSys, "未查询到数据！");

        serviceAttrSys.setServicePrice(serviceAttr.getServicePrice());
        serviceAttrSys.setOfficialPrice(serviceAttr.getOfficialPrice());
        serviceAttrSys.setSort(serviceAttr.getSort());
        serviceAttrSys.setStatus(serviceAttr.getStatus());

        this.serviceAttrDao.update(serviceAttrSys);
    }

    @Transactional
    public void delete(Long id) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.notNull(id, "id不能为空！");
        ServiceAttr serviceAttrSys = this.serviceAttrDao.findById(id);
        Assert.notNull(serviceAttrSys, "未查询到数据！");

        serviceAttrSys.setFlag(0);
        serviceAttrSys.setUpdaterUser(userEntity);
        this.serviceAttrDao.update(serviceAttrSys);

        // 删除 服务与服务属性关系
        List<ServiceAttrRelation> serviceAttrRelationList = this.serviceAttrRelationDao.query(new HashMap() {{
            put("serviceAttrId", id);
        }});
        for (ServiceAttrRelation serviceAttrRelation : serviceAttrRelationList) {
            this.serviceAttrRelationDao.deleteById(serviceAttrRelation.getId());
        }
    }

}
