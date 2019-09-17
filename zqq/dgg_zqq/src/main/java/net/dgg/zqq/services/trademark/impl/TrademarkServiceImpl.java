package net.dgg.zqq.services.trademark.impl;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.dao.trademark.TrademarkDao;
import net.dgg.zqq.services.trademark.TrademarkService;
import net.dgg.zqq.utils.ThreadHelper;
import net.dgg.zqq.utils.Toolkit;
import net.dgg.zqq.utils.es.EsClientUtils;
import net.dgg.zqq.utils.es.EsUtils;
import net.dgg.zqq.utils.es.stack.UserBoolQueryBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.noka.itextpdf.text.Document;
import org.noka.itextpdf.text.DocumentException;
import org.noka.itextpdf.text.Image;
import org.noka.itextpdf.text.PageSize;
import org.noka.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/10/11.
 */
@Slf4j
@Service
public class TrademarkServiceImpl implements TrademarkService {

    @Value("${firstTrial.index}")
    private String data_index;

    @Value("${firstTrial.type}")
    private String data_type;

    @Value("${debug}")
    private boolean debug;

    @Value("${firstTrial.brand.defaultImage}")
    private String defaultImage;

    @Value("${img.fileUrlPrefix}")
    private String fileUrlPrefix;

    @Autowired
    TrademarkDao trademarkDao;

