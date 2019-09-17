//package net.dgg.dqc.backservice.controller;
//
//import net.dgg.dqc.backservice.es.services.EsService;
//import net.dgg.dqc.backservice.service.CompanyNameResultService;
//import net.fblock.web.common.BaseController;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author 刘阳
// * @ClassName <TestMongoDbController>
// * @despriction
// * @create 2018/07/24 16:33
// **/
////@Controller
////@RequestMapping("/test/")
//public class TestMongoDbAndEsController extends BaseController {
//    @Autowired
//    private CompanyNameResultService companyNameResultService;
//
//    @Autowired
//    private EsService esService;
//
//    /**
//     * @return
//     */
//   /* @RequestMapping(value = "/countMongoDb", method = RequestMethod.GET)
//    @ResponseBody
//    public Object countMongoDb() {
//        try {
//            return this.getSuccessResponse(companyNameResultService.countMongodb());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this.getFailResponse("fail!");
//    }
//*/
//    /**
//     * @return
//     */
//   /* @RequestMapping(value = "/findOneFromMongoDb", method = RequestMethod.GET)
//    @ResponseBody
//    public Object findOneFromMongoDb(String id) {
//        try {
//            return this.getSuccessResponse(companyNameResultService.findOneFromMongoDb(id));
//        } catch (Exception e) {
//           // e.printStackTrace();
//            return this.getFailResponse(e.getMessage());
//        }
//
//    }*/
//
//    /**
//     * @return
//     */
//   /* @RequestMapping(value = "/countEs", method = RequestMethod.GET)
//    @ResponseBody
//    public Object countEs() {
//        try {
//            String index = "company_name_results", type="company_name",field = "companyName";
//
//            return this.getSuccessResponse(esService.getTotalCount(index,type,field,"成都"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this.getFailResponse("fail!");
//    }*/
//
//    /**
//     * @return
//     */
//    /*@RequestMapping(value = "/findOneFromEs", method = RequestMethod.GET)
//    @ResponseBody
//    public Object findOneFromEs(String id) {
//        try {
//            return this.getSuccessResponse(esService.);
//        } catch (Exception e) {
//            // e.printStackTrace();
//            return this.getFailResponse(e.getMessage());
//        }
//
//    }*/
//
//
//}
