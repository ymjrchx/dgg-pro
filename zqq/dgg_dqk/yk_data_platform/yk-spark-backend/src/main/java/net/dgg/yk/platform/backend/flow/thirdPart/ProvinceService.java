package net.dgg.yk.platform.backend.flow.thirdPart;

import com.google.gson.Gson;
import lombok.Cleanup;
import lombok.SneakyThrows;
import net.dgg.yk.platform.backend.flow.thirdPart.data.TreeBook;
import org.apache.commons.lang.StringUtils;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProvinceService {
    private String PROVINCE = "省";
    private String AUTONOMOUS_REGION = "自治区";
    private String SPECIAL_ADMINISTRATIVE_REGION = "特别行政区";
    private String CITY = "市";
    private String DISTRICT = "区";
    private String COUNTY = "县";

    private List<String> ignore_district = new ArrayList() {{
        add("社区");
        add("小区");
        add("镇区");
    }};
    private String ignore_district_regx = "[0-9a-zA-Z]区";


    private Map<Long, TreeBook> treeBookIdMap;
    private Map<String, TreeBook> treeBookNameMap;
    private Map<String, Integer> nameCountMap;

    public ProvinceService() {
        initTreeBookMap();
    }

    /**
     * 初始化 省市区 map
     */
    @SneakyThrows
    public void initTreeBookMap() {
        if (treeBookIdMap == null || treeBookNameMap == null) {
            @Cleanup ObjectInputStream ois = new ObjectInputStream(this.getClass().getResourceAsStream("/data/list"));
            List<String> jsons = (List<String>) ois.readObject();
            List<TreeBook> treeBookList = jsons.stream().map(s -> new Gson().fromJson(s, TreeBook.class)).collect(Collectors.toList());
            treeBookIdMap = new HashMap<>(treeBookList.size());
            treeBookNameMap = new HashMap<>(treeBookList.size());
            nameCountMap = new HashMap<>(treeBookList.size());
            for (TreeBook treeBook : treeBookList) {
                this.treeBookIdMap.put(treeBook.getId(), treeBook);
                this.treeBookNameMap.put(treeBook.getName(), treeBook);
                this.nameCountMap.put(treeBook.getName(), (
                        this.nameCountMap.containsKey(treeBook.getName()) ?
                                this.nameCountMap.get(treeBook.getName()) : 0) + 1);
            }
        }
    }

    /**
     * 删除 干扰 字段
     *
     * @param str
     * @return
     */
    private String deleteIgnore(String str) {
        for (String st : this.ignore_district) {
            str = str.replaceAll(st, "");
        }
        str = str.replaceAll(this.ignore_district_regx, "");
        return str;
    }

    /**
     * 获取父级
     *
     * @param id
     * @return
     */
    private TreeBook getParentTreeBook(Long id) {
        TreeBook treeBook = this.treeBookIdMap.get(id);
        return this.treeBookIdMap.get(treeBook.getPid());
    }

    /**
     * 根据数据库的配置获取从省级到此父级的地址
     *
     * @return
     */
    private String getAddressUntilTop(Long id) {
        TreeBook treeBook = this.getParentTreeBook(id);
        String s = treeBook.getName();
        while (treeBook.getPid().longValue() != 7453706779315408896L) {
            treeBook = this.getParentTreeBook(treeBook.getId());
            s = treeBook.getName().concat(s == null ? "" : s);
        }
        return s;
    }

    /**
     * 获取 区级地址
     *
     * @param str
     * @return
     */
    private String getAllSmallAddress(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        if (this.deleteIgnore(str).contains(DISTRICT)) {
            return str.substring(0, (str.contains(AUTONOMOUS_REGION) ? str.indexOf(DISTRICT, str.indexOf(AUTONOMOUS_REGION)) : str.indexOf(DISTRICT)) + 1);
        }
        if (str.contains(COUNTY)) {
            return str.substring(0, str.lastIndexOf(COUNTY) + 1);
        }
        if (str.contains(CITY)) {
            return str.substring(0, str.lastIndexOf(CITY) + 1);
        }

        return null;
    }

    /**
     * 根据 区域类型 来获取
     *
     * @param type
     * @param str
     * @return
     */
    private String getAddressByType(String type, String str) {
        this.initTreeBookMap();
        if (str.contains(type)) {
            String addr = str.substring(0, str.indexOf(type) + 1);
            // 验证 是否 唯一
            if (!this.nameCountMap.containsKey(addr) || this.nameCountMap.get(addr).intValue() > 1) {
                return null;
            }
            TreeBook treeBook = this.treeBookNameMap.get(addr);
            return this.getAddressUntilTop(treeBook.getId()).concat(this.getAllSmallAddress(str));
        }
        return null;
    }


    /**
     * 验证是都含有省市区字段 有就直接提取， 匹配失败 则返回null
     *
     * @param str
     * @return
     */
    private String getAllAddress(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        if ((str.contains(PROVINCE) || str.contains(AUTONOMOUS_REGION)
                || str.contains("北京") || str.contains("天津") || str.contains("上海") || str.contains("重庆"))  // 直辖
                && (str.contains(CITY) || str.contains(DISTRICT) || str.contains(COUNTY))) {
            if (this.deleteIgnore(str).contains(DISTRICT)) {
                str = str.substring(0, (str.contains(AUTONOMOUS_REGION) ? str.indexOf(DISTRICT, str.indexOf(AUTONOMOUS_REGION)) : str.indexOf(DISTRICT)) + 1);
                return str.substring(0, str.indexOf(DISTRICT) + 1);
            }
            if (str.contains(COUNTY)) {
                return str.substring(0, str.lastIndexOf(COUNTY) + 1);
            }

            if (str.contains(CITY)) {
                return str.substring(0, str.lastIndexOf(CITY) + 1);
            }
        }
        return null;
    }


    /**
     * 根据数据库字段补全 有省市区字段
     *
     * @param str
     * @return
     */
    private String getAddressByDataBase(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        // 含有 ”市“
        String addr = this.getAddressByType(CITY, str);
        if (!StringUtils.isEmpty(addr)) {
            return addr;
        }
        //  含有 县
        addr = this.getAddressByType(COUNTY, str);
        if (!StringUtils.isEmpty(addr)) {
            return addr;
        }
        // 含有区
        addr = this.getAddressByType(DISTRICT, str);
        if (!StringUtils.isEmpty(addr)) {
            return addr;
        }
        return null;
    }


    /**
     * @return
     */
    public String getAreaString(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            str = str.replaceAll(" ", "");
            String s = this.getAllAddress(str);
            if (!StringUtils.isEmpty(s)) {
                return s;
            }
            s = this.getAddressByDataBase(str);
            if (!StringUtils.isEmpty(s)) {
                return s;
            }
            return s;
        }
    }

}
