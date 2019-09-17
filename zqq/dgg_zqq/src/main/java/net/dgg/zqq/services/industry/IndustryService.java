package net.dgg.zqq.services.industry;

import net.dgg.zqq.dao.industry.IndustryCategorysThirdDao;
import net.dgg.zqq.dao.industry.IndustryDao;
import net.dgg.zqq.dao.order.ClassFirstDao;
import net.dgg.zqq.dao.order.ClassThirdDao;
import net.dgg.zqq.entity.industry.Industry;
import net.dgg.zqq.entity.industry.IndustryCategorysThird;
import net.dgg.zqq.entity.order.ClassFirst;
import net.dgg.zqq.entity.order.ClassThird;
import net.dgg.zqq.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author 刘阳
 * @ClassName <IndustryService>
 * @despriction
 * @create 2018/10/11 15:45
 **/
@Service
public class IndustryService {
    @Autowired
    private IndustryDao industryDao;
    @Autowired
    private IndustryCategorysThirdDao industryCategorysThirdDao;
    @Autowired
    private ClassFirstDao classFirstDao;
    @Autowired
    private ClassThirdDao classThirdDao;

    public List<Map> queryDomain(String userId) {
        //Assert.hasText(userId, "用户ID不能为空！");
        //Assert.isTrue(RedisUtils.exists(userId), "当前登陆已过期！");
        List<Industry> industryList = industryDao.query(null);
        Map<Long, Industry> idMap = new HashMap<>(industryList.size());
        Map<Long, List<Industry>> pIdListMap = new HashMap<>(industryList.size());
        for (Industry industry : industryList) {
            idMap.put(industry.getId(), industry);
            List<Industry> industries = pIdListMap.containsKey(industry.getParentId()) ? pIdListMap.get(industry.getParentId()) : new ArrayList<>();
            industries.add(industry);
            this.sortList(industries);
            pIdListMap.put(industry.getParentId(), industries);
        }
        Long top = 0L;
        List<Industry> industries = pIdListMap.get(top);
        List<Map> mapList = new ArrayList<>(industries.size());
        String sonKey = "sonArr";
        for (Industry industry : industries) {
            Map temp = MapUtils.convertBean(industry);
            temp.put(sonKey, pIdListMap.get(industry.getId()));
            mapList.add(temp);
        }
        return mapList;
    }

    /**
     * 排序
     *
     * @param industryList
     */
    private void sortList(List<Industry> industryList) {
        industryList.sort((o1, o2) -> {
            Integer s1 = o1 == null ? null : o1.getSort(), s2 = o2 == null ? null : o2.getSort();
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 != null && s2 == null) {
                return -1;
            }
            if (s1 == null && s2 != null) {
                return 1;
            }
            return s1.compareTo(s2);
        });
    }


    public List<Map> queryCommodity(Long id) {
        Assert.notNull(id, "id 不能为空！");
        Map map = new HashMap();
        map.put("industryId", id);
        List<IndustryCategorysThird> industryCategorysThirds = industryCategorysThirdDao.query(map);

        Map<String, List<String>> noMap = new HashMap<>(industryCategorysThirds.size()); // 一级编号 下的  三级编号
        Map<String, List<String>> secondNoMap = new HashMap<>(industryCategorysThirds.size()); // 二级编号下的 三级编号

        for (IndustryCategorysThird industryCategorysThird : industryCategorysThirds) {
            List<String> stringList = noMap.containsKey(industryCategorysThird.getFirstCateNo())
                    ? noMap.get(industryCategorysThird.getFirstCateNo()) : new ArrayList<>();
            stringList.add(industryCategorysThird.getThirdNo());
            noMap.put(industryCategorysThird.getFirstCateNo(), stringList);

            List<String> secStringList = secondNoMap.containsKey(industryCategorysThird.getSecondCateNo())
                    ? secondNoMap.get(industryCategorysThird.getSecondCateNo()) : new ArrayList<>();
            secStringList.add(industryCategorysThird.getThirdNo());
            secondNoMap.put(industryCategorysThird.getSecondCateNo(), secStringList);
        }
        List<Map> mapList = new ArrayList<>(noMap.size());
        for (Map.Entry<String, List<String>> entry : noMap.entrySet()) {
            List<ClassFirst> classFirsts = classFirstDao.query(new HashMap() {{
                put("number", entry.getKey());
            }});
            if (classFirsts.size() == 0) {
                continue;
            }
            List<ClassThird> classThirds = classThirdDao.query(new HashMap() {{
                put("numberArr", entry.getValue());
            }});
            if (classThirds.size() == 0) {
                continue;
            }
            classThirds = this.distinctByNumber(secondNoMap, classThirds);
            this.sortClassThirdList(classThirds);
            ClassFirst classFirst = classFirsts.get(0);
            Map temp = MapUtils.convertBean(classFirst);
            temp.put("sonArr", classThirds);
            mapList.add(temp);
        }
        this.sortMap(mapList);
        return mapList;
    }

    private void sortClassThirdList(List<ClassThird> classThirds) {
        classThirds.sort((o1, o2) -> {
            Long s1 = o1 == null ? null : o1.getId(), s2 = o2 == null ? null : o2.getId();
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 != null && s2 == null) {
                return -1;
            }
            if (s1 == null && s2 != null) {
                return 1;
            }
            return s1.compareTo(s2);
        });
    }

    private void sortMap(List<Map> mapList) {
        mapList.sort((o1, o2) -> {
            Long s1 = o1 == null || o1.get("id") == null ? null : Long.valueOf(String.valueOf(o1.get("id"))),
                    s2 = o2 == null || o2.get("id") == null ? null : Long.valueOf(String.valueOf(o2.get("id")));
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 != null && s2 == null) {
                return -1;
            }
            if (s1 == null && s2 != null) {
                return 1;
            }
            return s1.compareTo(s2);
        });
    }

    /**
     * 根据编号去重
     *
     * @param thirdList
     * @return
     */
    private List<ClassThird> distinctByNumber(Map<String, List<String>> secondNoMap, List<ClassThird> thirdList) {
        if (thirdList == null || thirdList.isEmpty()) {
            return thirdList;
        }
        Set<String> numSet = new HashSet<>(thirdList.size());
        List<ClassThird> ClassThirds = new ArrayList<>(thirdList.size());
        for (ClassThird classThird : thirdList) {
            if (!secondNoMap.containsKey(classThird.getSecondNumber())
                    || !secondNoMap.get(classThird.getSecondNumber()).contains(classThird.getNumber())
                    || numSet.contains(classThird.getNumber())) {
                continue;
            }
            numSet.add(classThird.getNumber());
            ClassThirds.add(classThird);
        }
        return ClassThirds;
    }

}
