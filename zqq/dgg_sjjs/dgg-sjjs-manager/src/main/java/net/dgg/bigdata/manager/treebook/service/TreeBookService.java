package net.dgg.bigdata.manager.treebook.service;

import net.dgg.bigdata.common.constant.Constants;
import net.dgg.bigdata.common.entity.TreeBook;
import net.dgg.bigdata.manager.treebook.CommonConstant;
import net.dgg.bigdata.manager.treebook.dao.TreeBookDao;
import net.dgg.core.mongo.cache.AbstractMongoCacheModule;
import net.dgg.core.utils.DggJsonUtils;
import net.dgg.core.utils.DggStringUtils;
import net.dgg.core.utils.DggValidateUtil;
import net.dgg.core.utils.common.DggKeyWorker;
import net.dgg.core.utils.exception.DggBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 数据字典Service
 * 继承AbstractCacheModule，可从redis缓存中存取数据
 *
 * @author ZYJ
 */
@Service
public class TreeBookService extends AbstractMongoCacheModule<String> {


    @Autowired
    private TreeBookDao treeBookDao;

    //缓存闲置过期时间间隔，单位秒
    private long bookExpireInterval = 60 * 60 * 1000L;


    public TreeBookService() {
        this.setDataSecondDefault(bookExpireInterval);
        this.setCacheCollectionKey("net.dgg.iboss.cms.treebook.service.TreeBookService");
    }

    /**
     * 根据pid获取code
     * @param pid pid
     * @return
     */
    public List<String> getCodeByPid(String pid) {
        return treeBookDao.getCodeByPid(pid);
    }

    /**
     * 根据code获取TreeBook
     * @param code code
     * @return
     */
    public List<TreeBook> getNameAndCodeByCode(String code) {
        return treeBookDao.getNameAndCodeByCode(code);
    }

    /**
     * 根据code获取下级name
     * @param code code
     * @return
     */
    public List<String> getChildNameByCode(String code) {
        return treeBookDao.getChildNameByCode(code);
    }

    /**
     * 查询满足条件的数据字典
     *
     * @param params {id:字典id,code:字典编码,name:字典名称}
     * @return
     */
    public List<TreeBook> getTreeBooksByCondition(Map params) {
        List<TreeBook> list = new ArrayList<>();
        if (params != null) {
            list = treeBookDao.getTreeBooksByCondition(params);
        }
        return list;
    }
    /**
     * 删除数据字典及其下的子集
     *
     * @param bookIdList 需要删除的字典的id集合
     */
    public void deleteTreeBooks(List<Long> bookIdList) {
        //获取每个id对应的level
        List<String> levels = treeBookDao.getLevelsByIds(bookIdList);
        //根据level删除字典及其子集
        for (String level : levels) {
            treeBookDao.deleteByLevel(level);
        }
    }

    /**
     * 判断是否有上级
     *
     * @param pId 节点id
     * @return
     */
    public boolean isParent(Long pId) {
        Integer count = getTreeBookCountByPid(pId);
        if (count == null || count == 0) {
            return false;
        }
        return true;
    }

    /**
     * 查询满足条件的数据字典数量
     *
     * @param book 对象作为查询条件，所需的条件都set到book里面
     * @return
     */
    public Integer getTreeBookCountByCondition(TreeBook book) {
        return treeBookDao.getTreeBookCountByCondition(book);
    }

    /**
     * 查询满足条件的所有数据字典
     *
     * @param params {id:字典id,code:字典编码,name:字典名称}
     * @return
     */
    public List<TreeBook> getTreeBooksByConditionWithPage(Map params) {
        List<TreeBook> list = new ArrayList<>();
        if (params != null) {
            list = treeBookDao.getTreeBooksByConditionWithPage(params);
        }
        return list;
    }

