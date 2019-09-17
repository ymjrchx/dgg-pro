package net.dgg.zqq.services.news;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.dao.news.NewsDao;
import net.dgg.zqq.dao.news.NewsExtDao;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.entity.news.News;
import net.dgg.zqq.services.TreeBookService;
import net.dgg.zqq.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 刘阳
 * @ClassName <NewService2>
 * @despriction
 * @create 2018/11/23 14:07
 **/
@Service
public class NewsService2 {
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private NewsExtDao newsExtDao;
    @Autowired
    private TreeBookService treeBookService;

    /**
     * 查询详情
     *
     * @param type
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Map getNewsContentById(String type, Long id) {
        Assert.notNull(type, "type不能为空！");
        Assert.notNull(id, "id不能为空");
        News news = this.newsDao.findById(id);
        Assert.notNull(news, "依据此ID未查询到数据！");
        List<TreeBook> news_types = treeBookService.getNameAndCodeByCode("news_types");
        Map<String, String> typeMap = new HashMap() {{
            put("0", ""); //  全部
        }};
        for (TreeBook treeBook : news_types) {
            typeMap.put(treeBook.getExt1(), treeBook.getCode());
        }
        Assert.isTrue(typeMap.containsKey(type), "type值错误");
        String typeCode = typeMap.get(type);
        Integer order = this.newsExtDao.getOrderIndexById(new HashMap() {{
            put("id", id);
            put("type", typeCode);
        }});
        Map reMap = new HashMap();
        Map nowNewsMap = MapUtils.convertBean(news);
        nowNewsMap.put("description", news.getContent());
        reMap.put("now", news);
        if (order == null) {
            return reMap;
        }

        reMap.put("previous", this.dealNewsContent(this.getPrevious(order, typeCode)));
        reMap.put("next", this.dealNewsContent(this.getNext(order, typeCode)));
        return reMap;
    }

    @Transactional
    public Map getNewsContentByNumber(Long number,Integer limit) {
        //Assert.notNull(type, "type不能为空！");
        Assert.notNull(number, "编号不能为空");
        int li = (limit == null || limit < 0) ? 10 : limit;
        List<News> newsList = this.newsDao.query(new HashMap() {{
            put("number", number);
        }});
        News news = newsList.isEmpty() ? null : newsList.get(0);
        Assert.notNull(news, "依据此编号未查询到数据！");
        /*List<TreeBook> news_types = treeBookService.getNameAndCodeByCode("news_types");
        Map<String, String> typeMap = new HashMap() {{
            put("0", ""); //  全部
        }};
        for (TreeBook treeBook : news_types) {
            typeMap.put(treeBook.getExt1(), treeBook.getCode());
        }
        Assert.isTrue(typeMap.containsKey(type), "type值错误");
        String typeCode = typeMap.get(type);
        Integer order = this.newsExtDao.getOrderIndexByNumber(new HashMap() {{
            put("number", number);
            put("type", typeCode);
        }});*/
        Map reMap = new HashMap();
        Map nowNewsMap = MapUtils.convertBean(news);
        nowNewsMap.put("description", news.getContent());
        reMap.put("now", news);
        /*if (order == null) {
            return reMap;
        }*/

        reMap.put("previous", this.dealNewsContent(this.getPrevious(number)));
        reMap.put("next", this.dealNewsContent(this.getNext(number)));


        String type =StringUtils.isEmpty(news.getType())?null:news.getType();
        //最新资讯
        List<News> latestNews = this.newsDao.query(new HashMap() {{
            put("limit", li);
            put("type",type);
            put("flag",1);
            put("status", StatusConstant.ENABLE);
        }});

        if(latestNews!=null){
            latestNews.stream().forEach(a->dealNewsContent(a));
        }
        reMap.put("latestNews",latestNews);

        //排行
        LocalDateTime end = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start7 = end.plusWeeks(-1);
        LocalDateTime start30 = end.plusDays(-30);
        String endStr = end.format(formatter);
        String start7Str = start7.format(formatter);
        String start30Str = start30.format(formatter);
        //排行-近7天
        List<News> sevenList = this.getNewsByDay(10, type, endStr, start7Str);
        if(sevenList!=null){
            sevenList.stream().forEach(a->dealNewsContent(a));
        }
        reMap.put("weekList",sevenList);
        //排行-近一个月
        List<News> monthList = getNewsByDay(10, type, endStr, start30Str);
        if(monthList!=null){
            monthList.stream().forEach(a->dealNewsContent(a));
        }
        reMap.put("monthList",monthList);

        //更新浏览次数
        news.setViewTimes(news.getViewTimes()==null?1:news.getViewTimes()+1);
        newsDao.update(news);

