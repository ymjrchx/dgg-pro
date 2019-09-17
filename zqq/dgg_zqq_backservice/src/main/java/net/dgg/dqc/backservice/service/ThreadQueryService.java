package net.dgg.dqc.backservice.service;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.imagesearch.AipImageSearch;
import net.dgg.dqc.backservice.dao_elasticsearch.InsertDataDto;
import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.entity.parse.xst.XstModel;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.es.EsConst;
import net.dgg.dqc.backservice.framework.ai.xst.XstOper;
import net.dgg.dqc.backservice.parsedata.*;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadQueryService {

    Logger logger = LoggerFactory.getLogger(ThreadQueryService.class);

    @Autowired
    private InsertDataDto insertDataDto;

    @Async("processExecutor")
    public void qureyAllstand(int i, MongoCollection c, int skip, int limit, CountRecord cr) {
        logger.info("------stand-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            QccCompany company = StandDetailData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insert(company, EsConst.INDEX, EsConst.TYPE_COMPANY, "Stand", cr);
        }
    }

    @Async("processExecutor")
    public void qureyAlleqc(int i, MongoCollection c, int skip, int limit, CountRecord cr) {
        logger.info("------eqc-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            QccCompany company = EqcDetailData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insert(company, EsConst.INDEX, EsConst.TYPE_COMPANY, "eqc", cr);
        }
    }

    @Async("processExecutor")
    public void qureyAllqcc(int i, MongoCollection c, int skip, int limit, CountRecord cr) {
        logger.info("------qcc-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            QccCompany company = QccDetailData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insert(company, EsConst.INDEX, EsConst.TYPE_COMPANY, "qcc", cr);
        }
    }

    @Async("processExecutor")
    public void queryAllgxzg(int i, MongoCollection c, int skip, int limit, CountRecord cr) {
        logger.info("------news-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            QccCompany r = GxzgDetaliData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insert(r, EsConst.INDEX, EsConst.TYPE_COMPANY, "gxzg", cr);
        }
    }

    @Async("processExecutor")
    public void qureyAllzcgl(int i, MongoCollection c, int skip, int limit, CountRecord cr) {
        logger.info("------zcgl-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
//                .find("{zcsd:{$regex:'广东'}}")
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            ZcglModel z = ZcglData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insertZc(z, EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, "zcgl", cr);
        }
    }


    @Async("processExecutor")
    public void queryAllResumes(int i, MongoCollection c, int skip, int limit, CountRecord cr) {
        logger.info("------resumes-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            ResumeModel r = ResumeData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insertR(r, EsConst.INDEX_RESUME, EsConst.TYPE_COMPANY_RESUME, "Resumes", cr);
        }
    }

    @Async("processExecutor")
    public void queryAllnews(String index, String type, int i, MongoCollection c, int skip, int limit, CountRecord cr) {
        logger.info("------news-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
//            NewsModel r = NewsDetailData.conver(JSON.toJSON(cursor.next()));
            NewsModel r = NewsDetailData.conver2(JSON.toJSON(cursor.next()));
            insertDataDto.insertNews(r, index, type, "news", cr);
        }
    }

    @Async("processExecutor")
    public void queryAllbrand(String index, String type, int i, MongoCollection c, int skip, int limit, String comType, String query, CountRecord cr) {
        logger.info("------brand-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        String q = query == null ? "{}" : query;
        MongoCursor<Object> cursor = c
                .find(q)
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            if (comType.equals("zhigaodian_detail_results") || comType.equals("qianhui_detail_results") || comType.equals("maibiaoku_detail_results") || comType.equals("subiao_detail_results")) {
                SbDetails s = BrandDetailData.conver(JSON.toJSON(cursor.next()));
                insertDataDto.insertBrandDetail(s, index, type, comType, cr);
                continue;
            }

            QccCompany r;
            if (comType.equals("eqc-brand"))
                r = BrandData.converEqcBrand(JSON.toJSON(cursor.next()));
            else if (comType.equals("qcc-brand") || comType.equals("qcc-zhichan-brand"))
                r = BrandData.converQccBrand(JSON.toJSON(cursor.next()));
            else if (comType.equals("gxzg-brand"))
                r = BrandData.converGxzgBrand(JSON.toJSON(cursor.next()));
            else //if (comType.equals("stand"))
                r = BrandData.converStandBrand(JSON.toJSON(cursor.next()));

            insertDataDto.insertBrand(r, index, type, comType.concat("-webSite-brand"), cr);
        }
    }

    @Async("processExecutor")
    public void queryAllpatent(String index, String type, int i, MongoCollection c, int skip, int limit, String comType, CountRecord cr) {
        logger.info("------patent-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            if (comType.equals("sooip_results")) {
                Zlxq r = PatentData.converStandPatent(JSON.toJSON(cursor.next()));
                insertDataDto.insertPatent2(r, index, type, comType.concat("-webSite-patent"), cr);
                continue;
            }

            // 单个对象处理逻辑
            QccCompany r;
            if (comType.equals("eqc"))
                r = PatentData.converEqcPatent(JSON.toJSON(cursor.next()));
            else // (comType.equals("qcc"))
                r = PatentData.converQccPatent(JSON.toJSON(cursor.next()));
            insertDataDto.insertPatent(r, index, type, comType.concat("-webSite-patent"), cr);
        }
    }

    @Async("processExecutor")
    public void queryAllCpws(String index, String type, int i, MongoCollection c, int skip, int limit, String comType, CountRecord cr, String query) {
        logger.info("------cpws-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        String q = query == null ? "{}" : query;
        MongoCursor<Object> cursor = c
                .find(q)
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
//            Cpws r = JudgementDocData.conver(JSON.toJSON(cursor.next()));
            Cpws r = JudgementDocData.conver2(JSON.toJSON(cursor.next()));
            insertDataDto.insertCpws(r, index, type, comType, cr);
        }
    }

//    @Async("processExecutor")
    public void saveXstImg(int i, MongoCollection c, int skip, int limit, String comType, CountRecord cr, String query, String imgPath, AipImageSearch client, String tags) {
        logger.info("------xst-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        String q = query == null ? "{}" : query;
        MongoCursor<Object> cursor = c
                .find(q)
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            XstModel x = XstData.conver(JSON.toJSON(cursor.next()));
            XstOper.saveXstImg(client, x, tags, imgPath);
        }
    }
}