    @Override
    public List<Map<String, Object>> getLast12Terms() {

        //查找今日数据缓存是否存在，如果存在，则返回缓存数据
        int current = 0;
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> query = new LinkedHashMap<>();
            if (i >= 1) {
                query.put("annNo <", current);
            }
            Double currentMax = EsUtils.getMax(data_index, data_type, "annNo", query, 0, 1);
            if (!Double.isNaN(currentMax) && !Double.isInfinite(currentMax)) {
                current = currentMax.intValue();
                Map<String, Object> queryData = new LinkedHashMap<>();
                queryData.put("annNo", current);
                List<Map<String, Object>> currentDatas = EsUtils.getList(data_index, data_type, queryData, 0, 1);
                Map<String, Object> data = currentDatas.get(0);
                Map<String, Object> reg = new LinkedHashMap<>();
                reg.put("annDate", data.get("annDate"));
                reg.put("annNo", data.get("annNo"));
                Long annTime = Toolkit.DateUtils.fromDate((String) data.get("annDate"));
                Long currentTime = System.currentTimeMillis();
                Long afterThreeMonthTime = Toolkit.DateUtils.addMonth(annTime, 3);
                String exceptionAppDate = Toolkit.DateUtils.format(afterThreeMonthTime, "yyyy-MM-dd");
                reg.put("exceptionAppDate", exceptionAppDate);
                long exceptionAppLeaveDay = (afterThreeMonthTime - currentTime) / (24L * 3600L * 1000L);
                reg.put("exceptionAppLeaveDay", exceptionAppLeaveDay > 0 ? exceptionAppLeaveDay : 0);
                Map<String, Object> queryCount = new LinkedHashMap<>();
                queryCount.put("annNo", current);
                Long count = EsUtils.count(data_index, data_type, queryCount);
                reg.put("total", count);
                result.add(reg);
            } else {
                break;
            }
        }
        return result;
    }

    public List<Map<String, Object>> queryField(String field, String text, int count) {
        Map<String, Object> cond = new LinkedHashMap<>();
        if (StringUtils.isNotEmpty(text)) {
            UserBoolQueryBuilder bool = (f, boolQueryBuilder) -> {
                boolQueryBuilder.must(QueryBuilders.wildcardQuery(f.concat(".keyword"), "*" + text + "*"));
            };
            cond.put(field + " user", bool);
        }
        Map<String, Long> counts = EsUtils.getDistinctText(data_index, data_type, cond, field, count);
        List<Map<String, Object>> maps = counts.keySet().stream().map(key -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("name", key);
            m.put("count", counts.get(key));
            return m;
        }).collect(Collectors.toList());
        return maps;
    }

    @SneakyThrows
    public Map<String, Object> queryPage(Map<String, Object> params) {
        Integer start = (Integer) params.get("start");
        Integer limit = (Integer) params.get("limit");
        Map<String, Object> condition = new LinkedHashMap<>();
        buildCondition(condition, params);
        Long count = EsUtils.count(data_index, data_type, condition);
        List<Map<String, Object>> list = EsUtils.getList(data_index, data_type, condition, start, limit, null, new String[]{"intCls"});
        list.stream().forEach(this::fillData);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("list", list);
        result.put("count", count);
        return result;
    }

    @Override
    public Map<String, Object> getTradeMark(String id) {
        Map<String, Object> map = EsUtils.get(data_index, data_type, id);
        if (map != null) {
            fillData(map);
        }
        return map;
    }

    @Override
    public Map<Integer, String> getAllTypes() {
        Map<Integer, String> typeNames = new LinkedHashMap<>();
        Map<Integer, String> types = getDistinctNumericTypeText(data_index, data_type, new LinkedHashMap<>(), "intCls");
        for (Integer id : types.keySet()) {
            Map<String, Object> type = trademarkDao.queryById(String.valueOf(id));
            if (type != null) {
                typeNames.put(id, "第" + id + "类" + "-" + type.get("name"));
            } else {
                typeNames.put(id, "第" + id + "类");
            }
        }
        return typeNames;
    }

    @Override
    @SneakyThrows
    public void exportResponse(Map<String, Object> params, HttpServletResponse response) {
        Map<String, Object> condition = new LinkedHashMap<>();
        buildCondition(condition, params);
        ThreadHelper.putThreadContextVar("params", params);
        List<Map<String, Object>> list = EsUtils.getList(data_index, data_type, condition, 0, 100);
        list.stream().forEach(this::fillData);
        write(list, response);
    }

    @Override
    @SneakyThrows
    public void exportPdf(String id, HttpServletResponse response) {
        Map<String, Object> img = EsUtils.get(data_index, data_type, id);
        fillData(img);
        String name = (String) img.get("tmName");
        Map<String, Object> gg = (Map<String, Object>) img.get("ggImgLocalData");
        String url = (String) gg.get("ggUrl");
        Document document = new Document();
        response.setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode("" + name + "-初审" + "公告" + ".pdf", "UTF-8"));
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, fos);
            document.addAuthor("知千秋");
            document.addSubject("商标详情.");
            document.setPageSize(PageSize.A4);
            document.open();
            Toolkit.Https https = new Toolkit.Https();
            Image image = Image.getInstance(https.getBinary(url, null, "UTF-8"));
            float imageHeight = image.getScaledHeight();
            float imageWidth = image.getScaledWidth();
            int i = 0;
            while (imageHeight > 500 || imageWidth > 500) {
                image.scalePercent(100 - i);
                i++;
                imageHeight = image.getScaledHeight();
                imageWidth = image.getScaledWidth();
            }
            image.setAlignment(Image.ALIGN_CENTER);
            document.add(image);
        } catch (DocumentException de) {
            System.out.println(de.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        document.close();
        fos.flush();
        byte[] data = fos.toByteArray();
        OutputStream oss = response.getOutputStream();
        oss.write(data);
    }

    private void fillData(Map<String, Object> item) {
        //重命名字段
        item.put("regNo", item.get("regNum"));
        item.put("announcementIssue", item.get("annNo"));
        item.put("name", item.get("tmName"));
        item.put("applicantCn", item.get("regName"));
        item.put("announcementDate", item.get("annDate"));

        String intCls = (String) item.get("intCls");
        if (StringUtils.isNotEmpty(intCls)) {
            Map<String, Object> classInfo = trademarkDao.queryById(intCls);
            if (classInfo != null) {
                item.put("intCls", "第" + intCls + "类" + "-" + classInfo.get("name"));
            }
        }

        String imageUrl = (String) item.get("imageUrl");
        if (StringUtils.isEmpty(imageUrl)) {
            item.put("imageUrl", defaultImage);
        }

        Object ggImgLocalData = item.get("ggImgLocalData");
        if (ggImgLocalData != null && ggImgLocalData instanceof Map) {
            Map<String, Object> local = (Map<String, Object>) ggImgLocalData;
            String ggPath = (String) local.get("ggPath");
            if (StringUtils.isNotEmpty(ggPath)) {
                local.put("ggUrl", fileUrlPrefix.concat(ggPath));
            }
        }

        //计算拒绝申请日期，剩余天数
        if (item.get("annDate") != null) {
            Long annTime = Toolkit.DateUtils.fromDate((String) item.get("annDate"));
            long currentTime = Clock.systemUTC().millis();
            Long afterThreeMonthTime = Toolkit.DateUtils.addMonth(annTime, 3);
            String exceptionAppDate = Toolkit.DateUtils.format(afterThreeMonthTime, "yyyy-MM-dd");
            item.put("exceptionAppDate", exceptionAppDate);
            long exceptionAppLeaveDay = (afterThreeMonthTime - currentTime) / (24L * 3600L * 1000L);
            item.put("exceptionAppLeaveDay", exceptionAppLeaveDay < 0L ? 0L : exceptionAppLeaveDay);
        }
    }

    @SneakyThrows
    private void write(List<Map<String, Object>> datas, HttpServletResponse response) {
        Toolkit.Https https = new Toolkit.Https();
        Map<String, Object> params = ThreadHelper.getThreadContextVar("params");
        OutputStream outputStream = response.getOutputStream();
        HSSFWorkbook wb = new HSSFWorkbook(TrademarkService.class.getResourceAsStream("/temps/firstTrialTemplate.xls"));
        HSSFSheet sheet = wb.getSheetAt(0);
        if (datas.isEmpty()) {

        } else {
            List<String> titles = new ArrayList<>();
            if (params.get("name") != null && StringUtils.isNotEmpty((String) params.get("name"))) {
                titles.add("商标名-" + params.get("name"));
            }
            if (params.get("announcementIssue") != null && StringUtils.isNotEmpty((String) params.get("announcementIssue"))) {
                titles.add("期号-" + params.get("announcementIssue"));
            }
            if (params.get("intCls") != null && StringUtils.isNotEmpty((String) params.get("intCls"))) {
                titles.add("分类列表-" + params.get("intCls"));
            }
            if (params.get("tmSection") != null && StringUtils.isNotEmpty((String) params.get("tmSection"))) {
                titles.add("代理-" + params.get("tmSection"));
            }

            String title = Toolkit.StringHelper.joinUse(titles.toArray(new String[0]), ",");

            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("知千秋商标-" + Toolkit.DateUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss") + ".xls", "UTF-8"));
            sheet.getRow(0).getCell(0).setCellValue(title + "初审公告查询结果");
            String mark = "* 该导出结果系以“" + title + "”作为关键词检索的初审公告数据结果，生成日期：" + Toolkit.DateUtils.format(System.currentTimeMillis(), "yyyy年MM月dd日");
            sheet.getRow(1).getCell(0).setCellValue(mark);

            byte[] defaultImageData = IOUtils.toByteArray(TrademarkService.class.getResourceAsStream("/temps/default.png"));

            Map<String, byte[]> imageData = new ConcurrentHashMap<>();
            datas.stream().map(data -> (String) data.get("imageUrl")).parallel().forEach(url -> {
                try {
                    byte[] img = https.getBinary(url, null, "utf-8");
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));
                    if (image != null && image.getWidth() > 0) {
                        imageData.put(url, img == null || img.length == 0 ? defaultImageData : img);
                    } else {
                        imageData.put(url, defaultImageData);
                    }
                } catch (Exception e) {
                    imageData.put(url, defaultImageData);
                    log.debug("{}", e);
                }
            });
            for (int i = 0; i < datas.size(); i++) {
                try {
                    Map<String, Object> data = datas.get(i);
                    byte[] image = imageData.get(data.get("imageUrl"));
                    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                    /**
                     * 该构造函数有8个参数
                     * 前四个参数是控制图片在单元格的位置，分别是图片距离单元格left，top，right，bottom的像素距离
                     * 后四个参数，前连个表示图片左上角所在的cellNum和 rowNum，后天个参数对应的表示图片右下角所在的cellNum和 rowNum，
                     * excel中的cellNum和rowNum的index都是从0开始的
                     */
                    //图片一导出到单元格B2中
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 0, i + 3, (short) 1, i + 5);
                    // 插入图片
                    patriarch.createPicture(anchor, wb.addPicture(image, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    HSSFRow row = sheet.createRow(i + 3);

                    HSSFCell typeCell = row.createCell(1);
                    typeCell.setCellValue((String) data.get("intCls"));

                    HSSFCell regNoCell = row.createCell(2);
                    regNoCell.setCellValue((String) data.get("regNo"));

                    HSSFCell name = row.createCell(3);
                    name.setCellValue((String) data.get("name"));

                    HSSFCell appDate = row.createCell(4);
                    appDate.setCellValue((String) data.get("applicationDate"));

                    HSSFCell announcementDate = row.createCell(5);
                    announcementDate.setCellValue((String) data.get("annDate"));

                    Long currentTime = Toolkit.DateUtils.fromDate((String) data.get("annDate"));
                    Long afterThreeMonthTime = Toolkit.DateUtils.addMonth(currentTime, 3);
                    String exceptionAppDate = Toolkit.DateUtils.format(afterThreeMonthTime, "yyyy-MM-dd");
                    HSSFCell exceptionAppDateCell = row.createCell(6);
                    exceptionAppDateCell.setCellValue(exceptionAppDate);

                    HSSFCell applicantCn = row.createCell(7);
                    applicantCn.setCellValue((String) data.get("applicantCn"));

                    HSSFCell addressCn = row.createCell(8);
                    addressCn.setCellValue((String) data.get("userAddress"));

                    HSSFCell agent = row.createCell(9);
                    agent.setCellValue((String) data.get("tmSection"));

                    HSSFCell announcementIssue = row.createCell(10);
                    announcementIssue.setCellValue(Integer.valueOf((Integer) data.get("annNo")));

                    HSSFCell annPage = row.createCell(11);
                    annPage.setCellValue(String.valueOf((Integer) data.get("annPage")));

                    HSSFCell tmGoodsService = row.createCell(12);
                    tmGoodsService.setCellValue((String) data.get("goods"));

                    HSSFCell serverNum = row.createCell(13);
                    serverNum.setCellValue((String) data.get("goodsNum"));

                    row.createCell(14).setCellValue("初审公告");

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("{}", e);
                } finally {

                }
            }
            @Cleanup ByteArrayOutputStream baos = new ByteArrayOutputStream();
            wb.write(baos);
            baos.flush();
            @Cleanup ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            byte[] buf = new byte[1024];
            int count;
            while ((count = bais.read(buf)) != -1) {
                outputStream.write(buf, 0, count);
                outputStream.flush();
            }
            outputStream.flush();
        }
    }

    private void buildCondition(Map<String, Object> condition, Map<String, Object> param) {
        //期号查询条件
        String announcementIssue = (String) param.get("announcementIssue");
        if (StringUtils.isNotEmpty(announcementIssue)) {
            if (announcementIssue.indexOf(",") > -1) {
                condition.put("annNo in", announcementIssue.split(","));
            } else {
                condition.put("annNo =", announcementIssue);
            }
        }
        //商标名称
        String name = (String) param.get("name");
        if (StringUtils.isNotEmpty(name)) {
            String matchMethod = (String) param.get("matchMethod");
            UserBoolQueryBuilder userBoolQueryBuilder = null;
            if (matchMethod.equalsIgnoreCase("FULL")) {
                userBoolQueryBuilder = (field, boolQueryBuilder) -> {
                    try {
                        boolQueryBuilder.must(QueryBuilders.termQuery(field + ".keyword", name));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            } else if (matchMethod.equalsIgnoreCase("NEAR")) {
                userBoolQueryBuilder = (field, boolQueryBuilder) -> boolQueryBuilder.must(QueryBuilders.matchQuery(field, name).operator(Operator.AND));
            } else if (matchMethod.equalsIgnoreCase("LIKE")) {
                userBoolQueryBuilder = (field, boolQueryBuilder) -> {
                    BoolQueryBuilder shouldQuery = QueryBuilders.boolQuery();
                    try {
                        BoolQueryBuilder bool = QueryBuilders.boolQuery();
                        bool.must(QueryBuilders.termQuery(field + ".keyword", name));
                        shouldQuery.should(bool);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    shouldQuery.should(QueryBuilders.matchQuery(field, name).operator(Operator.AND));
                    boolQueryBuilder.must(shouldQuery);
                };
            }
            condition.put("tmName user", userBoolQueryBuilder);
        }

        //注册号
        String regNo = (String) param.get("regNo");
        if (StringUtils.isNotEmpty(regNo)) {
            condition.put("regNum keyword", regNo);
        }

        //申请人
        String applicantCn = (String) param.get("applicantCn");
        if (StringUtils.isNotEmpty(applicantCn)) {
            condition.put("regName keyword", applicantCn);
        }

        //代理机构
        String tmSection = (String) param.get("tmSection");
        if (StringUtils.isNotEmpty(tmSection)) {
            condition.put("tmSection keyword", tmSection);
        }

        //申请类别
        String intCls = (String) param.get("intCls");
        if (StringUtils.isNotEmpty(intCls)) {
            Map<Integer, String> typeExists = getDistinctNumericTypeText(data_index, data_type, new HashMap<>(), "intCls");
            List<String> patterns = new LinkedList<>();
            for (String intClsText : intCls.split(",")) {
                String code = "";
                String text = "";
                if (intClsText.matches("\\d+_[^_]+")) {
                    String[] parts = intClsText.split("_");
                    code = parts[0];
                    text = parts[1];
                } else if (intClsText.matches("\\d+[^_\\d]+")) {
                    Pattern pattern = Pattern.compile("[\\d]+");
                    Matcher matcher = pattern.matcher(intClsText);
                    matcher.find();
                    code = matcher.group();
                    text = intClsText.substring(code.length());
                } else if (intClsText.matches("\\d+\\-[^\\-]+")) {
                    String[] parts = intClsText.split("\\-");
                    code = parts[0];
                    text = parts[1];
                }
                while (code.startsWith("0")) {
                    code = code.substring(1);
                }
                Integer codeInt = Integer.valueOf(code);
                if (!typeExists.containsKey(codeInt)) {
                    patterns.add("第" + code + "类 " + text);
                    patterns.add("第" + code + "类-" + text);
                    patterns.add(code + "类 " + text);
                    patterns.add(code + "类-" + text);
                    patterns.add("第" + code + " " + text);
                    patterns.add("第" + code + "-" + text);
                    patterns.add(code + " " + text);
                    patterns.add(code + "-" + text);
                    patterns.add(code);
                    patterns.add(text);
                } else {
                    patterns.add(typeExists.get(codeInt));
                }
            }

            condition.put("intCls keywordIn", patterns);
        }
    }

    @Override
    public Map<Integer, String> getDistinctNumericTypeText(String index, String
            type, Map<String, Object> condition, String field) {
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms(field).field(field.concat(".keyword")).size(Integer.MAX_VALUE);
        AggregationBuilder countAggregationBuilder = AggregationBuilders.count("count").field(field);
        aggregationBuilder.subAggregation(countAggregationBuilder);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        EsUtils.addConditionToBoolQueryBuilder(boolQueryBuilder, condition);
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .addAggregation(aggregationBuilder)
                .setQuery(boolQueryBuilder);
        searchRequestBuilder.setFrom(0).setSize(1);
        Map<String, Long> reduce = new LinkedHashMap<>();
        SearchResponse searchResponse = searchRequestBuilder.get();
        Aggregations terms = searchResponse.getAggregations();
        for (Aggregation a : terms) {
            StringTerms teamSum = (StringTerms) a;
            for (StringTerms.Bucket bucket : teamSum.getBuckets()) {
                reduce.put(bucket.getKeyAsString(), ((ValueCount) bucket.getAggregations().asMap().get("count")).getValue());
            }
        }
        Map<Integer, String> typeMapping = new LinkedHashMap<>();
        reduce.keySet().stream().forEach(text -> {
            if (StringUtils.isNotEmpty(text)) {
                Pattern numeric = Pattern.compile("\\d+");
                Matcher matcher = numeric.matcher(text);
                if (matcher.find()) {
                    String num = matcher.group();
                    typeMapping.put(Integer.valueOf(num), text);
                } else {
                    log.info(text);
                }
            }
        });

        Map<Integer, String> sortedMap = new LinkedHashMap<>();
        List<Integer> list = typeMapping.keySet().stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            Integer cls = list.get(i);
            sortedMap.put(cls, typeMapping.get(cls));
        }
        return sortedMap;
    }


    @Override
    public Map<Integer, List<String>> getDistinctNumericTypeMultiText(String index, String
            type, Map<String, Object> condition, String field) {
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms(field).field(field.concat(".keyword")).size(Integer.MAX_VALUE);
        AggregationBuilder countAggregationBuilder = AggregationBuilders.count("count").field(field);
        aggregationBuilder.subAggregation(countAggregationBuilder);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        EsUtils.addConditionToBoolQueryBuilder(boolQueryBuilder, condition);
        SearchRequestBuilder searchRequestBuilder = EsClientUtils.getClient().prepareSearch(index)
                .setTypes(type)
                .addAggregation(aggregationBuilder)
                .setQuery(boolQueryBuilder);
        searchRequestBuilder.setFrom(0).setSize(1);
        Map<String, Long> reduce = new LinkedHashMap<>();
        SearchResponse searchResponse = searchRequestBuilder.get();
        Aggregations terms = searchResponse.getAggregations();
        for (Aggregation a : terms) {
            StringTerms teamSum = (StringTerms) a;
            for (StringTerms.Bucket bucket : teamSum.getBuckets()) {
                reduce.put(bucket.getKeyAsString(), ((ValueCount) bucket.getAggregations().asMap().get("count")).getValue());
            }
        }
        Map<Integer, List<String>> typeMapping = new LinkedHashMap<>();
        reduce.keySet().stream().forEach(text -> {
            if (StringUtils.isNotEmpty(text)) {
                Pattern numeric = Pattern.compile("\\d+");
                Matcher matcher = numeric.matcher(text);
                if (matcher.find()) {
                    String num = matcher.group();
                    Integer key = Integer.valueOf(num);
                    List<String> data = typeMapping.get(key);
                    if (data == null) {
                        data = new ArrayList<>();
                    }
                    data.add(text);
                    typeMapping.put(key, data);
                } else {
                    log.info(text);
                }
            }
        });
        Map<Integer, List<String>> sortedMap = new LinkedHashMap<>();
        List<Integer> list = typeMapping.keySet().stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            Integer cls = list.get(i);
            sortedMap.put(cls, typeMapping.get(cls));
        }
        return sortedMap;
    }
}
