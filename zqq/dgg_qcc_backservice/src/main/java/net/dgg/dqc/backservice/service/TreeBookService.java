package net.dgg.dqc.backservice.service;


import net.dgg.dqc.backservice.dao_mybatis.TreeBookDao;
import net.dgg.dqc.backservice.entity.treebook.TreeBook;
import net.dgg.dqc.backservice.exception.BookException;
import net.fblock.core.util.ValidateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 数据字典Service
 * 继承AbstractCacheModule，可从redis缓存中存取数据
 *
 * @author ZYJ
 */
@Service
public class TreeBookService {


    @Autowired
    private TreeBookDao treeBookDao;

    //缓存闲置过期时间间隔，单位秒
    private long bookExpireInterval = 60 * 60 * 1000L;


    /**
     * 根据pid获取code
     *
     * @param pid pid
     * @return
     */
    public List<String> getCodeByPid(String pid) {
        return treeBookDao.getCodeByPid(pid);
    }

    /**
     * 根据code获取TreeBook
     *
     * @param code code
     * @return
     */
    public List<TreeBook> getNameAndCodeByCode(String code) {
        return treeBookDao.getNameAndCodeByCode(code);
    }

    /**
     * 根据code获取下级name
     *
     * @param code code
     * @return
     */
    public List<String> getChildNameByCode(String code) {
        return treeBookDao.getChildNameByCode(code);
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
        ValidateUtil.notNull(pid, BookException.class, "id不可为空");
        return treeBookDao.getTreeBooksByPid(pid);
    }

    /**
     * 根据pid查询子集数量
     *
     * @param pid 不可为空
     * @return 返回子集数量
     */
    public Integer getTreeBookCountByPid(Long pid) {
        ValidateUtil.notNull(pid, BookException.class, "id不可为空");
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
        ValidateUtil.notNull(id, BookException.class, "id不可为空");
        return treeBookDao.selectByPrimaryKey(id);
    }


    protected String loadValue(String field) {
        if (StringUtils.isEmpty(field)) {
            return null;
        }
        return treeBookDao.getNameByCode(field);
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
            throw new BookException(treeBookParent.getName() + "已禁用，不能移到此数据字典下");
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


}