    /**
     * 根据pid查询数据字典
     *
     * @param pid 不可为空
     * @return 返回数据字典集合
     */
    public List<TreeBook> getTreeBooksByPid(Long pid) {
        DggValidateUtil.notNull(pid, DggBaseException.class, "id不可为空");
        return treeBookDao.getTreeBooksByPid(pid);
    }

    /**
     * 根据pid查询子集数量
     *
     * @param pid 不可为空
     * @return 返回子集数量
     */
    public Integer getTreeBookCountByPid(Long pid) {
        DggValidateUtil.notNull(pid, DggBaseException.class, "id不可为空");
        return treeBookDao.getTreeBookCountByPid(pid);
    }

    /**
     * 根据pid查询出ids
     *
     * @param pid 父节点pid 不可为空
     * @return 返回查询所得的id集合
     */
    public Set<String> getIdsByPid(Long pid) {
        List<String> ids = treeBookDao.getIdsByPid(pid);
        Set<String> idSet = new HashSet<String>();
        for (String string : ids) {
            idSet.add(string);
        }
        return idSet;
    }

    /**
     * 根据ID查询单条数据字典
     *
     * @param id 不可为空
     * @return 数据字典对象
     */
    public TreeBook getTreeBookById(Long id) {
        DggValidateUtil.notNull(id, DggBaseException.class, "id不可为空");
        return treeBookDao.selectByPrimaryKey(id);
    }

    /**
     * 新增字典
     *
     * @param treeBook 新增的节点对象
     * @param userId   操作用户id
     * @param userName 操作用户名
     * @return 返回当前新增的对象
     */
    @Transactional
    public TreeBook addTreeBook(TreeBook treeBook, Long userId, String userName) {
        TreeBook target = null;
        //新增需要校验字典编码是否存在
        if (treeBookDao.countTreeBookByCode(treeBook.getCode()) > 0) {
            throw new DggBaseException("字典编码已存在");
        }
        Long id = DggKeyWorker.nextId();
        // 新节点id
        DggValidateUtil.notNull(treeBook.getPid(), DggBaseException.class, "父节点pid不可为空");
        // 根据pid判断是新增子节点还是新增根节点，如果为0则为新增根节点
        target = new TreeBook();
        if (treeBook.getPid() > 0L) {
            //新增子节点
            // 通过当前节点的父节点pid查询出父节点对象
            TreeBook ptreeBook = treeBookDao.selectByPrimaryKey(treeBook.getPid());
            if (ptreeBook != null) {
                if (ptreeBook != null && ptreeBook.getStatus() == 0 && treeBook.getStatus() != 0) {//如果如果父级字典状态未启用，则子集不能启用
                    throw new DggBaseException("上级字典未启用！请先设置上级启用");
                }
                target.setPid(ptreeBook.getId());
                target.setPcode(ptreeBook.getCode());
                target.setLevels(ptreeBook.getLevels() + "_" + ptreeBook.getId());
            } else {
                throw new DggBaseException("父级字典不存在");
            }
        } else {
            target.setPid(-1L);
            target.setPcode("-1");
            target.setLevels("-1");
        }
        target.setId(id);
        target.setCode(treeBook.getCode());
        target.setName(treeBook.getName());
        target.setDescription(treeBook.getDescription());
        target.setExt1(treeBook.getExt1());
        target.setExt2(treeBook.getExt2());
        target.setExt3(treeBook.getExt3());
        target.setExt4(treeBook.getExt4());
        target.setExt5(treeBook.getExt5());
        target.setSort(treeBook.getSort());
        target.setStatus(treeBook.getStatus());
        target.setCreateTime(new Date());
        target.setCreaterId(userId);
        target.setCreaterName(userName);
        target.setUpdaterId(userId);
        target.setUpdaterName(userName);
        treeBookDao.insert(target);
        super.saveToCache(target.getCode(), target.getName());//更新缓存
        return target;
    }

