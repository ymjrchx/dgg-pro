package net.dgg.dqc.backservice.service;

import com.alibaba.fastjson.JSON;
import net.dgg.dqc.backservice.entity.parse.ErrorRecord;
import net.dgg.dqc.backservice.entity.parse.QccCompany;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.es.EsConst;
import net.dgg.dqc.backservice.es.EsUtils;
import net.dgg.dqc.backservice.framework.mongo.MongoFactory;
import net.dgg.dqc.backservice.framework.mongo.MongoQuery;
import net.dgg.dqc.backservice.framework.mongo.Page3Dto;
import net.dgg.dqc.backservice.parsedata.QccDetailData;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static net.dgg.dqc.backservice.es.EsUtils.createMapping;


/**
 * Created by jiangsh on 2018/8/9 09:57
 */
@Service
public class SyncDealDataService {

    Logger logger = LoggerFactory.getLogger(SyncDealDataService.class);

    private static final MongoCollection c = MongoFactory.getColByDb("jl", "detail_results");

    @Autowired
    private ThreadQueryService threadQueryService;

    public void queryAlleqc(String dbName, String col) {
//        initMapping(1, getCol(new MongoConfig().onlyTest().getDB(dbName), col));
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class);
        MongoCollection eqcCon = MongoFactory.getColByDb(dbName, col);
//        addOption(eqcCon);

        final int bath = 50000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = eqcCon.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("eqc---------------------------------all--->{}, cir--->{}", all, cir);
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.qureyAlleqc(i, eqcCon, skip, bath);
        }
//        MongoConfig.getMongoClient().close();
    }

    public void queryAllqcc(String dbName, String col) {
//        initMapping(1, getCol(new MongoConfig().onlyTest().getDB(dbName), col));
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class);
        MongoCollection qccCon = MongoFactory.getColByDb(dbName, col);
//        addOption(qccCon);

        final int bath = 10000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = qccCon.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("qcc---------------------------------all--->{}, cir--->{}", all, cir);
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.qureyAllqcc(i, qccCon, skip, bath);
        }
    }

    public void queryAllzcgl(String dbName, String col) {
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, ZcglModel.class);
        MongoCollection zcgl = MongoFactory.getColByDb(dbName, col);
//        addOption(zcgl);

        final int bath = 10000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = zcgl.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("zcgl---------------------------------all--->{}, cir--->{}", all, cir);
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.qureyAllzcgl(i, zcgl, skip, bath);
        }
    }

    public void queryAllResumes(String dbName, String col) {
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY_RESUME, ResumeModel.class);
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);

        final int bath = 500; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("Resumes---------------------------------all--->{}, cir--->{}", all, cir);
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.queryAllResumes(i, r, skip, bath);
        }
    }


    public static void initMapping(int count, MongoCollection c) {
        Page3Dto<Object> page3Dto = MongoQuery.build(c).page3(1, count, Object.class, p -> p, "{}");
        if (page3Dto != null) {
            for (Object p : page3Dto.getData()) {
                QccCompany company = QccDetailData.conver(JSON.toJSON(p));
                EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY, company.getCompanyId(), JSON.toJSON(company));
                createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class);
            }
        }
    }

    private void addOption(MongoCollection c) {
        c.getDBCollection().addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
    }

    public static void saveErrorMsg(ErrorRecord er) {
        c.save(er);
    }
}
