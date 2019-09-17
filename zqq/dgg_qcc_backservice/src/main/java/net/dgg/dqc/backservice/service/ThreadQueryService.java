package net.dgg.dqc.backservice.service;

import com.alibaba.fastjson.JSON;
import net.dgg.dqc.backservice.dao_elasticsearch.InsertDataDto;
import net.dgg.dqc.backservice.entity.parse.QccCompany;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.es.EsConst;
import net.dgg.dqc.backservice.parsedata.EqcDetailData;
import net.dgg.dqc.backservice.parsedata.QccDetailData;
import net.dgg.dqc.backservice.parsedata.ResumeData;
import net.dgg.dqc.backservice.parsedata.ZcglData;
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
    public void qureyAlleqc(int i, MongoCollection c, int skip, int limit) {
        logger.info("------eqc-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            QccCompany company = EqcDetailData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insert(company, EsConst.INDEX, EsConst.TYPE_COMPANY, "eqc");
        }
    }

    @Async("processExecutor")
    public void qureyAllqcc(int i, MongoCollection c, int skip, int limit) {
        logger.info("------qcc-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            QccCompany company = QccDetailData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insert(company, EsConst.INDEX, EsConst.TYPE_COMPANY, "qcc");
        }
    }

    @Async("processExecutor")
    public void qureyAllzcgl(int i, MongoCollection c, int skip, int limit) {
        logger.info("------zcgl-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            ZcglModel z = ZcglData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insertZc(z, EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, "zcgl");
        }
    }


    @Async("processExecutor")
    public void queryAllResumes(int i, MongoCollection c, int skip, int limit) {
        logger.info("------resumes-----current--{}-------skip------>{}, limit------->{}", i,  skip, limit);
        MongoCursor<Object> cursor = c
                .find("{}")
                .skip(skip)
                .limit(limit)
                .as(Object.class);
        while (cursor.hasNext()) {
            // 单个对象处理逻辑
            ResumeModel r = ResumeData.conver(JSON.toJSON(cursor.next()));
            insertDataDto.insertR(r, EsConst.INDEX, EsConst.TYPE_COMPANY_RESUME, "Resumes");
        }
    }
}
