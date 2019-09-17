package net.dgg.zqq.services.ServiceItem.impl;

import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrExtDao;
import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrRelationDao;
import net.dgg.zqq.dao.serviceAndAttr.ServiceItemDao;
import net.dgg.zqq.dao.serviceAndAttr.ServiceItemExtDao;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttr;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttrRelation;
import net.dgg.zqq.entity.serviceAndAttr.ServiceItem;
import net.dgg.zqq.services.ServiceItem.ServiceItemService;
import net.dgg.zqq.services.TreeBookService;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.dgg.zqq.utils.BeanUtils;
import net.dgg.zqq.utils.MapUtils;
import net.dgg.zqq.utils.ServiceItemNumberUtil;
import net.dgg.zqq.utils.StringUtils;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {

    @Autowired
    private ServiceItemDao serviceItemDao;
    @Autowired
    private ServiceItemExtDao serviceItemExtDao;
    @Autowired
    private ServiceAttrExtDao serviceAttrExtDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;
    @Autowired
    private ServiceAttrRelationDao serviceAttrRelationDao;
    @Autowired
    private TreeBookService treeBookService;
    @Autowired
    private FileUploadService fileUploadService;

    public ServiceItem findById(Long id) {
        return this.serviceItemDao.findById(id);
    }


    @Override
    public List<Map<String, Object>> findServiceItemByNumber(String numbers) {
        Assert.notNull(numbers, "numbers不能为空");
        String[] numberAttr = numbers.split(",");
        Assert.isTrue(numberAttr != null && numberAttr.length > 0, "codes传参格式错误");
        List<Map<String, Object>> resultList = new ArrayList();
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        map.put("flag", 1);
        map.put("limit", 1);
        for (String number : numberAttr) {
            map.put("number", number);
            List<ServiceItem> list = serviceItemDao.query(map);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("number", number);
            ServiceItem serviceItem = null;
            if (list != null && list.size() > 0 && list.get(0) != null) {
                serviceItem = list.get(0);
            }
            resultMap.put("serviceItem", serviceItem);
            resultMap.put("fhost",fileUploadService.getHost());
            resultList.add(resultMap);

        }
        return resultList;

    }

    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.serviceItemExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.serviceItemExtDao.queryMap(query);
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void save(ServiceItem serviceItem, String serviceAttrId) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");
        Assert.hasText(serviceItem.getLogoFile(), "logoFile不能为空");
        Assert.hasText(serviceItem.getDetailImgFile(), "详情图片不能为空");

        this.checkServiceItem(serviceItem, true);
        List<Long> serviceAttrIdList = this.checkServiceAttrId(serviceAttrId);
        serviceItem.setId(KeyWorker.nextId());
        serviceItem.setNumber(ServiceItemNumberUtil.getNumber());
        serviceItem.setFlag(1);
        serviceItem.setCreateUser(userEntity);

        // 保存
        this.serviceItemDao.save(serviceItem);
        if (serviceAttrIdList != null && !serviceAttrIdList.isEmpty()) {
            for (Long attrId : serviceAttrIdList) {
                ServiceAttrRelation sar = new ServiceAttrRelation();
                sar.setId(KeyWorker.nextId());
                sar.setServiceItemId(serviceItem.getId());
                sar.setServiceAttrId(attrId);
                sar.setCreateUser(userEntity);
                this.serviceAttrRelationDao.save(sar);
            }
        }


    }

    @Override
    @Transactional
    public void update(ServiceItem serviceItem, String serviceAttrId) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        this.checkServiceItem(serviceItem, false);
        List<Long> serviceAttrIdList = this.checkServiceAttrId(serviceAttrId);
        ServiceItem serviceItemSys = this.serviceItemDao.findById(serviceItem.getId());
        Assert.notNull(serviceItemSys, "未查询到此数据！");
        BeanUtils.copyPropertiesIgnoreNull(serviceItem, serviceItemSys);

        serviceItemSys.setUpdaterUser(userEntity);
        //更新
        this.serviceItemDao.update(serviceItemSys);

        List<ServiceAttrRelation> serviceAttrRelationSysList = this.serviceAttrRelationDao.query(new HashMap() {{
            put("serviceItemId", serviceItemSys.getId());
        }});
        for (ServiceAttrRelation serviceAttrRelation : serviceAttrRelationSysList) {
            this.serviceAttrRelationDao.deleteById(serviceAttrRelation.getId());
        }
        if (serviceAttrIdList != null && !serviceAttrIdList.isEmpty()) {
            for (Long attrId : serviceAttrIdList) {
                ServiceAttrRelation sare = new ServiceAttrRelation();
                sare.setId(KeyWorker.nextId());
                sare.setServiceItemId(serviceItem.getId());
                sare.setServiceAttrId(attrId);
                sare.setCreateUser(userEntity);
                this.serviceAttrRelationDao.save(sare);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空！");
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.notNull(id, "id不能为空！");
        ServiceItem serviceItemSys = this.serviceItemDao.findById(id);
        Assert.notNull(serviceItemSys, "未查询到数据！");

        serviceItemSys.setFlag(0);
        serviceItemSys.setUpdaterUser(userEntity);
        this.serviceItemDao.update(serviceItemSys);

        // 删除 服务与服务属性关系
        List<ServiceAttrRelation> serviceAttrRelationList = this.serviceAttrRelationDao.query(new HashMap() {{
            put("serviceItemId", id);
        }});
        for (ServiceAttrRelation serviceAttrRelation : serviceAttrRelationList) {
            this.serviceAttrRelationDao.deleteById(serviceAttrRelation.getId());
        }
    }

    @Override
    public List<Map> queryByTypeLevel3Code(String code, String serviceItemNum) {
        Assert.hasText(code, "code不能为空！");
        TreeBook treeBook = this.treeBookService.getTreeBookByCode(code);
        Assert.notNull(treeBook, "未查询到业务配置！");
        Set<String> codeSet = new HashSet<>();
        codeSet.add(treeBook.getCode());
        // 显示同级下所有服务项
        if (!StringUtils.isEmpty(treeBook.getExt2()) && "1".equals(treeBook.getExt2().trim())) {
            List<TreeBook> treeBooks = this.treeBookService.getTreeBookListByCode(treeBook.getPcode(), 1, 1, null);
            for (TreeBook treeBook1 : treeBooks) {
                codeSet.add(treeBook1.getCode());
            }
        }
        List<ServiceItem> serviceItems = this.serviceItemExtDao.query(new HashMap() {{
            put("typeLevel3CodeArr", codeSet);
            put("status", StatusConstant.ENABLE);
            put("flag", 1);
        }});
        serviceItems.sort((o1, o2) -> {
            Integer s1 = o1 == null ? null : o1.getSort(), s2 = o2 == null ? null : o2.getSort();
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 != null && s2 == null) {
                return -1;
            }
            if (s1 == null && s2 != null) {
                return 1;
            }
            return s1.compareTo(s2);
        });
        List<Map> maps = new ArrayList<>(serviceItems.size());

        // 设置默认选中
        serviceItemNum = StringUtils.isEmpty(serviceItemNum) ? treeBook.getExt4() : serviceItemNum;

        for (ServiceItem serviceItem : serviceItems) {
            Map temp = MapUtils.convertBean(serviceItem);
            temp.put("choosed", serviceItemNum.equals(serviceItem.getNumber()) ? 1 : 0);
            String attrKey = "serviceAttr", attrTypeKey = "serviceAttrType";
            List<ServiceAttr> serviceAttrList = this.getServiceItemAttr(serviceItem.getId());
            temp.put(attrKey, serviceAttrList);
            List<Map> serviceAttrType = this.getServiceAttrTypeArr(serviceAttrList);
            serviceAttrType.sort((Map o1, Map o2) -> {
                String sortKey = "sort";
                Integer s1 = o1 == null || o1.get(sortKey) == null ? null : Integer.parseInt(String.valueOf(o1.get(sortKey))),
                        s2 = o1 == null || o2.get(sortKey) == null ? null : Integer.parseInt(String.valueOf(o2.get(sortKey)));
                if (s1 == null && s2 == null) {
                    return 0;
                }
                if (s1 != null && s2 == null) {
                    return -1;
                }
                if (s1 == null && s2 != null) {
                    return 1;
                }
                return s1.compareTo(s2);
            });
            temp.put(attrTypeKey, serviceAttrType);
            maps.add(temp);
        }
        return maps;
    }

    // 获取服务属性子项
    List<Map> getServiceAttrTypeArr(List<ServiceAttr> serviceAttrList) {
        if (serviceAttrList.size() == 0) {
            return Collections.emptyList();
        }
        Set<String> pCodeAttrSet = new HashSet<>();
        for (ServiceAttr serviceAttr : serviceAttrList) {
            pCodeAttrSet.add(serviceAttr.getParentCode());
        }
        Set<String> pCodeSet = new HashSet<>();
        for (String pCodeAttr : pCodeAttrSet) {
            String[] pCodeArr = pCodeAttr.split(",");
            for (String code : pCodeArr) {
                pCodeSet.add(code);
            }
        }
        List<Map> attrMapList = new ArrayList<>(pCodeSet.size());
        for (String pCode : pCodeSet) {
            Map pMap = this.getAttrMap(pCode);
            if (pMap == null) {
                continue;
            }
            attrMapList.add(pMap);
        }
        return attrMapList;

    }

    // 获取code 字典及其子项
    private Map getAttrMap(String code) {
        Map attrMap = null;
        TreeBook curTreeBook = null;
        List<TreeBook> treeBooks = this.treeBookService.getTreeBookListByCode(code, 1, 3, null);
        for (TreeBook treeBook : treeBooks) {
            if (code.equals(treeBook.getCode())) {
                curTreeBook = treeBook;
                attrMap = MapUtils.convertBean(treeBook);
                break;
            }
        }
        if (attrMap == null) {
            return null;
        }
        treeBooks.remove(curTreeBook);
        treeBooks.sort((TreeBook o1, TreeBook o2) -> {
            Integer s1 = o1 == null ? null : o1.getSort(), s2 = o2 == null ? null : o2.getSort();
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 != null && s2 == null) {
                return -1;
            }
            if (s1 == null && s2 != null) {
                return 1;
            }
            return s1.compareTo(s2);
        });
        attrMap.put("sonAttrArr", treeBooks);
        return attrMap;
    }


    // 获取 服务项 绑定的属性
    private List<ServiceAttr> getServiceItemAttr(Long serviceItemId) {
        List<ServiceAttrRelation> serviceAttrRelations = this.serviceAttrRelationDao.query(new HashMap() {{
            put("serviceItemId", serviceItemId);
        }});
        if (serviceAttrRelations.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> attrIdList = new ArrayList<>(serviceAttrRelations.size());
        for (ServiceAttrRelation serviceAttrRelation : serviceAttrRelations) {
            attrIdList.add(serviceAttrRelation.getServiceAttrId());
        }
        List<ServiceAttr> serviceAttrs = this.serviceAttrExtDao.query(new HashMap() {{
            put("idArr", attrIdList);
            put("status", StatusConstant.ENABLE);
            put("flag", 1);
        }});
        serviceAttrs.sort((ServiceAttr o1, ServiceAttr o2) -> {
            Integer s1 = o1 == null ? null : o1.getSort(), s2 = o2 == null ? null : o2.getSort();
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 != null && s2 == null) {
                return -1;
            }
            if (s1 == null && s2 != null) {
                return 1;
            }
            return s1.compareTo(s2);
        });
        return serviceAttrs;
    }


    // 验证服务属性
    private void checkServiceItem(ServiceItem serviceItem, boolean isSave) {
        Assert.notNull(serviceItem, "服务项数据不能为空！");
        Assert.hasText(serviceItem.getName(), "服务名称不能为空！");
        Assert.notNull(serviceItem.getServicePrice(), "服务价格不能为空！");
        Assert.notNull(serviceItem.getOfficialPrice(), "官费不能为空！");
        Assert.hasText(serviceItem.getStatus(), "状态不能为空！");
        Assert.hasText(serviceItem.getTypeLevel1Code(), "一级大类不能为空！");
        Assert.hasText(serviceItem.getTypeLevel2Code(), "二级分类不能为空！");
        Assert.hasText(serviceItem.getTypeLevel3Code(), "三级分类不能为空！");
        Assert.notNull(serviceItem.getSort(), "排序不能为空！");
        Assert.hasText(serviceItem.getDescrible(), "描述不能为空！");
        List<ServiceItem> serviceItemSysList = this.serviceItemDao.query(new HashMap() {{
            put("name", serviceItem.getName());
            put("typeLevel3Code", serviceItem.getTypeLevel3Code());
            put("flag", 1);
        }});

        if (isSave) {
            Assert.isTrue(serviceItemSysList.size() == 0, String.format("此三级分类下已经有【%s】服务项", serviceItem.getTypeLevel3Code(), serviceItem.getName()));
            return;
        }
        if (serviceItemSysList.size() > 0) {
            ServiceItem serviceItemSys = serviceItemSysList.get(0);
            Assert.isTrue(serviceItemSys.getId().equals(serviceItem.getId()), String.format("此三级分类下已经有【%s】服务项", serviceItem.getTypeLevel3Code(), serviceItem.getName()));
        }

    }

    // 验证服务属性ID
    private List<Long> checkServiceAttrId(String serviceAttrId) {
        if (StringUtils.isEmpty(serviceAttrId)) {
            return null;
        }
        Assert.hasText(serviceAttrId, "服务属性ID不能为空！");
        List<Long> idList = new ArrayList<>();
        for (String id : serviceAttrId.split(",")) {
            idList.add(Long.valueOf(id));
        }
        Assert.notEmpty(idList, "服务属性ID不能为空！");
        Integer count = serviceAttrExtDao.count(new HashMap() {{
            put("idArr", idList);
            put("status", StatusConstant.ENABLE);
            put("flag", 1);
        }});
        Assert.isTrue(count.intValue() == idList.size(), "选择的服务属性中有已被禁用或删除的！");
        return idList;
    }

    //根据三级code获取服务项的图片和描述
    public List getServiceItemByCode(String typeLevel3Code) {
        List<ServiceItem> list = this.serviceItemDao.query(new HashMap() {{
            put("typeLevel3Code", typeLevel3Code);
        }});
        ServiceItem serviceItem = list.get(0);
        List dataList = new ArrayList();
        String host = this.fileUploadService.getHost();
        dataList.add(serviceItem.getDescrible());
        if (!StringUtils.isNullOrEmpty(serviceItem.getLogoFile())) {
            String url = host.concat(serviceItem.getLogoFile());
            dataList.add(url);
        }

        return dataList;
    }


}