    /**
     * 修改字典
     *
     * @param treeBook 修改的节点对象
     * @param userId   操作用户id
     * @param userName 操作用户名
     * @return 返回当前修改的对象
     */
    @Transactional
    public TreeBook updateTreeBook(TreeBook treeBook, Long userId, String userName) {
        TreeBook target = null;
        DggValidateUtil.notNull(treeBook.getId(), DggBaseException.class, "id不可为空");
        target = treeBookDao.selectByPrimaryKey(treeBook.getId());//待更新的treebook
        /*if (!target.getCode().equals(treeBook.getCode())) {
            throw new DggBaseException("不能修改字典编码");
        }*/
        DggValidateUtil.notNull(treeBook.getPid(), DggBaseException.class, "父节点pid不可为空");
        //通过当前节点的父节点pid查询出父节点对象
        TreeBook ptreeBook = treeBookDao.selectByPrimaryKey(treeBook.getPid());
        if (ptreeBook != null && ptreeBook.getStatus() == 0 && treeBook.getStatus() != 0) {//如果如果父级字典状态未启用，则子集不能启用
            throw new DggBaseException("上级字典未启用！请先设置上级启用");
        }
        if (treeBook.getStatus() == 0) {//如果当节点为禁用，需要判断是否有启用的子节点，如果有就不能禁用
            int count = treeBookDao.countChildTreeBook(target.getLevels() + "_" + target.getId(), 1);
            if (count > 1) {
                throw new DggBaseException("字典下有启用的子级！不能设置禁用");
            }
        }
        //把页面传回来的值放入对象中
        target.setExt1(treeBook.getExt1());
        target.setExt2(treeBook.getExt2());
        target.setExt3(treeBook.getExt3());
        target.setExt4(treeBook.getExt4());
        target.setExt5(treeBook.getExt5());
        target.setName(treeBook.getName());
        target.setDescription(treeBook.getDescription());
        target.setSort(treeBook.getSort());
        target.setStatus(treeBook.getStatus());
        target.setUpdaterId(userId);
        target.setUpdaterName(userName);
        treeBookDao.updateByPrimaryKey(target);
        super.saveToCache(target.getCode(), target.getName());//更新缓存
        return target;
    }

    @Override
    protected String loadValue(String field) {
        if (DggStringUtils.isEmpty(field)) {
            return null;
        }
        return treeBookDao.getNameByCode(field);
    }


    /**
     * 查询字典接口
     *
     * @param code   编码
     * @param status 状态 1：启用，0：禁用，无：所有
     * @param type   1：根据分类编码查询下级第一级分类（用于Select选择）
     *               2：根据分类编码查询所有下级分类（用于Tree选择无父级的）
     *               3：根据分类编码查询所有下级分类包含自己（用于Tree选择有父级的）
     * @return
     */
    public List<TreeBook> getTreeBookListByCode(String code, Integer status, Integer type, Integer level) {
        List<TreeBook> resultList = new ArrayList<TreeBook>();
        if (DggStringUtils.isEmpty(code)) {
            throw new DggBaseException("编码不能为空");
        }
        if (null == type) {
            throw new DggBaseException("查询类型不能为空");
        }
        if (type.intValue() != 1 && type.intValue() != 2 && type.intValue() != 3) {
            throw new DggBaseException("查询类型只能是1/2/3");
        }
        String cacheName = Constants.TREEBOOK_LIST_KEY + code;
        cacheName += (DggStringUtils.isEmptyObj(type) ? "" : "_type" + type);
        cacheName += (DggStringUtils.isEmptyObj(status) ? "" : "_status" + status);
        cacheName += (DggStringUtils.isEmptyObj(level) ? "" : "_level" + level);

        String treeJson = super.get(cacheName);
        if (!DggStringUtils.isEmpty(treeJson)) {
            resultList = DggJsonUtils.json2List(treeJson, TreeBook.class);
        } else {
            resultList = treeBookDao.getTreeBookListByCode(code, status, type, level);
            if (null != resultList && resultList.size() > 0) {
                super.saveToCache(cacheName, DggJsonUtils.obj2Json(resultList));
            }
        }
        return resultList;
    }

