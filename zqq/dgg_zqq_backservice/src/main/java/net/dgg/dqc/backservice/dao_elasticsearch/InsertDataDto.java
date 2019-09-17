package net.dgg.dqc.backservice.dao_elasticsearch;

import com.alibaba.fastjson.JSON;
import com.google.gson.*;
import net.dgg.dqc.backservice.entity.parse.*;
import net.dgg.dqc.backservice.entity.parse.resume.ResumeModel;
import net.dgg.dqc.backservice.entity.parse.zcgl.ZcglModel;
import net.dgg.dqc.backservice.es.EsClientUtils;
import net.dgg.dqc.backservice.utils.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static net.dgg.dqc.backservice.service.SyncDealDataService.recordCount;

/**
 * Created by jiangsh on 2018/8/9 10:29
 */

@Repository
public class InsertDataDto {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Gson gson = null;

    static {
        gson = new GsonBuilder().serializeNulls().registerTypeAdapter(String.class, (JsonSerializer<String>) (src, typeOfSrc, context) -> "-".equals(src) ? JsonNull.INSTANCE : new JsonPrimitive(src)).create();
    }

    public void insert(QccCompany c, String index, String type, String companyType, CountRecord cr) {
        try {
            recordCount(cr);
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getCompanyId()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}", companyType, index, type, c.getCompanyId(), c.getName());
//            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}, source --> {}", companyType, index, type, c.getCompanyId(), c.getName(), gson.toJson(c));
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertBrand(QccCompany c, String index, String type, String companyType, CountRecord cr) {//商标
        try {
            recordCount(cr);
            List<SbDetails> brand =  c.getSbDetails();
            if (brand != null && brand.size() > 0) {
                for (SbDetails b : brand) {
                    String rgeNo = b.getRegNo();
                    if (StringUtils.isEmpty(rgeNo)) rgeNo = UUID.randomUUID().toString();
                    EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, rgeNo).source(gson.toJson(b), XContentType.JSON));
                    logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}", companyType, index, type, rgeNo, b.getName());
                }
            } else logger.info(" {} company is not have brand msg !", c.getName());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertBrandDetail(SbDetails c, String index, String type, String companyType, CountRecord cr) {
        try {
//            recordCount(cr);
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getEsId()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}", companyType, index, type, c.getEsId(), c.getName());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertPatent(QccCompany c, String index, String type, String companyType, CountRecord cr) {//专利
        try {
            recordCount(cr);
            List<Zlxq> zlxq =  c.getZlxq();
            if (zlxq != null && zlxq.size() > 0) {
                for (Zlxq b : zlxq) {
                    final String rgeNo = b.getApplicationNumber();
                    if (StringUtils.isEmpty(rgeNo)) continue;
                    EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, rgeNo).source(gson.toJson(b), XContentType.JSON));
                    logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}", companyType, index, type, rgeNo, b.getPiInventName());
                }
            } else logger.info(" {} company is not have patent msg !", c.getName());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertPatent2(Zlxq c, String index, String type, String companyType, CountRecord cr) {//专利
        try {
            recordCount(cr);
            final String rgeNo = c.getApplicationNumber();
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, rgeNo).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}", companyType, index, type, rgeNo, c.getPiInventName());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertZc(ZcglModel c, String index, String type, String companyType, CountRecord cr) {
        try {
            recordCount(cr);
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getCompanyId()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}", companyType, index, type, c.getCompanyId(), c.getQymc());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertR(ResumeModel c, String index, String type, String companyType, CountRecord cr) {
        try {
            recordCount(cr);
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getUrl()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {}, name --> {}", companyType, index, type, c.getUrl(), c.getName());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertNews(NewsModel c, String index, String type, String companyType, CountRecord cr) {
        try {
            recordCount(cr);
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getTitle()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {} pubdate -- > {}", companyType, index, type, c.getTitle(), c.getPubdate());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }

    public void insertCpws(Cpws c, String index, String type, String companyType, CountRecord cr) {
        try {
//            recordCount(cr);
            EsClientUtils.getBulkProcessor().add(new IndexRequest(index, type, c.getId()).source(gson.toJson(c), XContentType.JSON));
            logger.info(" {} detail add es success!    index -> {}, type --> {}, id --> {} caseName -- > {}", companyType, index, type, c.getId(), c.getCaseName());
        } catch (Exception e) {
            logger.error("bulkProcessor failed ,reason:{}",e);
        }
    }
}
