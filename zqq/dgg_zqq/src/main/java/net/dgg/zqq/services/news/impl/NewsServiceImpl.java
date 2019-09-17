package net.dgg.zqq.services.news.impl;

import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.dao.TreeBookDao;
import net.dgg.zqq.dao.news.NewsDao;
import net.dgg.zqq.dao.news.NewsExtDao;
import net.dgg.zqq.dao.news.NewsLabelDao;
import net.dgg.zqq.dao.news.NewsLabelExtDao;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.entity.news.News;
import net.dgg.zqq.entity.news.NewsLabel;
import net.dgg.zqq.services.news.NewsService;
import net.dgg.zqq.utils.MapUtils;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;
    @Autowired
    private TreeBookDao treeBookDao;
    @Autowired
    private NewsLabelDao newsLabelDao;
    @Autowired
    private NewsLabelExtDao newsLabelExtDao;
    @Autowired
    private NewsExtDao newsExtDao;

    @Override
    @Transactional
    public void save(News news, String content, String newsPhoto) {
        Assert.notNull(sessionManager.getCurrentSession(), "请重新登录");
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.hasText(newsPhoto, "展示照片不能为空！");
        Assert.hasText(content, "内容不能为空");

        Assert.isTrue(newsPhoto.length() <= 500, "图片地址过长，请重新上传图片");
        Assert.notNull(news.getTypeLevel1Name(), "服务大类不能为空！");
        Assert.notNull(news.getTitle(), "标题不能为空！");
        Assert.notNull(news.getLabel(), "标签不能为空！");
        Assert.notNull(news.getOrigin(), "来源不能为空！");
        Assert.notNull(news.getType(), "分类不能为空！");
        Assert.notNull(news.getStatus(), "状态不能为空！");
        Assert.notNull(news.getFlag(), "正常删除不能为空！");
        news.setContent(content);
        news.setNewsPhoto(newsPhoto);
        news.setFlag(1);
        news.setStatus(StatusConstant.ENABLE);
        news.setViewTimes(0);
        news.setPraiseTimes(0);
        Long newsId = KeyWorker.nextId();
        news.setId(newsId);
        //news.setCreateUser(userEntity);
        news.setCreaterId(userId);
        news.setCreaterName(userEntity.getRealName());
        news.setCreateTime(news.getCreateTime() == null ? new Date() : news.getCreateTime());
        Integer count = this.newsDao.count(null);
        news.setNumber(Long.valueOf(count + 1));
        newsDao.save(news);

        //保存新闻标签到数据库中
        Long labelId = KeyWorker.nextId();
        NewsLabel newsLabel = new NewsLabel();
        newsLabel.setId(labelId);
        newsLabel.setNewsId(newsId);
        newsLabel.setNewsLabel(news.getLabel());
        newsLabel.setCreateUser(userEntity);
        newsLabelDao.save(newsLabel);
    }


    public void excSave(News news) {
        Assert.notNull(sessionManager.getCurrentSession(), "请重新登录");
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        news.setFlag(1);
        news.setStatus(StatusConstant.ENABLE);
        news.setViewTimes(0);
        news.setPraiseTimes(0);
        Long newsId = KeyWorker.nextId();
        news.setId(newsId);
        news.setCreateUser(userEntity);

        //保存新闻标签到数据库中
        Long labelId = KeyWorker.nextId();
        NewsLabel newsLabel = new NewsLabel();
        newsLabel.setId(labelId);
        newsLabel.setNewsId(newsId);
        newsLabel.setNewsLabel(news.getLabel());
        newsLabel.setCreateUser(userEntity);
        newsLabelDao.save(newsLabel);

        newsDao.save(news);
    }

    @Override
    @Transactional
    public void update(News news, String content, String newsPhoto) {
        Assert.notNull(sessionManager.getCurrentSession(), "请重新登录");
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");

        Assert.hasText(newsPhoto, "修改展示图片为空!");
        Assert.hasText(content, "文本内容不能为空!");

        Assert.isTrue(newsPhoto.length() <= 500, "图片地址过长，请重新上传图片");
        Assert.notNull(news.getId(), "修改咨询时ID不能为空！");
        Assert.hasText(news.getTypeLevel1Name(), "服务大类不能为空！");
        Assert.hasText(news.getTitle(), "标题不能为空！");
        Assert.hasText(news.getLabel(), "标签不能为空！");
        Assert.hasText(news.getOrigin(), "来源不能为空！");
        Assert.hasText(news.getType(), "分类不能为空！");
        Assert.hasText(news.getStatus(), "状态不能为空！");
        Assert.notNull(news.getFlag(), "正常删除不能为空！");
        Assert.notNull(news.getCreateTime(), "创建时间不能为空！");

        News newsById = newsDao.findById(news.getId());
        Assert.notNull(newsById, "ID传递错误");

        newsById.setTypeLevel1Name(news.getTypeLevel1Name());
        newsById.setTitle(news.getTitle());
        newsById.setLabel(news.getLabel());
        newsById.setOrigin(news.getOrigin());
        newsById.setType(news.getType());
        newsById.setStatus(news.getStatus());
        newsById.setFlag(news.getFlag());
        newsById.setContent(content);
        newsById.setNewsPhoto(newsPhoto);
        newsById.setUpdaterUser(userEntity);
        newsById.setCreateTime(news.getCreateTime());
        newsDao.update(newsById);

        //修改标签
        NewsLabel newsLabel = newsLabelExtDao.findByNewsId(news.getId());
        if (newsLabel == null) {
            newsLabel = new NewsLabel();
            Long labelId = KeyWorker.nextId();
            newsLabel.setId(labelId);
            newsLabel.setNewsId(news.getId());
            newsLabel.setNewsLabel(news.getLabel());
            newsLabel.setCreateUser(userEntity);
            newsLabelDao.save(newsLabel);
        } else {
            if (StringUtils.hasText(newsLabel.getNewsLabel())) {
                if (news.getLabel().equals(newsLabel.getNewsLabel())) {
                    //标签没变
                    return;
                }
            }
            newsLabel.setNewsLabel(news.getLabel());
            newsLabelDao.update(newsLabel);
        }

    }

    @Override
    @Transactional
    public News findById(Long id) {
        Assert.notNull(id, "查询条件id为空");
        News byId = newsDao.findById(id);
        return byId;
    }

    @Override
    @Transactional
    public List<News> query(Map map) {
        Assert.notNull(map, "query查询条件为空");
        return newsDao.query(map);
    }

    @Override
    @Transactional
    public List<Map> queryMap(Map map) {
        Assert.notNull(map, "querymap查询条件为空");
        List<Map> maps = newsDao.queryMap(map);
        return maps;
    }

    @Override
    @Transactional
    public Map showAllNews(int pageSize, int pageNum, String type, String order) {
        List<Map> maps;
        Map resultMap = new HashMap();
        Map params = new HashMap();
        List<TreeBook> status = treeBookDao.getNameAndCodeByCode("status");
        for (TreeBook treeBook : status) {
            if (treeBook.getName().equals("启用")) {
                params.put("status", treeBook.getCode());
            }
        }
        params.put("start", (pageNum - 1) * pageSize);
        params.put("limit", pageSize);
        params.put("flag", 1);
        params.put("status", StatusConstant.ENABLE);

        if (type != null) {
            params.put("type", type);
        }
        maps = newsDao.queryMap(params);
       /* if (StringUtils.hasText(order)){
            maps.sort(new Comparator<Map>() {
                @Override
                public int compare(Map o1, Map o2) {
                    return (int) o2.get("viewTimes")-(int)o1.get("viewTimes");
                }
            });
        }*/
        for (Map map : maps) {
            doShowNewsPhoto(map);
            doShowNewsContent(map);
        }
        Integer count = newsDao.count(params);
        resultMap.put("data", maps);
        resultMap.put("count", count);
        return resultMap;
    }


    @Override
    public Map pageQueryByLabel(Integer pageSize, Integer pageNum, String label) {
        List<Map> maps;

        Map resultMap = new HashMap();
        Map params = new HashMap();

        if (pageSize == null || pageSize < 0) {
            pageSize = 10; //如果不设置 默认查询10条
        }
        if (pageNum == null || pageNum < 0) {
            pageNum = 1;  //如果不设置页码 默认查询第一页
        }
        Integer start = (pageNum - 1) * pageSize;
        Integer limit = pageSize;

        params.put("start", start);
        params.put("limit", limit);
        params.put("flag", 1);
        params.put("status", StatusConstant.ENABLE);
        params.put("label", label);

        maps = newsDao.queryMap(params);

        for (Map map : maps) {
            doShowNewsPhoto(map);
            doShowNewsContent(map);
        }
        Integer count = newsDao.count(params);
        resultMap.put("data", maps);
        resultMap.put("count", count);
        return resultMap;
    }


    @Override
    @Transactional
    public Integer praise(Long id) {
        newsDao.updatePraise(id);
        News byId = newsDao.findById(id);
        return byId.getPraiseTimes();
    }

    @Override
    @Transactional
    public Map showProtalData(String typeOne, String typeTwo, String typeThree) {
        List<TreeBook> news_types = treeBookDao.getNameAndCodeByCode("news_types");
        List<TreeBook> status = treeBookDao.getNameAndCodeByCode("status");
        Map result = new HashMap();
        Map params = new HashMap();
        for (TreeBook treeBook : status) {
            if (treeBook.getName().equals("启用")) {
                params.put("status", treeBook.getCode());
            }
        }
        params.put("start", 0);
        params.put("limit", 4);
        params.put("flag", 1);
        params.put("status", StatusConstant.ENABLE);

        params.put("type", typeOne);
        List<Map> mapsOne = newsDao.queryMap(params);
        for (Map map : mapsOne) {
            for (TreeBook treeBook : news_types) {
                if (map.get("type").equals(treeBook.getCode())) {
                    map.put("type_c", treeBook.getName());
                }
            }
            doShowNewsPhoto(map);
            doShowNewsContent(map);
        }

        params.put("type", typeTwo);
        List<Map> mapsTwo = newsDao.queryMap(params);
        for (Map map : mapsTwo) {
            for (TreeBook treeBook : news_types) {
                if (map.get("type").equals(treeBook.getCode())) {
                    map.put("type_c", treeBook.getName());
                }
            }
            doShowNewsPhoto(map);
            doShowNewsContent(map);
        }
        params.put("type", typeThree);
        List<Map> mapsThree = newsDao.queryMap(params);
        for (Map map : mapsThree) {
            for (TreeBook treeBook : news_types) {
                if (map.get("type").equals(treeBook.getCode())) {
                    map.put("type_c", treeBook.getName());
                }
            }
            doShowNewsPhoto(map);
            doShowNewsContent(map);
        }
        result.put("one", mapsOne);
        result.put("two", mapsTwo);
        result.put("three", mapsThree);
        return result;
    }

    @Override
    public Integer count(Map map) {
        Assert.notNull(map, "count查询条件为空");
        return newsDao.count(map);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(sessionManager.getCurrentSession(), "请重新登录");
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        Assert.notNull(userId, "未获取到用户ID");
        UserEntity userEntity = userRecorderDAO.findUserEntityByUserId(userId);
        Assert.notNull(userEntity, "未查询到当前登录用户！");
        Assert.notNull(id, "id不能为空！");
        News byId = this.newsDao.findById(id);
        Assert.notNull(byId, "未查询到数据！");
        this.newsDao.deleteById(id);
    }

    @Override
    @Transactional
    public Map getNewsContent(Long id, String type, Integer pageSize, Integer pageNum) {
        Assert.isTrue(id != null, "id不能为空");
        //Assert.isTrue(pageSize != null && pageSize > 0, "pageSize不能为空");
        // Assert.isTrue(pageNum != null && pageNum > 0, "pageNum不能为空");
        //Assert.hasText(type,"type不能为空");

        News newSys = newsDao.findById(id);
        Assert.notNull(newSys, "ID传递错误");
        newsDao.updateViewTimes(id);

        Map temp = MapUtils.convertBean(newSys);
        this.doShowNewsContent(temp);
        temp.put("description", temp.get("content"));
        temp.put("content", newSys.getContent());
        Map result = new HashMap(); // getMap(id, news);
        result.put("now", temp);
        /*if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }

        Integer start = (pageNum - 1) * pageSize;
        if (start - pageSize >= 0) {
            start = start - pageSize;
        }

        Integer limit = pageSize * 3;

        Map query = new HashMap();
        query.put("start", start);
        query.put("limit", limit);
        query.put("flag", 1);
        query.put("status", StatusConstant.ENABLE);
        if (StringUtils.hasText(type)) {
            query.put("type", type);
        }

        List<News> news = newsDao.query(query);*/


        //result.put("now", newSys);
        return result;
    }


    @Override
    public Map getNewsContentByLabel(Long id, String label, Integer pageSize, Integer pageNum) {
        Assert.isTrue(id != null, "id不能为空");
        //Assert.isTrue(pageSize != null && pageSize > 0, "pageSize不能为空");
        Assert.isTrue(pageNum != null && pageNum > 0, "pageNum不能为空");
        Assert.hasText(label, "标签不能为空");

        News newSys = newsDao.findById(id);
        Assert.notNull(newSys, "ID传递错误");
        Assert.isTrue(StringUtils.hasText(newSys.getLabel()) && label.equals(newSys.getLabel()), "ID传递错误");
        newsDao.updateViewTimes(id);

        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }

        Integer start = (pageNum - 1) * pageSize;
        if (start - pageSize >= 0) {
            start = start - pageSize;
        }

        Integer limit = pageSize * 3;

        Map query = new HashMap();
        query.put("start", start);
        query.put("limit", limit);
        query.put("flag", 1);
        query.put("status", StatusConstant.ENABLE);
        query.put("label", label);
        List<News> news = newsDao.query(query);

        Map result = getMap(id, news);
        return result;
    }

    @Override
    @Transactional
    public List<News> findByLabel(Integer limit, String newsLabels) {
        if(limit==null && limit<0){
            limit = 10;
        }

        Assert.hasText(newsLabels,"标签不能为空");
        String[] labels = newsLabels.split(",");

        if(labels!=null && labels.length>0){
            Map map = new HashMap();
            map.put("labels",labels);
            map.put("limit",limit);
            map.put("flag", 1);
            map.put("status", StatusConstant.ENABLE);
            return newsExtDao.findByLabel(map);
        }

        return null;
    }

    private Map getMap(Long id, List<News> news) {
        Map result = new HashMap();
        if (news.size() > 0) {
            for (int j = 0; j < news.size(); j++) {
                News news1 = news.get(j);
                if (news1.getId().equals(id)) {
                    // 当前资讯内容
                    Map temp = MapUtils.convertBean(news1);
                    this.doShowNewsContent(temp);
                    temp.put("description", temp.get("content"));
                    temp.put("content", news1.getContent());
                    result.put("now", temp);
                    if (j < news.size() - 1) {
                        //当前资讯下一篇
                        News newsNext = news.get(j + 1);
                        if (newsNext != null) {
                            if (!StringUtils.isEmpty(newsNext.getNewsPhoto())) {
                                for (String newsPhot : newsNext.getNewsPhoto().split("\"")) {
                                    if (newsPhot.contains("http")) {
                                        newsNext.setNewsPhoto(newsPhot);
                                        break;
                                    }
                                }
                            }
                            if (!StringUtils.isEmpty(newsNext.getContent())) {
                                String content = newsNext.getContent();
                                String reg = "<img [^>]*src=['\"]([^'\"]+)[^>]*>";
                                String contentNew = content.replaceAll(reg, "").replaceAll("&nbsp;", "").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("\\s*", "");
                                newsNext.setContent(contentNew);
                            }
                            result.put("next", newsNext);
                        }
                    }
                    if (j > 0) {
                        //当前资讯上一篇
                        News newsPrevious = news.get(j - 1);
                        if (newsPrevious != null) {
                            if (!StringUtils.isEmpty(newsPrevious.getNewsPhoto())) {
                                for (String newsPhot : newsPrevious.getNewsPhoto().split("\"")) {
                                    if (newsPhot.contains("http")) {
                                        newsPrevious.setNewsPhoto(newsPhot);
                                        break;
                                    }
                                }
                            }
                            if (!StringUtils.isEmpty(newsPrevious.getContent())) {
                                String content = newsPrevious.getContent();
                                String reg = "<img [^>]*src=['\"]([^'\"]+)[^>]*>";
                                String contentNew = content.replaceAll(reg, "").replaceAll("&nbsp;", "").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("\\s*", "");
                                newsPrevious.setContent(contentNew);
                            }
                            result.put("previous", newsPrevious);
                        }
                    }
                    break;
                }
            }
        }
        return result;
    }


   /* @Override
    @Transactional
    public Map getNewsContent(Long id) {
        newsDao.updateViewTimes(id);
        List<News> news = newsDao.query(new HashMap());
        Map result = new HashMap();


        for (int j = 0; j < news.size(); j++) {
            News news1 = news.get(j);
            if (news1.getId().equals(id)) {
                // 当前资讯内容
                Map temp = MapUtils.convertBean(news1);
                this.doShowNewsContent(temp);
                temp.put("description", temp.get("content"));
                temp.put("content", news1.getContent());
                result.put("now", temp);
                if (j < news.size() - 1) {
                    //当前资讯下一篇
                    News newsNext = news.get(j + 1);
                    for (String newsPhot : newsNext.getNewsPhoto().split("\"")) {
                        if (newsPhot.contains("http")) {
                            newsNext.setNewsPhoto(newsPhot);
                            break;
                        }
                    }
                    String content = newsNext.getContent();
                    String reg = "<img [^>]*src=['\"]([^'\"]+)[^>]*>";
                    String noImg = content.replaceAll(reg, "");
                    String noNbsp = noImg.replaceAll("&nbsp;", "");
                    String noP1 = noNbsp.replaceAll("<p>", "");
                    String noP2 = noP1.replaceAll("</p>", "");
                    String contentNew = noP2.replaceAll("\\s*", "");
                    newsNext.setContent(contentNew);
                    result.put("next", newsNext);
                }
                if (j > 0) {
                    //当前资讯上一篇
                    News newsPrevious = news.get(j - 1);
                    if (!StringUtils.isEmpty(newsPrevious.getNewsPhoto())) {
                        for (String newsPhot : newsPrevious.getNewsPhoto().split("\"")) {
                            if (newsPhot.contains("http")) {
                                newsPrevious.setNewsPhoto(newsPhot);
                                break;
                            }
                        }
                    }
                    if (!StringUtils.isEmpty(newsPrevious.getContent())) {
                        String content = newsPrevious.getContent();
                        String reg = "<img [^>]*src=['\"]([^'\"]+)[^>]*>";
                        String contentNew = content.replaceAll(reg, "").replaceAll("&nbsp;", "").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("\\s*", "");
                        newsPrevious.setContent(contentNew);
                    }
                    result.put("previous", newsPrevious);
                }
                break;
            }
        }
        return result;
    }*/


    private void doShowNewsPhoto(Map map) {
        String newsPhoto = (String) map.get("newsPhoto");
        if (newsPhoto != null && !" ".equals(newsPhoto)) {
            String[] split = newsPhoto.split("\"");
            for (String s : split) {
                if (s.contains("http")) {
                    newsPhoto = s;
                    break;
                }
            }
            map.put("newsPhoto", newsPhoto);
        }
    }

    private void doShowNewsContent(Map map) {
        String content = (String) map.get("content");
        if (content != null && !" ".equals(content)) {

            String reg = "<img [^>]*src=['\"]([^'\"]+)[^>]*>";
            String noImg = content.replaceAll(reg, "");
            String html = noImg.replaceAll("<.*?>", " ").replaceAll("", "");
            String s2 = html.replaceAll("<.*?", "");

            String noNbsp = s2.replaceAll("&nbsp;", "");
            String noP = noNbsp.replaceAll("<p>", "").replaceAll("</p>", "");
            String contentNew = noP.replaceAll("\\s*", "");

            map.put("content", contentNew);
        }

    }
}
