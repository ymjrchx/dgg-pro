package net.dgg.dqc.backservice.service;

import com.baidu.aip.imagesearch.AipImageSearch;
import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.es.EsConst;
import net.dgg.dqc.backservice.framework.ai.businessCard.OperServces;
import net.dgg.dqc.backservice.framework.mongo.MongoFactory;
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

    private static final MongoCollection erc =  MongoFactory.getColByDb("jl", "detail_results");
    private static final MongoCollection rec =  MongoFactory.getColByDb("jl", "record");
    private static final MongoCollection imgDetail =  MongoFactory.getColByDb("jl", "imgDetail");

    @Autowired
    private ThreadQueryService threadQueryService;

    public void queryAllstand(String dbName, String col) {
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class, "name");
        MongoCollection eqcCon = MongoFactory.getColByDb(dbName, col);
        addOption(eqcCon);

        final int bath = 20000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = eqcCon.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("stand---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord("queryAllstand", "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.qureyAllstand(i, eqcCon, skip, bath, cr);
        }
//        MongoConfig.getMongoClient().close();
    }

    public void queryAlleqc(String dbName, String col) {
//        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class, "name");
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class, null);
        MongoCollection eqcCon = MongoFactory.getColByDb(dbName, col);
//        addOption(eqcCon);

        final int bath = 20000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = eqcCon.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("eqc---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord("queryAlleqc", "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.qureyAlleqc(i, eqcCon, skip, bath, cr);
        }
//        MongoConfig.getMongoClient().close();
    }

    public void queryAllqcc(String dbName, String col, String index, String type) {
//        initMapping(1, getCol(new MongoConfig().onlyTest().getDB(dbName), col));
        createMapping(index, type, QccCompany.class, "name");
        MongoCollection qccCon = MongoFactory.getColByDb(dbName, col);
//        addOption(qccCon);

        final int bath = 20000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = qccCon.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("qcc---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord("queryAllqcc", "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.qureyAllqcc(i, qccCon, skip, bath, cr);
        }
    }

    public void queryAllgxzg(String dbName, String col) {
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class, "name");
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);

        final int bath = 10000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("gxzg---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord("queryAllgxzg", "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.queryAllgxzg(i, r, skip, bath, cr);
        }
    }

    public void queryAllzcgl(String dbName, String col) {
        createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, ZcglModel.class, null);
        MongoCollection zcgl = MongoFactory.getColByDb(dbName, col);
//        addOption(zcgl);

        final int bath = 10000; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = zcgl.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("zcgl---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord("queryAllzcgl", "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.qureyAllzcgl(i, zcgl, skip, bath, cr);
        }
    }

    public void queryAllResumes(String dbName, String col) {
        createMapping(EsConst.INDEX_RESUME, EsConst.TYPE_COMPANY_RESUME, ResumeModel.class, null);
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);

        final int bath = 500; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("Resumes---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord("queryAllResumes", "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.queryAllResumes(i, r, skip, bath, cr);
        }
    }

    public void queryAllnews(String index, String type, String dbName, String col) {
        createMapping(index, type, NewsModel.class, "title");
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);

        final int bath = 500; //每页多少条
        final int baseSkip = 0; //初始跳过
        final long all = r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("news---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord("queryAllnews", "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.queryAllnews(index, type, i, r, skip, bath, cr);
        }
    }

    public void queryAllbrand(String index, String type, String dbName, String col, String comType, final int bath, final int baseSkip, final int allcount, String query) {
        createMapping(index, type, SbDetails.class, "name");
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);
        final long all =  allcount > 0 ? allcount : r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("brand---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord(comType, "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.queryAllbrand(index, type, i, r, skip, bath, comType, query, cr);
        }
    }

    public void queryAllpatent(String index, String type, String dbName, String col, String comType, final int bath, final int baseSkip) {
        createMapping(index, type, Zlxq.class, "piInventName");
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);

        final long all = r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("patent---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord(comType, "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.queryAllpatent(index, type, i, r, skip, bath, comType, cr);
        }
    }

    public void queryAllCpws(String index, String type, String dbName, String col, String comType, int bath, int baseSkip, String query) {
        createMapping(index, type, Cpws.class, "title");
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);

        final long all = r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("cpws---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord(comType, "1");
        for (int i = 0; i < cir; i++) {
            int skip = i * bath + baseSkip;
            threadQueryService.queryAllCpws(index, type, i, r, skip, bath, comType, cr, query);
        }
    }

    public void saveBusinessCardImg(String imgPath) {
        OperServces.dealImgData(imgPath, imgDetail);
    }

    public void saveXstImg(String dbName, String col, String comType, int bath, int baseSkip, int allcount, String query, String imgPath, AipImageSearch client, String tags) {
        MongoCollection r = MongoFactory.getColByDb(dbName, col);
//        addOption(r);

        final long all =  allcount > 0 ? allcount : r.count();
        final long skipAfter = all - baseSkip;

        long cir = skipAfter / bath + (skipAfter % bath == 0 ? 0 : 1);
        logger.info("xst---------------------------------all--->{}, cir--->{}", all, cir);
        CountRecord cr = new CountRecord(comType, "1");
        int skip =  baseSkip;
        threadQueryService.saveXstImg(0, r, skip, bath, comType, cr, query, imgPath, client, tags);
//        for (int i = 0; i < cir; i++) {
//            int skip = i * bath + baseSkip;
//            threadQueryService.saveXstImg(i, r, skip, bath, comType, cr, query, imgPath, client, tags);
//        }
    }

    private void addOption(MongoCollection c) {
        c.getDBCollection().addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
    }

    public static void saveErrorMsg(ErrorRecord er) {
        erc.save(er);
    }

    public static void recordCount(CountRecord cr) {
        rec.save(cr);
    }

}
