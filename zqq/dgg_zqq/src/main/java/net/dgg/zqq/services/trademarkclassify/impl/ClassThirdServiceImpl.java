package net.dgg.zqq.services.trademarkclassify.impl;

import net.dgg.zqq.dao.order.ClassFirstDao;
import net.dgg.zqq.dao.order.ClassSecondDao;
import net.dgg.zqq.dao.order.ClassThirdDao;
import net.dgg.zqq.entity.order.ClassFirst;
import net.dgg.zqq.entity.order.ClassSecond;
import net.dgg.zqq.entity.order.ClassThird;
import net.dgg.zqq.services.trademarkclassify.ClassThirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author huanggai
 * @creatTime 11:15 2018/9/30
 */
@Service
public class ClassThirdServiceImpl implements ClassThirdService
{
    @Autowired
    private ClassThirdDao classThirdDao;
    @Autowired
    private ClassFirstDao classFirstDao;
    @Autowired
    private ClassSecondDao classSecondDao;

    @Override
    public void save(ClassThird ClassThird) {
        Assert.notNull(ClassThird,"保存数据为空!");
        classThirdDao.save(ClassThird);
    }

    @Override
    public void update(ClassThird ClassThird) {
        Assert.notNull(ClassThird,"更新数据为空!");
        classThirdDao.update(ClassThird);
    }

    @Override
    public ClassThird findById(Long id) {
        Assert.notNull(id,"查询数据为空!");
        return classThirdDao.findById(id);
    }

    @Override
    public List<ClassThird> query(Map map) {
        Assert.notNull(map,"query查询数据为空!");
        return classThirdDao.query(map);
    }

    @Override
    public Map<String,Object> queryByKey(String key) {
        if (StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("请输入关键字");
        }
        Map<String,Object> resultMap = new LinkedHashMap();
        Map<String,List<ClassThird>> classSecondMap = new LinkedHashMap();
        Map<String,Map<String,Object>> classFirstMap = new LinkedHashMap();
        List<ClassThird> classThirds = classThirdDao.queryByKey(key);
        classThirds.sort(new Comparator<ClassThird>() {
            @Override
            public int compare(ClassThird o1, ClassThird o2){
                return Integer.parseInt(o1.getSecondNumber())-Integer.parseInt(o2.getSecondNumber());
            }
        });
        /**
         * 根据三级分类找出对应的一二级分类
         */for (ClassThird classThird : classThirds) {
            boolean secondFlag = true;
            boolean firstFlag = true;
            String name = classThird.getName();
            if (name.contains(key))
            {
                Long secondId = classThird.getSecondId();
                ClassSecond classSecond = classSecondDao.findById(secondId);
                List<ClassThird> tempList = classSecondMap.get(classSecond.getNumber() + classSecond.getName());
                if (tempList==null){
                    List<ClassThird> temps = new ArrayList<>();
                    temps.add(classThird);
                    classSecondMap.put(classSecond.getNumber() + classSecond.getName(),temps);
                }else {
                    for (ClassThird classThird1:tempList){
                        if (classThird.getId().equals(classThird1.getId())){
                            secondFlag = false;
                            break;
                        }
                    }
                    if (secondFlag){
                        tempList.add(classThird);
                    }
                }
                Long firstId = classSecond.getFirstId();
                ClassFirst classFirst = classFirstDao.findById(firstId);

                Map<String, Object> stringListMap = classFirstMap.get(classFirst.getNumber() + classFirst.getName());
                if (stringListMap==null){
                    Map<String,Object> tempMap = new HashMap<>();
                    List<ClassSecond> tempClassSeconds = new ArrayList<>();
                    tempClassSeconds.add(classSecond);
                    tempMap.put("annotation",classFirst.getRemark());
                    tempMap.put("data",tempClassSeconds);
                    classFirstMap.put(classFirst.getNumber() + classFirst.getName(),tempMap);
                }else {
                    List<ClassSecond> oldList = (List)stringListMap.get("data");
                    if (oldList==null){
                        List<ClassSecond> temps = new ArrayList<>();
                        temps.add(classSecond);
                        stringListMap.put("annotation",classFirst.getRemark());
                        stringListMap.put("data",temps);
                    }else {
                        for (ClassSecond classSecond1:oldList){
                            if (classSecond.getId().equals(classSecond1.getId())){
                                firstFlag = false;
                                break;
                            }
                        }
                        if (firstFlag){
                            oldList.add(classSecond);
                        }
                    }
                }
                String replace = name.replace(key, "<font class='keyword'>" + key + "</font>");
                classThird.setName(replace);
            }
        }
        resultMap.put("first",classFirstMap);
        resultMap.put("second",classSecondMap);

        return resultMap;
    }

    @Override
    public List<Map> queryMap(Map map) {
        Assert.notNull(map,"querymap查询数据为空!");
        return classThirdDao.queryMap(map);
    }

    @Override
    public Integer count(Map map) {
        Assert.notNull(map,"count查询数据为空!");
        return classThirdDao.count(map);
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id,"deleteById查询数据为空!");
        classThirdDao.deleteById(id);
    }
}
