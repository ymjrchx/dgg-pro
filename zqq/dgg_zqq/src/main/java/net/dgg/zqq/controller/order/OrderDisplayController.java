package net.dgg.zqq.controller.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.constant.ApplocantTypeConstant;
import net.dgg.zqq.constant.OrderStatusConstant;
import net.dgg.zqq.constant.OrderTypeConstant;
import net.dgg.zqq.constant.UploadFileType;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.dao.order.TrademarkClassDao;
import net.dgg.zqq.entity.order.Assess;
import net.dgg.zqq.entity.order.Order;
import net.dgg.zqq.entity.order.TrademarkAndApplicant;
import net.dgg.zqq.entity.order.TrademarkClass;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.dgg.zqq.services.impl.UserServiceImpl;
import net.dgg.zqq.services.order.ApplicantTemplateService;
import net.dgg.zqq.services.order.AssessService;
import net.dgg.zqq.services.order.OrderDisplayService;
import net.dgg.zqq.services.order.TrademarkAndApplicantService;
import net.dgg.zqq.utils.MapUtils;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description: 订单展示管理系统
 * @Author: huangl
 * @Date: 2018/10/9 15:08
 */

@Controller
@RequestMapping("/orderDisplay")
@Api(description = "订单-后台管理")
public class OrderDisplayController extends BaseController {

    @Autowired
    OrderDisplayService orderDisplayService;
    @Autowired
    private TranslateUtil translateUtil;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    TrademarkAndApplicantService trademarkAndApplicantService;
    @Autowired
    ApplicantTemplateService applicantTemplateService;
    @Autowired
    TrademarkClassDao trademarkClassDao;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    private AssessService assessService;

    /**
     * 返回主页面
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String commentCheck() {
        return "orderDisplay/index";
    }

    //返回表单数据
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiOperation(value = "订单展示管理系统", notes = "订单展示管理系统", httpMethod = "POST")
    @ResponseBody
    public Object query(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.orderDisplayService.pageQuery(params);
        translateUtil.translateList(new String[]{"serviceTypeLevel1Code", "serviceTypeLevel3Code", "status", "type", "baoJianStatus"}, list);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }


    /**
     * 改变订单状态
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    @ApiOperation(value = "改变订单状态", notes = "改变订单状态", httpMethod = "POST")
    @ResponseBody
    public Object changeStatus(@RequestParam Long id, @RequestParam String status) {
        try {
            this.orderDisplayService.updateStatus(id, status);
            return this.getSuccessResponse("success");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统错误");
        }
    }


    /**
     * 返回订单改变页面
     */
    @RequestMapping(value = "/changePage", method = RequestMethod.GET)
    public String changePage(@RequestParam Long id, @RequestParam String status, Model model) {
        String path = "orderDisplay/changeStatus";
        model.addAttribute("id", id);
        model.addAttribute("status", status);
        return path;
    }

    @RequestMapping(value = "/znIndex", method = RequestMethod.GET)
    public String znIndex(Model model) {
        model.addAttribute("PAYED", OrderStatusConstant.PAYED);
        model.addAttribute("IN_PROCESS", OrderStatusConstant.IN_PROCESS);
        model.addAttribute("WAITING_AUDIT", OrderStatusConstant.WAITING_AUDIT);
        model.addAttribute("IN_PROCESS", OrderStatusConstant.IN_PROCESS);
        return "orderDisplay/znOrder";
    }


