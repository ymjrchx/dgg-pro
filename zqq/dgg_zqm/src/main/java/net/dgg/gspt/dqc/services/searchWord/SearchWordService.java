package net.dgg.gspt.dqc.services.searchWord;

import net.dgg.gspt.dqc.dao.searchWord.SearchWordDao;
import net.dgg.gspt.dqc.dao.searchWord.SearchWordExtDao;
import net.dgg.gspt.dqc.entity.searchWord.SearchWord;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/27 11:23
 */
@Service
public class SearchWordService {


    @Autowired
    SearchWordDao searchWordDao;
    @Autowired
    private SearchWordExtDao searchWordExtDao;

    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.searchWordDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.searchWordDao.queryMap(query);
        }
        return Collections.emptyList();
    }

    /**
     * 根据id删除搜索词
     */
    @Transactional
    public void deleteWordById(Long id) {
        this.searchWordDao.deleteById(id);
    }

    /**
     * 后台 管理添加操作
     */
    @Transactional
    public void addWord(String word, int flag) {
        Assert.notNull(word, "搜索词不能为空");
        Assert.isTrue(word.length() < 20, "搜索词长度小于应小于20");
        List<SearchWord> list = this.searchWordDao.query(new HashMap() {{
            put("word", word);
        }});
        Assert.isTrue(list.size() == 0, "该搜索词已存在");
        SearchWord searchWord = new SearchWord();
        searchWord.setId(KeyWorker.nextId());
        searchWord.setWord(word);
        searchWord.setFlag(flag);
        searchWord.setTime(1);
        searchWord.setCreateTime(new Date());
        this.searchWordDao.save(searchWord);
    }

    /**
     * 后台 管理添加操作(Excel导入的添加方法)
     */
    @Transactional
    public void addWord1(String word) {
        Assert.notNull(word, "搜索词不能为空");
        Assert.isTrue(word.length() < 20, "搜索词长度小于应小于20");
        List<SearchWord> list = this.searchWordDao.query(new HashMap() {{
            put("word", word);
        }});
        if(list.size()==0){
            SearchWord searchWord = new SearchWord();
            searchWord.setId(KeyWorker.nextId());
            searchWord.setWord(word);
            searchWord.setFlag(0);
            searchWord.setTime(1);
            searchWord.setCreateTime(new Date());
            this.searchWordDao.save(searchWord);
        }

    }

    /**
     * 启用禁用操作
     */
    @Transactional
    public void updateFlag(Long id) {
        SearchWord searchWord = this.searchWordDao.findById(id);
        if (searchWord.getFlag() == 1) {
            searchWord.setFlag(0);
        } else searchWord.setFlag(1);
        this.searchWordDao.update(searchWord);
    }


    /**
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Map pageQuery(Integer pageSize, Integer pageNum) {
        if(pageSize==null || pageSize<0){
            pageSize=10; //如果不设置 默认查询10条
        }
        if(pageNum==null || pageNum<0){
            pageNum=1;  //如果不设置页码 默认查询第一页
        }
        Integer start = (pageNum-1)*pageSize;
        Integer limit = pageSize;
        Integer count = searchWordDao.count(new HashMap(){{
            put("flag",1);
        }});
        if(count==null || count<0){
            return null;
        }
        List<SearchWord> list = searchWordExtDao.query(new HashMap(){{
            put("flag",1);
            put("start",start);
            put("limit",limit);
        }});
        Map map = new HashMap();
        map.put("count",count);
        map.put("list",list);
        return map;
    }


    /**
     *
     * 记录客户搜索词条
     * @param word 客户搜索词
     */
    @Transactional
    public void search(String word, String ip) {
        Assert.hasText(word,"客户搜索词不能为空");
        if(word.length()>50){
            word = word.substring(50);
        }

        //查询表中是否存在
        Map map = new HashMap();
        map.put("word",word);
        map.put("ip", ip);
        List<SearchWord> list = searchWordDao.query(map);
        SearchWord searchWord = null;

        //存在
        if(list!=null && list.size()>0){
            searchWord = list.get(0);
            searchWord.setTime(searchWord.getTime()+1);
            searchWordDao.update(searchWord);
            return ;
        }

        //不存在
        searchWord = new SearchWord();
        searchWord.setId(KeyWorker.nextId());
        searchWord.setTime(1);
        searchWord.setFlag(1);
        searchWord.setWord(word);
        searchWord.setIp(ip);
        searchWord.setCreateTime(new Date());
        searchWordDao.save(searchWord);

    }
}
