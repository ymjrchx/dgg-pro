package net.dgg.zqq.services;

import net.dgg.zqq.dao.TreeBookDao;
import net.dgg.zqq.dao.UserDao;
import net.dgg.zqq.dao.order.CommentRecordDao;
import net.dgg.zqq.dao.order.CommentRecordExtDao;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/9/29 9:49
 */

@Service
public class CommentRecordService {
    @Autowired
    CommentRecordDao commentRecordDao;
    @Autowired
    CommentRecordExtDao commentRecordExtDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TreeBookDao treeBookDao;

    //返回当页List
    public List<String> queryRecord(Map typeCode, int pageIndex, int pageSize, int total) {
        int start = pageSize * (pageIndex - 1);  //开始
        int page = (total - 1) / pageSize + 1; //总页数
        int limit;
        if (page == pageIndex) {
            limit = total - start;
        } else limit = pageSize;
        typeCode.put("start", start);
        typeCode.put("limit", limit);
        List dataList = this.commentRecordDao.query(typeCode);
        return dataList;
    }

    //返回评价总条数
    public int recordCount(Map<String, String> typeCode) {
        return this.commentRecordDao.total(typeCode);
    }

    public User findUser(String userId) {
        return this.userDao.selectPersonInfoById(userId);
    }

    //获取好评中评 差评的条数
    public List GetCount(Map query, int total) {
        List<Map> list = new ArrayList<Map>();
        int good = this.commentRecordExtDao.selectGoodRecord(query);
        int mid = this.commentRecordExtDao.selectMidRecord(query);
        int bad = this.commentRecordExtDao.selectBadRecord(query);
        Map map = new HashMap();
        map.put("good", good);
        map.put("mid", mid);
        map.put("bad", bad);
        map.put("total", total);
        list.add(map);
        return list;
    }

    /**
     * 根据三级code获取一级code
     */
    public String getLevel1Code(String serviceTypeLevel3Code) {
        TreeBook treeBook = this.treeBookDao.getTreeBookByCode(serviceTypeLevel3Code);
        TreeBook treeBook1 = this.treeBookDao.getTreeBookByCode(treeBook.getPcode());
        String code = treeBook1.getPcode();
        return code;
    }
}
