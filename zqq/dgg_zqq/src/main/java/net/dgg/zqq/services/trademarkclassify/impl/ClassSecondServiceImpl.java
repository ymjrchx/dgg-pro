package net.dgg.zqq.services.trademarkclassify.impl;

import net.dgg.zqq.dao.order.ClassSecondDao;
import net.dgg.zqq.entity.order.ClassSecond;
import net.dgg.zqq.services.trademarkclassify.ClassSecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author huanggai
 * @creatTime 11:15 2018/9/30
 */
@Service
public class ClassSecondServiceImpl implements ClassSecondService
{
    @Autowired
    private ClassSecondDao classSecondDao;

    @Override
    public void save(ClassSecond ClassSecond) {
        Assert.notNull(ClassSecond,"保存数据为空!");
        classSecondDao.save(ClassSecond);
    }

    @Override
    public void update(ClassSecond ClassSecond) {
        Assert.notNull(ClassSecond,"更新数据为空!");
        classSecondDao.update(ClassSecond);
    }

    @Override
    public ClassSecond findById(Long id) {
        Assert.notNull(id,"查询数据为空!");
        return classSecondDao.findById(id);
    }

    @Override
    public List<ClassSecond> query(Map map) {
        Assert.notNull(map,"query查询数据为空!");
        return classSecondDao.query(map);
    }

    @Override
    public List<Map> queryMap(Map map) {
        Assert.notNull(map,"querymap查询数据为空!");
        return classSecondDao.queryMap(map);
    }

    @Override
    public Integer count(Map map) {
        Assert.notNull(map,"count查询数据为空!");
        return classSecondDao.count(map);
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id,"deleteById查询数据为空!");
        classSecondDao.deleteById(id);
    }
}
