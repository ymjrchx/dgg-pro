package net.dgg.gspt.dqc.services.checkCompany;

import com.google.gson.Gson;
import net.dgg.gspt.dqc.dao.TreeBookDao;
import net.dgg.gspt.dqc.dao.checkCompanyRecord.CheckCompanyRecordDao;
import net.dgg.gspt.dqc.entity.checkCompany.CheckCompanyRecord;
import net.dgg.gspt.dqc.framework.PTConstOld;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.services.TreeBookService;
import net.dgg.gspt.dqc.services.UserService;
import net.dgg.gspt.dqc.services.searchWord.SearchWordService;
import net.dgg.gspt.dqc.utils.StringUtils;
import net.dgg.gspt.dqc.utils.esOld.services.EsServiceOld;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 刘阳
 * @ClassName <CheckCompanyService>
 * @despriction 核名service
 * @create 2018/07/20 9:30
 **/
@Service
public class CheckCompanyService {
    private static String HIGH = "H";
    private static String MEDIUM = "M";
    private static String LOW = "L";

    //@Value("${check.index}")
    private String index = "company_name_v1";

    ///@Value("${check.type}")
    private String type = "company_name";

    private String field = "companyName";

    private Gson gson = new Gson();

    private Integer highlightDefault = 1;


    @Autowired
    private CheckCompanyRecordDao checkCompanyRecordDao;
    @Autowired
    private UserService userService;
    @Autowired
    private EsServiceOld esService;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private SearchWordService searchWordService;
    @Autowired
    private TreeBookService treeBookService;
    @Autowired
    private TreeBookDao treeBookDao;


    /**
     * 核名 公共参数验证
     *
     * @param city
     * @param name
     * @param industry
     * @param companyType
     */
    private void checkCompanyParam(String city, String name, String industry, String companyType) {
        String blank = " ", emptyStr = "";

        Assert.hasText(city, "行政区划不能能为空");
        Assert.hasText(city = city.replaceAll(blank, emptyStr), "行政区划不能全部是空格！");
        Assert.isTrue(city.length() < 15, "行政区划的字符数目不能大于14！");
        Assert.isTrue(StringUtils.isOnlySimpleChinese(city), "行政区划只能是简体中文！");


        Assert.hasText(name, "企业名不能为空！");
        Assert.hasText(name = name.replaceAll(blank, emptyStr), "企业名字不能全部是空格！");
        Assert.isTrue(name.length() < 10, "企业名字的字符数目不能大于9！");
        Assert.isTrue(StringUtils.isOnlySimpleChinese(name), "企业名只能是简体中文！");

        if (!RedisUtils.exists(PTConstOld.TREE_KEY)) {
            initTree();
        }
        Assert.isTrue(this.treeBookDao.countNameInPCode(PTConstOld.CODE_PROVINCE, name).intValue() == 0, "企业名不能包含地区名！");

        Assert.hasText(industry, "行业不能为空！");
        Assert.hasText(industry = industry.replaceAll(blank, emptyStr), "行业不能全部是空格！");
        Assert.isTrue(industry.length() < 15, "行业的字符数目不能大于14！");
        Assert.isTrue(StringUtils.isOnlySimpleChinese(industry), "行业名只能是简体中文！");

        Assert.hasText(companyType, "企业类型不能为空！");
        Assert.hasText(companyType = companyType.replaceAll(blank, emptyStr), "企业类型不能全部是空格！");
        Assert.isTrue(companyType.length() < 15, "企业类型的字符数目不能大于14！");
        Assert.isTrue(StringUtils.isOnlySimpleChinese(companyType), "企业类型只能是简体中文！");
    }

    /**
     * 核名验证
     *
     * @param name
     * @return
     */
    @Transactional
    public Map checkCompany(String url, String key, String city, String name, String industry, String companyType, Integer highlight, String ipAddress) {
        highlight = highlight == null ? highlightDefault : highlight;

        //Assert.isTrue(urlCallStatusService.getCallStatus(url), "此接口被关闭！");

        // 公共参数验证
        this.checkCompanyParam(city, name, industry, companyType);

        // 外部接口 api url 和 key 的公共验证 与登录状态验证
        // 验证成功 就在redis 保存登录状态
        //apiKeyAndUrlCheckService.checkAndLogin(key, url);

//        String comName = String.format("%s%s%s%s", city, name, industry, companyType);
        long all = esService.getTotalCount(this.index, this.type, this.field, city, name, industry, companyType);
        List<Map> nameList = all == 0 ? new ArrayList<>(0) : esService.getCheckNameList(this.index, this.type, this.field, city, name, industry, companyType, 10);

        Map reMap = this.anasysResult(all, nameList, highlight);
        this.createRecord(url, key, city, name, industry, companyType, all, highlight, ipAddress);
        return reMap;

    }


