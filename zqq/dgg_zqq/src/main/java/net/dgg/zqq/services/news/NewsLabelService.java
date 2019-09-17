package net.dgg.zqq.services.news;

import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.dao.news.NewsLabelDao;
import net.dgg.zqq.dao.news.NewsLabelExtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: NewsLabelService
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/11/9 13:35
 */


@Service
@Transactional
public class NewsLabelService {

    @Autowired
    private NewsLabelDao newsLabelDao;
    @Autowired
    private NewsLabelExtDao newsLabelExtDao;

    public List<Map<String, Object>> find(Integer pageSize, Integer pageNum, String newsLabel) {
        if (pageSize == null || pageSize < 0) {
            pageSize = 10; //如果不设置 默认查询10条
        }
        if (pageNum == null || pageNum < 0) {
            pageNum = 1;  //如果不设置页码 默认查询第一页
        }
        Integer start = (pageNum - 1) * pageSize;
        Integer limit = pageSize;

        Map map = new HashMap();
        map.put("start", start);
        map.put("limit", limit);
        map.put("newsLabel", newsLabel);
        map.put("flag", 1);
        map.put("status", StatusConstant.ENABLE);

        List<Map<String, Object>> news = newsLabelExtDao.find(map);

        Map result = new HashMap();
        return news;

    }
}