    /**
     * 更新数据字典缓存
     */
    public void updateTreeBookCache() {
        super.clear();
        List<TreeBook> resultList = treeBookDao.getTreeBookListByCode(null, null, null, null);
        for (int i = 0; i < resultList.size(); i++) {
            TreeBook treeBook = resultList.get(i);
            this.cacheTreeBook(treeBook);

        }
    }

    /**
     * 缓存单个数据字典
     *
     * @param treeBook
     */
    public void cacheTreeBook(TreeBook treeBook) {
        super.saveToCache(Constants.TREEBOOK_KEY + treeBook.getCode(), treeBook.getName());
        super.saveToCache(Constants.TREEBOOK_OBJ + treeBook.getCode(), DggJsonUtils.obj2Json(treeBook));
    }


    /**
     * 根据编码查询数据字典
     *
     * @param code
     * @return
     */
    public TreeBook getTreeBookByCode(String code) {
        return treeBookDao.getTreeBookByCode(code);
    }

    /**
     * 根据编码查询数据字典(多个)
     *
     * @param codes
     * @return
     */
    public List<TreeBook> findTreeBookByCodes(String codes) {
        return treeBookDao.findTreeBookByCodes(codes);
    }

    /**
     * 移动字典
     *
     * @param id
     * @param pid
     * @return
     */
    public TreeBook updateTreebookDrop(Long id, Long pid) {
        TreeBook treeBook = null;
        treeBook = this.getTreeBookById(id);
        TreeBook treeBookParent = this.getTreeBookById(pid);
        if (treeBookParent.getStatus().intValue() != 1) {
            throw new DggBaseException(treeBookParent.getName() + "已禁用，不能移到此数据字典下");
        }
        String oldLevel = treeBook.getLevels() + "_" + treeBook.getId();//拿到原子节点层级信息
        treeBook.setLevels(treeBookParent.getLevels() + "_" + treeBookParent.getId());//设值新的层级信息
        treeBook.setPid(treeBookParent.getId());
        treeBook.setPcode(treeBookParent.getCode());
        String newLevel = treeBook.getLevels() + "_" + treeBook.getId();//根据设置的新层级拿到子节点的层级
        treeBookDao.updateTreebookDrop(oldLevel, newLevel);
        treeBookDao.updateByPrimaryKey(treeBook);
        return treeBook;
    }

    /**
     * 查询部门业态
     *
     * @param orgId 部门ID
     * @return
     */
    public List<TreeBook> queryOrgBusTypeByOrgId(Long orgId) {
        return treeBookDao.queryOrgBusTypeByOrgId(orgId);
    }

    /**
     * 缓存字典集合
     *
     * @param key       key
     * @param treeBooks treeBooks
     * @return
     */
    public void cacheListTreeBook(String key, List<TreeBook> treeBooks) {
        super.saveToCache(key, DggJsonUtils.obj2Json(treeBooks));
    }

    /**
     * 根据多个code查询字典翻译集合
     *
     * @param codes
     * @return
     */
    public Map getTreeBookNameMulti(String codes) {
        Map reMap = new HashMap();
        String[] codStr = codes.split(",");
        for (int i = 0; i < codStr.length; i++) {
            String code = codStr[i];
            if (DggStringUtils.isNotEmpty(code)) {
                String bookName = this.getTreeBookName(code);
                reMap.put(code, bookName);
            }
        }
        return reMap;
    }

    public String getTreeBookName(String code) {
        if (DggStringUtils.isEmpty(code)) {
            return code;
        }
        String treeName = this.get(CommonConstant.TREEBOOK_KEY + code);
        if (DggStringUtils.isEmpty(treeName) || treeName.equals(code)) {
            TreeBook treeBook = this.getTreeBookByCode(code);
            if (null != treeBook) {
                treeName = treeBook.getName();
                this.cacheTreeBook(treeBook);
            }
        }
        return treeName;
    }

}
