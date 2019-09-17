package net.dgg.gspt.dqc.framework;

import net.dgg.gspt.dqc.dto.brandsearch.Kv;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.utils.HidePhoneUtils;
import net.dgg.gspt.dqc.utils.esOld.EsConst;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jiangsh on 2018/8/16 19:05
 */
public interface ConstMethodOld {

    /**
     * 简单分页封装
     *
     * @param pageIndex 第几页
     * @param pageSize  查多少条
     * @return
     */
    default Map<String, Object> pages(int pageIndex, int pageSize) {
        Map<String, Object> dataMap = new HashMap<>();
        int startCount = 1;
        int endCount = 10;
        if (pageIndex <= 0) pageIndex = 1;
        if (pageSize <= 0) pageSize = 10;
        else endCount = pageSize;
        if (pageIndex > 1) {
            startCount = (pageIndex - 1) * pageSize + 1;
            endCount = pageIndex * pageSize;
        }
        dataMap.put("pageIndex", pageIndex);
        dataMap.put("pageSize", pageSize);
        dataMap.put("startCount", startCount);
        dataMap.put("endCount", endCount);
        return dataMap;
    }

    /**
     * 服务 简单分页封装
     *
     * @param pageIndex 第几页
     * @param pageSize  查多少条
     * @return
     */
    default Map<String, Object> pageService(int pageIndex, int pageSize) {
        //判断是否vip之类的，默认不超过50条
        if (pageSize > 50) pageSize = 50;

        Map<String, Object> dataMap = new HashMap<>();
        int startCount = 1;
        int endCount = 10;
        if (pageIndex <= 0) pageIndex = 1;
        if (pageSize <= 0) pageSize = 10;
        else endCount = pageSize;
        if (pageIndex > 1) {
            startCount = (pageIndex - 1) * pageSize + 1;
            endCount = pageIndex * pageSize;
        }
        dataMap.put("pageIndex", pageIndex);
        dataMap.put("pageSize", pageSize);
        dataMap.put("startCount", startCount);
        dataMap.put("endCount", endCount);
        return dataMap;
    }

    /**
     * 判断是否登录
     *
     * @param request
     * @return
     */
    default boolean justLogin(HttpServletRequest request) {
        String token = request.getHeader(PTConstOld.USER_TOKEN); //userId
        String username = RedisUtils.get(token);
        if (StringUtils.isNotEmpty(username)) return true; //已登录
        else return false;
    }

    /**
     * 加密电话、邮箱
     */
    default List<Map> justList(boolean status, List<Map> list) {
        if (!status && list != null && list.size() > 0) {
            HidePhoneUtils.hidePhone(getColumns(), list);
        }
        return list;
    }

    /**
     * 获取需加密信息
     *
     * @return
     */
    default String[] getColumns() {
        final String[] columns = {PTConstOld.INDEX_TEL, PTConstOld.BASICINFO_CONTACTNO,
                PTConstOld.BASICINFO_PHONENUMBER, PTConstOld.BASICINFO_EMAIL, PTConstOld.BASICINFO_TEL, PTConstOld.BASICINFO_QYNB_EMAIL,
                PTConstOld.BASICINFO_FPTT_PHONENO, PTConstOld.INDEX_EMAIL};
        return columns;
    }

    default String indexs(String t) {
        String index;
        switch (t) {
            case PTConstOld.MIX_TAX:
                index = EsConst.INDEX;
                break;

            case PTConstOld.MIX_BRAND:
                index = EsConst.INDEX_BRAND;
                break;

            case PTConstOld.MIX_PATENT:
                index = EsConst.INDEX_PATENT;
                break;

            case PTConstOld.MIX_CPWS:
                index = EsConst.INDEX_CPWS;
                break;

            case PTConstOld.MIX_CPWS_TS:
                index = EsConst.INDEX_CPWS_TS;
                break;

            default:
                index = EsConst.INDEX;
                break;
        }
        return index;
    }

    default String types(String t) {
        String type;
        switch (t) {
            case PTConstOld.MIX_TAX:
                type = EsConst.TYPE_COMPANY;
                break;

            case PTConstOld.MIX_BRAND:
                type = EsConst.TYPE_COMPANY_BRAND;
                break;

            case PTConstOld.MIX_PATENT:
                type = EsConst.TYPE_COMPANY_PATENT;
                break;

            case PTConstOld.MIX_CPWS:
                type = EsConst.TYPE_COMPANY_CPWS;
                break;

            case PTConstOld.MIX_CPWS_TS:
                type = EsConst.TYPE_COMPANY_CPWS_TS;
                break;

            default:
                type = EsConst.TYPE_COMPANY;
                break;
        }
        return type;
    }

    /**
     * 首页列表展示列
     *
     * @return
     */
    default String[] getSearchListColumn() {
        final String[] columns = {"logo", "name", "qygsDetail.operName", "qygsDetail.registCapi", "qygsDetail.startDate",
                "email", "tel", "qygsDetail.address", "qygsDetail.status", "qygsDetail.contactInfoList.phoneNumber", "companyId"};
        return columns;
    }

    /**
     * 对外服务 企业列表 展示列
     *
     * @return
     */
    default String[] listColumn() {
        final String[] columns = {"logo", "name", "qygsDetail.operName", "qygsDetail.registCapi", "qygsDetail.startDate",
                "email", "qygsDetail.address", "qygsDetail.status", "companyId"};
        return columns;
    }

    /**
     * 对外服务 首页详情展示列
     *
     * @return
     */
    default String[] getSearchDetailColumn() {
        final String[] columns = {"logo", "name", "qygsDetail.operName", "qygsDetail.registCapi", "qygsDetail.startDate",
                "email", "qygsDetail.address", "qygsDetail.status", "companyId",
                "qygsDetail.creditCode", "qygsDetail.no", "qygsDetail.orgNo", "qygsDetail.econKind",
                "qygsDetail.industry", "qygsDetail.belongOrg", "qygsDetail.checkDate", "qygsDetail.province", "qygsDetail.scope", "qygsDetail.termStart", "qygsDetail.teamEnd"};
        return columns;
    }


    default List<Kv> converKv(Map<String, Long> map) {
        List<Kv> kvs = new ArrayList<>();
        if (map != null && map.size() > 0)
            for (Map.Entry<String, Long> e : map.entrySet()) {
                Kv kv = new Kv();
                kv.setKey(e.getKey());
                kv.setVal(String.valueOf(e.getValue()));
                kvs.add(kv);
            }
        return kvs;
    }

    /**
     * 裁判文书类别
     *
     * @return
     */
    default List<String> getCpwsType() {
        return new ArrayList<String>() {{
            add("民事");
            add("刑事");
            add("行政");
            add("赔偿");
            add("执行");
        }};
    }

}
