package net.dgg.zqq.controller.word;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.dgg.zqq.dto.export.ProxyDto;
import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.utils.ImageUtils;
import net.dgg.zqq.utils.JsonUtils;
import net.dgg.zqq.utils.MapUtils;
import net.dgg.zqq.utils.es.EsConst;
import net.dgg.zqq.utils.es.services.EsService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.dgg.zqq.controller.search.CompanyItemController.indexs;
import static net.dgg.zqq.controller.search.CompanyItemController.types;


@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private EsService esService;

    @Autowired
    private Configuration cfg;

    //proxy
    @RequestMapping(value = "/proxy", method = RequestMethod.POST)
    public @ResponseBody
    void exportProxy(ProxyDto proxyDto, HttpServletResponse response) throws IOException {
        try {
            //验证参数
            Assert.notNull(proxyDto, "参数不能为空");
            Assert.hasText(proxyDto.getType(), "请输入类型");
            if ("0".equals(proxyDto.getType())) {
                //个人
                Assert.hasText(proxyDto.getIdCard(), "请输入身份证号码");
            }
            Assert.hasText(proxyDto.getClient(), "请输入委托人");
            Assert.hasText(proxyDto.getClientAddress(), "请输入委托人地址");
            Assert.hasText(proxyDto.getContactName(), "请输入联系人姓名");
            Assert.hasText(proxyDto.getContactPhone(), "请输入联系人电话");
            Assert.hasText(proxyDto.getPostalCode(), "请输入邮政编码");

            Map map = MapUtils.convertBean(proxyDto);
            LocalDate date = LocalDate.now();
            map.put("year", date.getYear() + "");
            map.put("month", date.getMonth().getValue());
            map.put("day", date.getDayOfMonth());

            //导出word
            Template template = null;
            if ("0".equals(proxyDto.getType())) {
                //个人
                template = cfg.getTemplate("person.ftl");
            } else {
                template = cfg.getTemplate("business.ftl");
            }
            File file = null;
            InputStream fin = null;
            ServletOutputStream out = null;
            try {
                // 调用工具类的createDoc方法生成Word文档
                file = createDoc(map, template);
                fin = new FileInputStream(file);

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/msword");
                // 设置浏览器以下载的方式处理该文件名
                String fileName = "商标委托书" + RandomStringUtils.randomAlphabetic(10) + ".doc";
                response.setHeader("Content-Disposition", "attachment;filename="
                        .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

                out = response.getOutputStream();
                byte[] buffer = new byte[512];  // 缓冲区
                int bytesToRead = -1;
                // 通过循环将读入的Word文件的内容输出到浏览器中
                while ((bytesToRead = fin.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }
            } finally {
                if (fin != null) fin.close();
                if (out != null) out.close();
                if (file != null) file.delete(); // 删除临时文件
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            RestResponse restResponse = new RestResponse();
            restResponse.setCode(1);
            restResponse.setMsg(e.getMessage());
            restResponse.setData(null);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonUtils.obj2Json(restResponse));
            printWriter.close();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            RestResponse restResponse = new RestResponse();
            restResponse.setCode(1);
            restResponse.setMsg("系统异常");
            restResponse.setData(null);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonUtils.obj2Json(restResponse));
            printWriter.close();
            response.flushBuffer();
        }
    }

    @RequestMapping("/patent")
    public @ResponseBody
    void exportWord(HttpServletRequest request,
                    HttpServletResponse response) {
        String key = "applicationNumber";
        String val = "CN201720814390";
        //数据封装
        Map map = getData(key, val);
        //导出word
        Template template = null;
        try {
            //Configuration cfg = new Configuration(Configuration.VERSION_2_3_21);
            template = cfg.getTemplate("wordTemplate.ftl");
            File file = null;
            InputStream fin = null;
            ServletOutputStream out = null;
            try {
                // 调用工具类的createDoc方法生成Word文档
                file = createDoc(map, template);
                fin = new FileInputStream(file);

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/msword");
                // 设置浏览器以下载的方式处理该文件名
                String fileName = "专利" + ".doc";
                response.setHeader("Content-Disposition", "attachment;filename="
                        .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

                out = response.getOutputStream();
                byte[] buffer = new byte[512];  // 缓冲区
                int bytesToRead = -1;
                // 通过循环将读入的Word文件的内容输出到浏览器中
                while ((bytesToRead = fin.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }
            } finally {
                if (fin != null) fin.close();
                if (out != null) out.close();
                if (file != null) file.delete(); // 删除临时文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/trademarker")
    public @ResponseBody
    void exportTrademarker(HttpServletRequest request,
                           HttpServletResponse response) {
        String type = "第25类-服装鞋帽";
        String appNo = "21028416";
        final String t = PTConst.MIX_BRAND;
        List<Map> dataList = esService.getDataOneBrand(indexs(t), types(t), type, appNo);
        Map dataMap = dataList.get(0);

        Map map = new HashMap();
        map.put("name", dataMap.get("name")); //商标名
        map.put("status", dataMap.get("status"));//商标状态
        map.put("sbType", dataMap.get("sbType")); //商标类别
        map.put("regNo", dataMap.get("regNo"));//申请号
        map.put("appDate", dataMap.get("appDate"));//申请日期
        map.put("applicantCn", dataMap.get("applicantCn"));//申请人中文
        map.put("addressCn", dataMap.get("addressCn"));//申请人地址中文
        map.put("applicantEn", dataMap.get("applicantEn"));//申请人英文
        map.put("addressEn", dataMap.get("addressEn")); //申请人地址英文

        String imageUrl = ImageUtils.image2Base64((String) dataMap.get("imageUrl"));
        map.put("imageUrl", imageUrl);  //图片地址

        map.put("tmGoodsService", dataMap.get("tmGoodsService"));  //商品服务列表
        map.put("announcementIssue", dataMap.get("announcementIssue")); //初审公告期号
        map.put("announcementDate", dataMap.get("announcementDate"));//初审公告日期
        map.put("regIssue", dataMap.get("regIssue"));//注册公告期号
        map.put("regDate", dataMap.get("regDate"));//注册公告日期
        map.put("validPeriod", dataMap.get("validPeriod")); //有效期区间
        map.put("houQiZhiDingDate", dataMap.get("houQiZhiDingDate"));//后期指定日期
        map.put("guoJiZhuCeDate", dataMap.get("guoJiZhuCeDate")); //国际注册日期
        map.put("youXianQuanDate", dataMap.get("youXianQuanDate"));// //优先权日期
        map.put("agent", dataMap.get("agent"));//代理人名称
        map.put("tmGongGaoyFlow", dataMap.get("tmGongGaoyFlow")); //公告流程

        //导出word
        Template template = null;
        try {
            template = cfg.getTemplate("trademerker.ftl");
            File file = null;
            InputStream fin = null;
            ServletOutputStream out = null;
            try {
                // 调用工具类的createDoc方法生成Word文档
                file = createDoc(map, template);
                fin = new FileInputStream(file);

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/msword");
                // 设置浏览器以下载的方式处理该文件名
                String fileName = "商标" + ".doc";
                response.setHeader("Content-Disposition", "attachment;filename="
                        .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
                out = response.getOutputStream();
                byte[] buffer = new byte[512];  // 缓冲区
                int bytesToRead = -1;
                // 通过循环将读入的Word文件的内容输出到浏览器中
                while ((bytesToRead = fin.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }
            } finally {
                if (fin != null) fin.close();
                if (out != null) out.close();
                if (file != null) file.delete(); // 删除临时文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createDoc(Map<?, ?> dataMap, Template template) {
        String name = "sellPlan.doc";
        File f = new File(name);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

    private Map getData(String key, String val) {
        List<Map> dataList = esService.getMsgByKeyWordCommon(key, val, EsConst.INDEX_PATENT, EsConst.TYPE_COMPANY_PATENT);
        Map dataMap = dataList.get(0);
        Map map = new HashMap();
        String patentImage = null;
        String patentImageName = null;
        if (dataMap.get("patentImage") != null) {
            patentImage = ImageUtils.image2Base64((String) dataMap.get("patentImage"));
            patentImageName = (String) dataMap.get("patentImage");
        }

        List<String> list = new ArrayList<>();
        if (dataMap.get("desDrawings") != null) {
            ArrayList<Map> imgList = (ArrayList) dataMap.get("desDrawings");
            for (Map imgMap : imgList) {
                String url = (String) imgMap.get("clUrl");
                String image = ImageUtils.image2Base64(url);
                list.add(image);
            }
        }

        map.put("imgList", list);
        map.put("patentImage", patentImage);
        map.put("patentImageName", patentImageName);
        map.put("piApplyPublishNum", dataMap.get("piApplyPublishNum"));
        map.put("piApplyAnnounceDate", dataMap.get("piApplyAnnounceDate"));
        map.put("kindCodeDesc", dataMap.get("kindCodeDesc"));
        map.put("applicationNumber", dataMap.get("applicationNumber"));
        map.put("applicationDate", dataMap.get("applicationDate"));
        map.put("assigneestring", dataMap.get("assigneestring"));
        map.put("piAddress", dataMap.get("piAddress"));
        map.put("inventorString", dataMap.get("inventorString"));
        map.put("agency", dataMap.get("agency"));
        map.put("agent", dataMap.get("agent"));
        map.put("piInventName", dataMap.get("piInventName"));
        map.put("abstracts", dataMap.get("abstracts"));
        map.put("claInfo", dataMap.get("claInfo"));
        map.put("desInfo", dataMap.get("desInfo"));
        return map;
    }

}