        return reMap;
    }

    /**
     * 查询指定天数之内浏览量倒序的新闻列表
     * @param limit
     * @param typeCode
     * @param end
     * @param start
     * @return
     */
    private List<News> getNewsByDay(Integer limit, String typeCode, String end, String start) {
        return this.newsExtDao.query(new HashMap() {{
                put("limit", limit);
                put("type", typeCode);
                put("flag",1);
                put("status", StatusConstant.ENABLE);
                put("createTimeStart",start);
                put("createTimeEnd",end);
            }});
    }

    /**
     * 最新资讯
     *
     * @param type
     * @param limit
     * @return
     */
    @Transactional
    public List<News> getNewsByType(String type, Integer limit) {
        Assert.notNull(type, "type不能为空！");
        Assert.notNull(limit, "查询条数不能为空");

        List<TreeBook> news_types = treeBookService.getNameAndCodeByCode("news_types");
        Map<String, String> typeMap = new HashMap() {{
            put("0", ""); //  全部
        }};
        for (TreeBook treeBook : news_types) {
            typeMap.put(treeBook.getExt1(), treeBook.getCode());
        }
        Assert.isTrue(typeMap.containsKey(type), "type值错误");
        String typeCode = typeMap.get(type);

        List<News> list = this.newsDao.query(new HashMap() {{
            put("limit", limit);
            put("type", typeCode);
            put("flag",1);
            put("status", StatusConstant.ENABLE);
        }});

        list.stream().forEach(a->dealNewsContent(a));
        return list;

    }

    /**
     * 查询几天内的资讯 按浏览量排序
     * @param type
     * @param limit
     * @return
     */
    @Transactional
    public List<News> getNewsByViewTimes(String type, Integer limit,Integer datecount) {
        Assert.notNull(type, "type不能为空！");
        Assert.notNull(limit, "查询条数不能为空");
        Assert.isTrue(datecount!=null && datecount>0, "查询几天不能为空 并且大于0");

        List<TreeBook> news_types = treeBookService.getNameAndCodeByCode("news_types");
        Map<String, String> typeMap = new HashMap() {{
            put("0", ""); //  全部
        }};
        for (TreeBook treeBook : news_types) {
            typeMap.put(treeBook.getExt1(), treeBook.getCode());
        }
        Assert.isTrue(typeMap.containsKey(type), "type值错误");
        String typeCode = typeMap.get(type);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String end = format.format(new Date());

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, -datecount);// num为增加的天数，可以改变的
        Date createTimestart = ca.getTime();
        String start = format.format(createTimestart);

        List<News> list = this.newsExtDao.query(new HashMap() {{
            put("limit", limit);
            put("type", typeCode);
            put("flag",1);
            put("status", StatusConstant.ENABLE);
            put("createTimeStart",start);
            put("createTimeEnd",end);
        }});
        list.stream().forEach(a->dealNewsContent(a));
        return list;
    }


    /**
     * 获取前一个
     *
     * @param order
     * @param typeCode
     * @return
     */
    private News getPrevious(Integer order, String typeCode) {
        if (order == null || order.intValue() <= 0) {
            return null;
        }
        List<News> newsList = this.newsDao.query(new HashMap() {{
            put("start", order - 1);
            put("limit", 1);
            put("type", typeCode);
        }});
        return newsList.isEmpty() ? null : newsList.get(0);
    }

    /**
     * 获取前一个
     *
     * @return
     */
    private News getPrevious(Long number) {
        if (number == null || number.intValue() <= 1) {
            return null;
        }
        List<News> newsList = this.newsDao.query(new HashMap() {{
            put("number", number - 1L);
        }});
        return newsList.isEmpty() ? null : newsList.get(0);
    }

    /**
     * 获取下一个
     *
     * @param order
     * @param typeCode
     * @return
     */
    private News getNext(Integer order, String typeCode) {
        Integer count = this.newsDao.count(new HashMap() {{
            put("type", typeCode);
        }});
        if (order.compareTo(count - 1) >= 0) {
            return null;
        }
        List<News> newsList = this.newsDao.query(new HashMap() {{
            put("start", order + 1);
            put("limit", 1);
            put("type", typeCode);
        }});
        return newsList.isEmpty() ? null : newsList.get(0);
    }

    /**
     * 获取前一个
     *
     * @return
     */
    private News getNext(Long number) {
        List<News> newsList = this.newsDao.query(new HashMap() {{
            put("number", number + 1L);
        }});
        return newsList.isEmpty() ? null : newsList.get(0);
    }

    /**
     * 处理内容
     *
     * @param news
     * @return
     */
    private News dealNewsContent(News news) {
        if (news == null) {
            return news;
        }
        if (!StringUtils.isEmpty(news.getNewsPhoto())) {
            for (String newsPhot : news.getNewsPhoto().split("\"")) {
                if (newsPhot.contains("http")) {
                    news.setNewsPhoto(newsPhot);
                    break;
                }
            }
        }
        if (!StringUtils.isEmpty(news.getContent())) {
            String content = news.getContent();
            String reg = "<img [^>]*src=['\"]([^'\"]+)[^>]*>";
            String contentNew = content.replaceAll(reg, "").replaceAll("&nbsp;", "").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("\\s*", "");
            news.setContent(contentNew);
        }
        return news;
    }


}
