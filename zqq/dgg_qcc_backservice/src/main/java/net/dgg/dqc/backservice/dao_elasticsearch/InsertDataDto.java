package net.dgg.dqc.backservice.dao_elasticsearch;

import com.alibaba.fastjson.JSON;
import com.google.gson.*;
import net.dgg.dqc.backservice.entity.parse.QccCompany;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.es.EsClientUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

/**
 * Created by jiangsh on 2018/8/9 10:29
 */

@Repository
public class InsertDataDto {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(String.class, (JsonSerializer<String>) (src, typeOfSrc, context) -> "-".equals(src) ? JsonNull.INSTANCE : new JsonPrimitive(src)).create();

    public void insert(QccCompany c, String index, String type, String companyType) {
        try {
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getCompanyId()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}", companyType, index, type, c.getCompanyId());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertZc(ZcglModel c, String index, String type, String companyType) {
        try {
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getCompanyId()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}", companyType, index, type, c.getCompanyId());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertR(ResumeModel c, String index, String type, String companyType) {
        try {
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getUrl()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}", companyType, index, type, c.getUrl());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

}
