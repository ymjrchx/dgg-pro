package net.dgg.zqq.services;

import net.dgg.zqq.dao.order.CommentRecordExtDao;
import net.dgg.zqq.entity.order.CommentRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/9/29 19:51
 */
@Service
public class CommentCheckService {


    @Autowired
    CommentRecordExtDao commentRecordExtDao;

    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.commentRecordExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.commentRecordExtDao.queryMap(query);
        }
        return Collections.emptyList();
    }


    /**
     * 根据id 改变该评论审核状态
     */

    @Transactional
    public void updateFlag(Long id) {
        CommentRecord commentRecord = this.commentRecordExtDao.findById(id);
        int flag = commentRecord.getFlag();
        if (flag == 1) {
            this.commentRecordExtDao.updateF(id);
        } else {
            this.commentRecordExtDao.updateT(id);
        }
    }
}
