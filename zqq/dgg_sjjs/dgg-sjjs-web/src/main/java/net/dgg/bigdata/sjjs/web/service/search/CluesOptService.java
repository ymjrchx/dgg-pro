package net.dgg.bigdata.sjjs.web.service.search;

import net.dgg.bigdata.common.constant.BConst;
import net.dgg.bigdata.sjjs.web.config.EsConfig;
import net.dgg.bigdata.sjjs.web.entity.SysUser;
import net.dgg.bigdata.sjjs.web.entity.dto.search.clues.YkClues;
import net.dgg.bigdata.sjjs.web.entity.dto.search.clues.YkCluesRecord;
import net.dgg.bigdata.sjjs.web.service.SysUserService;
import net.dgg.core.es.services.EsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: CluesOptService
 * @Description: 线索操作service
 * @Author: jiangsh
 * @Date: 2018/12/24 16:43
 */
@Service
public class CluesOptService {

    @Resource
    private EsService esService;
    @Resource
    private SysUserService sysUserService;

    /**
     *  线索记录
     * @param obj  线索记录源文件
     * @return
     */
    public boolean addRecordDocument(YkCluesRecord obj) {
       return esService.addDocument(EsConfig.YK_COMMERCIAL_CLUES_RECORD, EsConfig.YK_COMMERCIAL_CLUES_RECORD, UUID.randomUUID().toString(), obj);
    }

    /**
     * 线索转换
     * @param id  公司id
     * @param obj  线索转换源文件
     * @return
     */
    public boolean addCluesConverDocument(String id, YkClues obj) {
        SysUser sysUser = sysUserService.findByIbossCode(obj.getNum()); //num 购买号
        if (sysUser != null) {
            obj.setLegenDept(sysUser.getLegenDept());
            obj.setLegenPerson(sysUser.getLegenPerson());
        } else return false;
       return esService.addDocumentClues(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_CHILD, id, obj, id);
    }

    /**
     * 获取需要推送的线索
     * @param companyId
     * @return
     */
    public List<Map> getClues(final String[] companyId) {
        if (companyId.length == 0) throw new IllegalArgumentException("请输入必要的公司id！");
        final String[] exclude = {""};
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.termsQuery(BConst.COMPANY_ID, companyId));
        return esService.getMsgByKeyWordFilterField(EsConfig.YK_COMMERCIAL, EsConfig.YK_COMMERCIAL_PARENT, companyId.length, includeField(), exclude, qb);
    }

    /**
     * 推送线索字段
     */
    private static String[] includeField() {
        final String[] include = {BConst.COMPANY_ID, BConst.COMPANY_NAME, BConst.LEGEN_PERSON, BConst.INDUSTRY, BConst.ADDRESS,
                BConst.WEB_SITE_URL, BConst.REG_MONEY, BConst.REG_TIME, BConst.BUSINESS_CB, BConst.TEL};
        return include;
    }
}