    //智能订单表单数据
    @RequestMapping(value = "/znQueryPage", method = RequestMethod.POST)
    @ApiOperation(value = "订单展示管理系统", notes = "订单展示管理系统", httpMethod = "POST")
    @ResponseBody
    public Object znQuery(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        params.put("type", OrderTypeConstant.AUTO);
        List<Map> list = this.orderDisplayService.pageQuery(params);
        for (Map map : list) {
            map.put("statusName", map.get("status"));
        }
        translateUtil.translateList(new String[]{"serviceTypeLevel1Code", "auditType", "serviceTypeLevel3Code", "statusName", "type", "baoJianStatus"}, list);
        for (Map map : list) {
            map.put("serviceTypeLevel", map.get("serviceTypeLevel1Code") + "-" + map.get("serviceTypeLevel3Code"));
        }
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    /**
     * 返回订单评估页面
     */
    @RequestMapping(value = "/assessOrder", method = RequestMethod.GET)
    public String assessOrder(String id, String way, Model model) {
        TrademarkAndApplicant tra = this.trademarkAndApplicantService.findByOrderId(id);
        String traType = tra.getType();
        switch (traType) {
            case "trademark_type1":
                traType = "文字";
                break;
            case "trademark_type2":
                traType = "图形";
                break;
            case "trademark_type3":
                traType = "文字及图形";
                break;
            case "trademark_type4":
                traType = "黑白";
                break;

            default:
                traType = "彩色";
                //...;
                break;
        }
        tra.setType(traType);
        Order order = this.orderDisplayService.findById(Long.valueOf(id));
        model.addAttribute("tra", tra);
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        List<TrademarkClass> list = this.trademarkClassDao.query(new HashMap() {{
            put("orderId", id);
        }});
        Map<String, TrademarkClass> noNameMap = new HashMap<>(list.size());
        Map<String, List<TrademarkClass>> noTradeClassListMap = new HashMap<>(list.size());
        for (TrademarkClass trademarkClass : list) {
            String code = trademarkClass.getClassLevel1Code();
            noNameMap.put(code, trademarkClass);
            List<TrademarkClass> trademarkClassList = noTradeClassListMap.containsKey(code) ? noTradeClassListMap.get(code) : new ArrayList<>();
            trademarkClassList.add(trademarkClass);
            noTradeClassListMap.put(code, trademarkClassList);
        }

        List<Map> dataList = new ArrayList<>(noNameMap.size());
        for (Map.Entry<String, TrademarkClass> entry : noNameMap.entrySet()) {
            String code = entry.getKey();
            TrademarkClass temp = entry.getValue();
            Map map = MapUtils.convertBean(temp);
            map.put("sonArr", noTradeClassListMap.get(code));
            dataList.add(map);
        }
        List<Assess> assessList = assessService.query(new HashMap() {{
            put("orderId", id);
        }});
        List assessData = new ArrayList();
        if (!assessList.isEmpty()) {
            for (Assess assess : assessList) {
                Map assessMap = new HashMap();
                assessMap.put("assessContent", assess.getContent());
                DateFormat bf = new SimpleDateFormat("yyyy-MM-dd E a HH:mm:ss");
                String createTime = bf.format(assess.getCreateTime());
                assessMap.put("createTime", createTime);
                String assessPhotos = assess.getPhoto();
                List photoList = new ArrayList();
                if (assessPhotos.contains(",")) {
                    String[] photoUrl = assessPhotos.split(",");
                    for (int i = 0; i < photoUrl.length; i++) {
                        photoList.add(photoUrl[i]);
                    }
                } else {
                    photoList.add(assessPhotos);
                }
                assessMap.put("photoList", photoList);
                assessData.add(assessMap);
            }
        }
        String host = fileUploadService.getHost();
        model.addAttribute("host", host);
        model.addAttribute("dataList", dataList);
        model.addAttribute("type", UploadFileType.AUTO_ORDER_CHECK);
        model.addAttribute("ENTERPRISE", ApplocantTypeConstant.ENTERPRISE);
        model.addAttribute("PERSON", ApplocantTypeConstant.PERSON);
        model.addAttribute("way", way);
        model.addAttribute("failAssess", assessData);
        return "orderDisplay/assess";
    }

    /**
     * 返回订到详情到自动报件页面
     */

    @RequestMapping(value = "/baojianOrder", method = RequestMethod.GET)
    public String baojianOrder(String id, Model model) {
        TrademarkAndApplicant tra = this.trademarkAndApplicantService.findByOrderId(id);
        Order order = this.orderDisplayService.findById(Long.valueOf(id));
        model.addAttribute("tra", tra);
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        List<TrademarkClass> list = this.trademarkClassDao.query(new HashMap() {{
            put("orderId", id);
        }});
        Map<String, TrademarkClass> noNameMap = new HashMap<>(list.size());
        Map<String, List<TrademarkClass>> noTradeClassListMap = new HashMap<>(list.size());
        for (TrademarkClass trademarkClass : list) {
            String code = trademarkClass.getClassLevel1Code();
            noNameMap.put(code, trademarkClass);
            List<TrademarkClass> trademarkClassList = noTradeClassListMap.containsKey(code) ? noTradeClassListMap.get(code) : new ArrayList<>();
            trademarkClassList.add(trademarkClass);
            noTradeClassListMap.put(code, trademarkClassList);
        }

        List<Map> dataList = new ArrayList<>(noNameMap.size());
        for (Map.Entry<String, TrademarkClass> entry : noNameMap.entrySet()) {
            String code = entry.getKey();
            TrademarkClass temp = entry.getValue();
            Map map = MapUtils.convertBean(temp);
            map.put("sonArr", noTradeClassListMap.get(code));
            dataList.add(map);
        }
        model.addAttribute("dataList", dataList);
        model.addAttribute("host", this.fileUploadService.getHost());
        model.addAttribute("ENTERPRISE", ApplocantTypeConstant.ENTERPRISE);
        model.addAttribute("PERSON", ApplocantTypeConstant.PERSON);
        return "orderDisplay/baojian";
    }

    /**
     * 评审
     */
    //智能订单表单数据
    @RequestMapping(value = "/examine", method = RequestMethod.POST)
    @ApiOperation(value = "评审", notes = "评审", httpMethod = "POST")
    @ResponseBody
    public Object examine(@ApiParam(name = "id", value = "订单ID", required = true) @RequestParam Long id,
                          @ApiParam(name = "content", value = "content", required = true) @RequestParam String content,
                          @ApiParam(name = "photo", value = "photo", required = false) @RequestParam(required = false) String photo,
                          @ApiParam(name = "operate", value = "操作（1：审核通过  0：审核不通过）", required = true) @RequestParam Integer operate) {
        try {
            this.orderDisplayService.examine(id, content, photo, operate);
            return this.getSuccessResponse("评审成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统错误");
        }

    }


}
