package net.dgg.gspt.dqc.services.TrademarkSearch;

import net.dgg.gspt.dqc.dao.order.ClassFirstDao;
import net.dgg.gspt.dqc.dao.order.ClassSecondDao;
import net.dgg.gspt.dqc.dao.order.ClassThirdDao;
import net.dgg.gspt.dqc.entity.order.ClassFirst;
import net.dgg.gspt.dqc.entity.order.ClassSecond;
import net.dgg.gspt.dqc.entity.order.ClassThird;
import net.dgg.gspt.dqc.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author 刘阳
 * @ClassName <TrademarkSearchService>
 * @despriction
 * @create 2018/10/12 10:24
 **/
@Service
public class TrademarkSearchService {

    @Autowired
    private ClassFirstDao classFirstDao;
    @Autowired
    private ClassSecondDao classSecondDao;
    @Autowired
    private ClassThirdDao classThirdDao;

    public List<Map> queryTrademarkList(String userId) {
        // Assert.isTrue(RedisUtils.exists(userId), "当前登陆已过期！");
        List<Map> classFirstList = classFirstDao.queryMap(null);
        return classFirstList;
    }

    /**
     * 根据类别查询下级数据
     *
     * @param userId
     * @param number
     * @param numberType
     * @return
     */
    public List<Map> queryTrademarkParticularList(String userId, String number, String numberType) {
        //Assert.hasText(userId, "用户ID不能为空！");
        //Assert.isTrue(RedisUtils.exists(userId), "当前登陆已过期！");
        Assert.notNull(number, "编号不能为空！");
        Assert.notNull(numberType, "编号类别不能为空！");
        Assert.isTrue(Arrays.asList("two", "three").contains(numberType), "类别的值错误！");
        List<Map> dataMap = new ArrayList<>();
        if (numberType.equals("two")) { //二级
            dataMap = classSecondDao.queryMap(new HashMap() {{
                put("firstNumber", number);
            }});
        } else if (numberType.equals("three")) { //三级
            dataMap = classThirdDao.queryMap(new HashMap() {{
                put("secondNumber", number);
            }});
        }
        return dataMap;
    }

    /**
     * 根据条件查询商标数据
     *
     * @param userId
     * @param searcKey
     * @return
     */
    public List<Map> queryTrademarkSearchList(String userId, String searcKey) {
        //Assert.hasText(userId, "用户ID不能为空！");
        //Assert.isTrue(RedisUtils.exists(userId), "当前登陆已过期！");
        Assert.isTrue(!(searcKey == null || searcKey.trim().length() == 0), "请输入搜索条件！");
        //查询出3类数据
        List<ClassThird> thirdList = classThirdDao.query(new HashMap() {{
            put("searcKey", searcKey);
        }});
        List<ClassSecond> secondList = new ArrayList<>();
        List<ClassFirst> firstList = new ArrayList<>();
        Map<String, List<String>> twoMap = new HashMap<>(thirdList.size());
        Map<String, List<String>> oneMap = new HashMap<>(thirdList.size());

        // 将三级分类按二级编号 分组
        List<ClassThird> thirds = new ArrayList<>(thirdList.size());
        Map<String, List<ClassThird>> twoNumClassThirdMap = new HashMap<>();
        for (ClassThird classThird : thirdList) {
            List<ClassThird> classThirds = twoNumClassThirdMap.containsKey(classThird.getSecondNumber())
                    ? twoNumClassThirdMap.get(classThird.getSecondNumber()) : new ArrayList<>();
            classThirds.add(classThird);
            twoNumClassThirdMap.put(classThird.getSecondNumber(), classThirds);
        }


        // 二级分类按一级编号分组 并将 三级分类数组放入对应父级二级分类Map sonkey 属性
        Map<String, ClassSecond> classSecondMap = new HashMap<>(twoNumClassThirdMap.size());
        Map<String, Map> classSecondMapMap = new HashMap<>(twoNumClassThirdMap.size());

        Map<String, List<ClassSecond>> firstNumberSecondMap = new HashMap<>(thirdList.size());

        String sonKey = "sonArr";
        //查询二级分类
        for (Map.Entry<String, List<ClassThird>> entry : twoNumClassThirdMap.entrySet()) {

            ClassSecond classSecond = classSecondDao.findByNumber(entry.getKey());
            if (classSecond == null) {
                continue;
            }
            classSecondMap.put(classSecond.getNumber(), classSecond);
            // 组装二级map 级 和三级子类数组
            Map temp = MapUtils.convertBean(classSecond);
            temp.put(sonKey, twoNumClassThirdMap.get(classSecond.getNumber()));
            classSecondMapMap.put(classSecond.getNumber(), temp);

            //按一级分组
            List<ClassSecond> seconds = firstNumberSecondMap.containsKey(classSecond.getFirstNumber())
                    ? firstNumberSecondMap.get(classSecond.getFirstNumber()) : new ArrayList<>();
            seconds.add(classSecond);
            firstNumberSecondMap.put(classSecond.getFirstNumber(), seconds);
        }

        // 循环查询 和组装 一级
        List<Map> mapList = new ArrayList<>();
        for (Map.Entry<String, List<ClassSecond>> entry : firstNumberSecondMap.entrySet()) {
            ClassFirst classFirst = classFirstDao.findByNumber(entry.getKey());
            Map temp = MapUtils.convertBean(classFirst);

            // 获取二级子集数组
            List<ClassSecond> sonClassSecondList = firstNumberSecondMap.get(classFirst.getNumber());
            List<Map> sonClassSecondMapList = new ArrayList<>(sonClassSecondList.size());
            for (ClassSecond second : sonClassSecondList) {
                Map secondMap = classSecondMapMap.get(second.getNumber());
                sonClassSecondMapList.add(secondMap);
            }
            temp.put(sonKey, sonClassSecondMapList);
            mapList.add(temp);
        }
        return mapList;
    }
}
