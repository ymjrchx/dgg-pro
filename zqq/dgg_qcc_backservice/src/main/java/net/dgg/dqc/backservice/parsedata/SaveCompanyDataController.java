//package net.dgg.dqc.backservice.parsedata;
//
//import com.alibaba.fastjson.JSON;
//import com.mongodb.MongoClient;
//import org.jongo.MongoCollection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Created by jiangsh on 2018/5/18 18:12
// * 保存company数据到es
// */
//@RestController
//@RequestMapping("/saveCompanyData")
//public class SaveCompanyDataController {
//
//    Logger logger = LoggerFactory.getLogger(SaveCompanyDataController.class);
//
//    private static final int PAGE_SIZE = 50;
//
//    @RequestMapping(value = "/saveDataToEs", method = RequestMethod.GET)
//    @ResponseBody
//    public void saveDataToEs(@RequestParam String status,
//                             @RequestParam int count
//    ) {
//        String dbName;
//        String col;
//        switch (status) {
//            case "gxzg":
//                dbName = "gxzg_org_cn";
//                col = "detail_results_old";
//                break;
//
//            case "gxzgdetail":
//                dbName = "gxzg_org_cn";
//                col = "detail_results";
//                break;
//
//            case "eqc":
//                dbName = "eqicha_com";
//                col = "list_results";
//                break;
//
//            case "eqcdetail":
//                dbName = "eqicha_com";
//                col = "detail_results";
//                break;
//
//            case "qcc":
//                dbName = "qichacha_com";
//                col = "search_results";
//                break;
//
//            case "qccdetail":
//                dbName = "qichacha_com";
////                col = "detail_results";
//                col = "detail_dgg";
//                break;
//
//            case "qcc_zscq":
//                dbName = "qichacha_com";
//                col = "zhichan_results_all";
//                break;
//
//            case "zcgl":
//                dbName = "zcgl";
//                col = "company_info";
//                break;
//
//            case "resumes":
//                dbName = "58guakao_com";
//                col = "new_detail_results";
//                break;
//
//            default:
//                dbName = "local_db";
//                col = "person";
//                break;
//        }
////        savePageCompanyMsg(MongoFactory.getColByDb(dbName, col), pageIndex, pageSize, status);
////        savePageCompanyMsg(MongoFactory.getColByDb(dbName, col), 1, status);
//        savePageCompanyMsg(MongoFactory.getColByDb(dbName, col),1, count, status);
//    }
//
//    /**
//     * 分页保存公司信息至es
//     * @param c
//     */
//    @Async("processExecutor")
//    public void savePageCompanyMsg(MongoCollection c
//            , int base
//            , int count
//            , String status
//    ) {
//        logger.info("MongoCollection -> {} ", c);
//
//        if (base == 1) {
//            Page3Dto<Object> page3Dto =  MongoQuery.build(c).page3(1, base, Object.class, p -> p, "{}");
//            if (page3Dto != null) {
//                for (Object p : page3Dto.getData()) {
//                    ResumeModel r = ResumeData.conver(JSON.toJSON(p));
//                    EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY_RESUME, r.getName(), JSON.toJSON(r));
//                    createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY_RESUME, ResumeModel.class);
////                    createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY, QccCompany.class);
//                    logger.info("test pre data success......==========================================================================........ Collection ---> " + c);
//                }
//            }
//        }
//
//        int len = count / PAGE_SIZE + 1;
//        MongoQuery mq = MongoQuery.build(c);
//
//        for (int i = 1; i <= len; i++) {
//            Page3Dto<Object> page3Dto =  mq.page3(i, PAGE_SIZE, Object.class, p -> p, "{}");
//
//            if (status.equals("eqcdetail")) {
//                logger.info("eqcdetail analysis ...........");
//                if (page3Dto != null)
//                    for (Object p : page3Dto.getData()) {
//                        QccCompany company = EqcDetailData.conver(JSON.toJSON(p));
//                        EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY, company.getCompanyId(), JSON.toJSON(company));
//                        logger.info("eqc detail add es success!    index -> {}, type --> {}, id --> {}, name -> {},  source -- > {}",
//                                EsConst.INDEX, EsConst.TYPE_COMPANY, company.getCompanyId(), company.getQygsDetail().getName(), JSON.toJSON(company));
//                    }
//
//            } else if (status.equals("qccdetail")) {
//                logger.info("qccdetail analysis ...........");
//                if (page3Dto != null)
//                    for (Object p : page3Dto.getData()) {
//                        QccCompany company = QccDetailData.conver(JSON.toJSON(p));
//                        EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY, company.getCompanyId(), JSON.toJSON(company));
//                        logger.info("qcc detail add es success!    index -> {}, type --> {}, id --> {}, name -> {}, source -- > {}",
//                                EsConst.INDEX, EsConst.TYPE_COMPANY, company.getCompanyId(), company.getQygsDetail().getName(), JSON.toJSON(company));
//                    }
//
//            } else if (status.equals("gxzgdetail")) {
//                logger.info("gxzgdetail analysis ...........");
//                if (page3Dto != null)
//                    for (Object p : page3Dto.getData()) {
//                        logger.info("==============come on!==================");
//                        QccCompany company = GxzgDetaliData.conver(JSON.toJSON(p));
//                        EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY, company.getCompanyId(), JSON.toJSON(company));
//                        logger.info("gxzg detail add es success!    index -> {}, type --> {}, id --> {}, name -> {}, source -- > {}",
//                                EsConst.INDEX, EsConst.TYPE_COMPANY, company.getCompanyId(), company.getQygsDetail().getName(), JSON.toJSON(company));
//                    }
//
//            } else if (status.equals("qcc_zscq")) {
//                logger.info("qcc zscq analysis ...........");
//                if (page3Dto != null)
//                    for (Object p : page3Dto.getData()) {
//                        QccCompany company = QccZscq.getZscqAboutQcc(JSON.toJSON(p));
//                        EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY_ZSCQ, company.getCompanyId(), JSON.toJSON(company));
//                        logger.info("qcc_zscq add es success!    index -> {}, type --> {}, id --> {}, name -> {}, source -- > {}",
//                                EsConst.INDEX, EsConst.TYPE_COMPANY_ZSCQ, company.getCompanyId(), company.getName(), JSON.toJSON(company));
//                    }
//
//            } else if (status.equals("resumes")) {
//                logger.info("resumes analysis ...........");
//                if (page3Dto != null)
//                    for (Object p : page3Dto.getData()) {
//                        ResumeModel r = ResumeData.conver(JSON.toJSON(p));
//                        EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY_RESUME, r.getUrl(), JSON.toJSON(r));
//                        logger.info("resumes add es success!    index -> {}, type --> {}, id --> {}, name -> {}, source -- > {}",
//                                EsConst.INDEX, EsConst.TYPE_COMPANY_RESUME, r.getUrl(), r.getName(), JSON.toJSON(r));
//                    }
//
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        int count = 100000;
//        MongoClient mc =  new MongoConfig().onlyTest();
////        MongoCollection gxzg =  getCol(mc.getDB("gxzg_org_cn"), "detail_results1");
////        MongoCollection eqc =  getCol(mc.getDB("eqicha_com"), "detail_results");
////        MongoCollection qcc =  getCol(mc.getDB("qichacha_com"), "detail_results");
//        MongoCollection resumes = getCol(mc.getDB("58guakao_com"), "new_detail_results");
////        MongoCollection zcgl =  getCol(mc.getDB("zcgl"), "company_info");
//
//        SaveCompanyDataController scd = new SaveCompanyDataController();
//
//        scd.savePageCompanyMsg(resumes, 1, 500, "resumes");
////        scd.saveZcgl(zcgl, 1, count);
//
////        scd.savePageCompanyMsg(gxzg, count, "gxzgdetail");
////        scd.savePageCompanyMsg(eqc, count, "eqcdetail");
////        scd.savePageCompanyMsg(qcc, count, "qccdetail");
//
//        System.out.println("===============END=========================");
//    }
//
//    /**
//     * 保存资产管理信息
//     */
//    @Async("processExecutor")
//    public void saveZcgl(MongoCollection c, int base, int count, String q) {
//
//        if (base == 1) {
//            Page3Dto<Object> page3Dto = MongoQuery.build(c).page3(1, base, Object.class, p -> p, q);
//            if (page3Dto != null) {
//                for (Object p : page3Dto.getData()) {
//                    ZcglModel company = ZcglData.conver(JSON.toJSON(p));
//                    EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, company.getCompanyId(), JSON.toJSON(company));
//                    createMapping(EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, ZcglModel.class);
//                    logger.info("zcgl add es success!    index -> {}, type --> {}, id --> {}, name -> {}, source -- > {}",
//                            EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, company.getCompanyId(), company.getQymc(), JSON.toJSON(company));
//                }
//            }
//        }
//
//        if (count == 1) return;
//        int len = count / PAGE_SIZE + 1;
//        MongoQuery mq = MongoQuery.build(c);
//
//        logger.info("zcgl analysis ...........len size--->" + len);
//        for (int i = 1; i <= len; i++) {
//            Page3Dto<Object> page3Dto = mq.page3(i, PAGE_SIZE, Object.class, p -> p, "{}");
//            if (page3Dto != null)
//                for (Object p : page3Dto.getData()) {
//                    ZcglModel company = ZcglData.conver(JSON.toJSON(p));
//                    EsUtils.add(EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, company.getCompanyId(), JSON.toJSON(company));
//                    logger.info("zcgl add es success!    index -> {}, type --> {}, id --> {}, name -> {}, source -- > {}",
//                            EsConst.INDEX, EsConst.TYPE_COMPANY_ZCGL, company.getCompanyId(), company.getQymc(), JSON.toJSON(company));
//                }
//        }
//    }
//
//}
