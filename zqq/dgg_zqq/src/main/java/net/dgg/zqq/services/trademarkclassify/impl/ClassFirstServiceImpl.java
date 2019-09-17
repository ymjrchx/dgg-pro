package net.dgg.zqq.services.trademarkclassify.impl;

import net.dgg.zqq.dao.order.ClassFirstDao;
import net.dgg.zqq.entity.order.ClassFirst;
import net.dgg.zqq.services.trademarkclassify.ClassFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author huanggai
 * @creatTime 11:04 2018/9/30
 */
@Service
public class ClassFirstServiceImpl implements ClassFirstService {
    @Autowired
    private ClassFirstDao classFirstDao;

    @Override
    public void save(ClassFirst classFirst) {
        Assert.notNull(classFirst,"保存数据为空!");
        classFirstDao.save(classFirst);
    }

    @Override
    public void update(ClassFirst classFirst) {
        Assert.notNull(classFirst,"更新数据为空!");
        classFirstDao.update(classFirst);
    }

    @Override
    public ClassFirst findById(Long id) {
        Assert.notNull(id,"查询数据为空!");
        return classFirstDao.findById(id);
    }

    @Override
    public List<ClassFirst> query(Map map) {
        Assert.notNull(map,"query查询数据为空!");
        return classFirstDao.query(map);
    }

    @Override
    public List<Map> queryMap(Map map) {
        return classFirstDao.queryMap(map);
    }

    @Override
    public Integer count(Map map) {
        Assert.notNull(map,"count查询数据为空!");
        return classFirstDao.count(map);
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id,"deleteById查询数据为空!");
        classFirstDao.deleteById(id);
    }
}
