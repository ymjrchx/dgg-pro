package net.dgg.dqc.backservice.controller;

import com.baidu.aip.imagesearch.AipImageSearch;
import net.dgg.dqc.backservice.es.EsConst;
import net.dgg.dqc.backservice.framework.ai.xst.ClientAipOcr;
import net.dgg.dqc.backservice.service.SyncDealDataService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static net.dgg.dqc.backservice.utils.BaseUtil.minusDay;


/**
 * Created by jiangsh on 2018/8/9 09:57
 */
@Controller
@RequestMapping("/syncDealData/")
public class SyncDealDataController extends BaseController {

    @Autowired
    private SyncDealDataService syncDealDataService;

    @RequestMapping(value = "/startInit", method = RequestMethod.GET)
    @ResponseBody
    public Object startInit() {
        final String index = EsConst.INDEX;
        final String type  = EsConst.TYPE_COMPANY;
        syncDealDataService.queryAllqcc("qichacha_com", "detail_dgg", index, type);
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startEqc", method = RequestMethod.GET)
    @ResponseBody
    public Object startEqc() {
        syncDealDataService.queryAlleqc("eqicha_com", "detail_results");
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startQcc", method = RequestMethod.GET)
    @ResponseBody
    public Object startQcc() {
        final String index = EsConst.INDEX;
        final String type  = EsConst.TYPE_COMPANY;
        syncDealDataService.queryAllqcc("qichacha_com", "detail_results", index, type);
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startGxzg", method = RequestMethod.GET)
    @ResponseBody
    public Object startGxzg() {
//        syncDealDataService.queryAllgxzg("gxzg_org_cn", "guangdong_zizhi_detail_results");
        syncDealDataService.queryAllgxzg("gxzg_org_cn", "detail_results");
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startStand", method = RequestMethod.GET)
    @ResponseBody
    public Object startStand() {
//        syncDealDataService.queryAllstand("qichamao_xiaowang", "detail_results");
        syncDealDataService.queryAllstand("qichacha_com", "temp_one_page_results");
        return this.getSuccessResponse("success");
    }


    @RequestMapping(value = "/startZcgl", method = RequestMethod.GET)
    @ResponseBody
    public Object startZcgl() {
        syncDealDataService.queryAllzcgl("zcgl", "company_info");
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startResumes", method = RequestMethod.GET)
    @ResponseBody
    public Object startResumes() {
        syncDealDataService.queryAllResumes("58guakao_com", "new_detail_results");
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startNews", method = RequestMethod.GET)
    @ResponseBody
    public Object startNews() {
        final String index = EsConst.INDEX_NEW;
        final String type  = EsConst.TYPE_COMPANY_NEWS;
//        final String q = "{\"pubdate\" : {$gte:\"" + minusDay(2) +"\"}}";
//        syncDealDataService.queryAllnews(index, type,"jinritoutiao_com", "detail_results");
        syncDealDataService.queryAllnews(index, type,"iprdaily_com", "detail_results");
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startBrand", method = RequestMethod.GET)
    @ResponseBody
    public Object startBrand() {
        final String index = EsConst.INDEX_BRAND;
        final String type  = EsConst.TYPE_COMPANY_BRAND;
//        final String query = "{\"loadTime\" : {$gte:\"2018-08-22T11:32:45\"}}";
        final String query = null;
//        syncDealDataService.queryAllbrand(index, type,"eqicha_com", "detail_results", "eqc-brand", 50000, 0); //易企查
//        syncDealDataService.queryAllbrand(index, type,"shangbiao_db", "zhigaodian_detail_results", "zhigaodian_detail_results", 50000, 0, 0, query); //智高点
//        syncDealDataService.queryAllbrand(index, type,"shangbiao_db", "qianhui_detail_results", "qianhui_detail_results", 40000, 0, 0, query); //千慧
//        syncDealDataService.queryAllbrand(index, type,"shangbiao_db", "maibiaoku_detail_results", "maibiaoku_detail_results", 40000, 0, 0, query); //麦标库
        syncDealDataService.queryAllbrand(index, type,"shangbiao_db", "subiao_detail_results", "subiao_detail_results", 40000, 0, 0, query); //苏标




//        syncDealDataService.queryAllbrand(index, type,"qichacha_com", "detail_results", "qcc-brand", 10000, 0, null); //企查查
//        syncDealDataService.queryAllbrand(index, type,"qichacha_com", "detail_results", "qcc-brand", 10000, 0, null); //企查查
//        syncDealDataService.queryAllbrand(index, type,"qichacha_com", "zhichan", "qcc-zhichan-brand", 10000, 0,null); //企查查-知识产权
//        syncDealDataService.queryAllbrand(index, type,"gxzg_org_cn", "guangdong_zizhi_detail_results", "gxzg-brand", 10000, 0, null); //公信中国，只有基本信息，无商标数据，不用跑
//        syncDealDataService.queryAllbrand(index, type,"tyc_com", "detail_results", "stand-brand", 500, 0, null); //标准
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startCpws", method = RequestMethod.GET)
    @ResponseBody
    public Object startCpws() {
        final String index = EsConst.INDEX_CPWS;
        final String type  = EsConst.TYPE_COMPANY_CPWS;
        final String query = null;
//        syncDealDataService.queryAllCpws(index, type, "falv_db", "tmp_wusong_detail_results", "cpws", 3000, 0, query); //裁判文书
        syncDealDataService.queryAllCpws(index, type, "wenshu_cn", "CPWS_XIANGQING", "cpws", 50000, 0, query); //裁判文书
        return this.getSuccessResponse("success");
    }

    @RequestMapping(value = "/startPatent", method = RequestMethod.GET)
    @ResponseBody
    public Object startPatent() {
        final String index = EsConst.INDEX_PATENT;
        final String type  = EsConst.TYPE_COMPANY_PATENT;
//        syncDealDataService.queryAllpatent(index, type,"eqicha_com", "detail_results", "eqc-patent", 20000, 0); //易企查
//        syncDealDataService.queryAllpatent(index, type,"qichacha_com", "detail_results", "qcc-patent", 10000, 0); //企查查详情，没有专利信息
//        syncDealDataService.queryAllpatent(index, type,"qichacha_com", "zhichan", "qcc-zhichan-patent", 10000, 0); //企查查-知识产权，没有专利信息
        syncDealDataService.queryAllpatent(index, type,"patent_com", "sooip_results", "sooip_results", 10000, 0); //sooip
        return this.getSuccessResponse("success");
    }


    /**
     * 存储扫描名片识别后的数据至Mongo
     * @return
     */
    @RequestMapping(value = "/saveBusinessCardImg", method = RequestMethod.GET)
    @ResponseBody
    public Object saveBusinessCardImg() {
        final String imgPath = "D:\\DGG\\files\\成都名片466张";
        syncDealDataService.saveBusinessCardImg(imgPath);
        return this.getSuccessResponse("success");
    }

    /**
     * 存储mongo图片数据至 百度云
     * @return
     */
    @RequestMapping(value = "/saveXstImg", method = RequestMethod.GET)
    @ResponseBody
    public Object saveXstImg() {
        final String query = null;
//        final String imgPath = "D:\\DGG\\files\\brandImg\\";
//        final String imgPath = "D:\\DGG\\files\\allImage\\qccImage\\";
        final String imgPath = "http://upload.dgg.net/";
        final AipImageSearch client = ClientAipOcr.getAipImageSearch();
        final String tags = "100,10"; //分类
        syncDealDataService.saveXstImg("shangbiao_db", "quanchacha_detail_results", "xst", 200000, 24990, 200000, query, imgPath, client, tags);
        return this.getSuccessResponse("success");
    }
}