    /**
     * 网站自用核名接口
     *
     * @param city
     * @param name
     * @param industry
     * @param companyType
     * @param userId
     * @return
     */
    @Transactional
    public Map innerCheckCompany(String url, String userId, String city, String name, String industry, String companyType, Integer highlight, String ipAddress) {
        highlight = highlight == null ? highlightDefault : highlight;

        // 公共参数验证
        this.checkCompanyParam(city, name, industry, companyType);

        Assert.hasText(userId, "用户的Id不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "登录过期，请重新登录！");

        String comName = String.format("%s%s%s%s", city, name, industry, companyType);
//        long all = esService.getTotalCount(this.index, this.type, this.field, comName, PTConst.F_FALSE);
//        List<Map> nameList = all == 0 ? new ArrayList<>(0) : esService.getCheckNameList(this.index, this.type, this.field, comName, 10, PTConst.F_FALSE);
        long all = esService.getTotalCount(this.index, this.type, this.field, city, name, industry, companyType);
        List<Map> nameList = all == 0 ? new ArrayList<>(0) : esService.getCheckNameList(this.index, this.type, this.field, city, name, industry, companyType, 10);

        Map reMap = this.anasysResult(all, nameList, highlight);
        this.createRecord(url, userId, city, name, industry, companyType, all, highlight, ipAddress);
        return reMap;

    }


    /**
     * 分析结果
     *
     * @param total
     * @param names
     * @return
     */
    private Map anasysResult(long total, List<Map> names, Integer highlight) {
        Map map = new HashMap();
        String possibility = null;
        if (total <= 10) {
            possibility = HIGH;
        } else if (total <= 25 && total > 10) {
            possibility = MEDIUM;
        } else if (total > 25) {
            possibility = LOW;
        }
        List<String> stringList = new ArrayList<>(names == null ? 0 : names.size());
        for (Map map1 : names) {
            if (map1.get("lightVal") != null) {
                String lightVal = map1.get("lightVal").toString();
                stringList.add(highlight == 1 ? lightVal : lightVal.replaceAll("<i>", "").replaceAll("</i>", ""));
            }
        }


        map.put("possibility", possibility);
        map.put("names", stringList);
        map.put("total", total);
        return map;
    }

    /**
     * 创建 记录
     *
     * @param city
     * @param name
     * @param industry
     * @param companyType
     * @param key
     */
    @Transactional
    public void createRecord(String url, String key, String city, String name, String industry, String companyType, long all, int highlight, String ipAddress) {
// 创建调用记录
        /*UserCallRecord record = new UserCallRecord();
        record.setId(KeyWorker.nextId());
        record.setCreateTime(new Date());
        record.setUrl(url);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", city);
        paramMap.put("name", name);
        paramMap.put("industry", industry);
        paramMap.put("companyType", companyType);
        paramMap.put("highlight", highlight);
        record.setParamJson(gson.toJson(paramMap));
        record.setKey(key);
        record.setResult(all > 0L ? 0 : 1);
        record.setIpAddress(ipAddress);
        userCallRecordService.save(record);*/

        this.searchWordService.search(String.format("%s%s%s%s", city, name, industry, companyType), ipAddress);
    }


    /**
     * 批量保存
     *
     * @param recordList
     */
    @Transactional
    public void saveList(List<CheckCompanyRecord> recordList) {
        if (recordList == null || recordList.size() == 0) {
            return;
        }
        for (CheckCompanyRecord record : recordList) {
            this.checkCompanyRecordDao.save(record);
        }
    }


    /**
     * python 接口调用返回结果
     */
    private class CheckResult {
        private String message;
        private List<String> names;
        private Integer status;
        private Integer total;

        public void setMessage(String message) {
            this.message = message;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }
    }

    /**
     * 初始化大区域名称至redis
     */
    private void initTree() {
        if (!RedisUtils.exists(PTConstOld.TREE_KEY)) {
            List<String> treeList = treeBookService.getChildNameByCode(PTConstOld.CODE_PROVINCE);
            if (treeList != null && treeList.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (String s : treeList) {
                    sb.append(s).append("-");
                }
                RedisUtils.set(PTConstOld.TREE_KEY, sb.toString());
                RedisUtils.expire(PTConstOld.TREE_KEY, Integer.parseInt(RedisUtils.getRedisPriperty(PTConstOld.TRKKKEY_EXPIRE)));
            }
        }
    }
}
