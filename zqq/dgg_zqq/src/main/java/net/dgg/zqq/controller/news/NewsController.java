package net.dgg.zqq.controller.news;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.zqq.constant.UserIdKeyConstant;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.entity.business.treebook.TreeBook;
import net.dgg.zqq.entity.news.News;
import net.dgg.zqq.services.TreeBookService;
import net.dgg.zqq.services.news.NewsService;
import net.dgg.zqq.utils.FileUtils;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/news")
@Api(description = "资讯")
public class NewsController extends BaseController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private TranslateUtil translateUtil;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private TreeBookService treeBookService;


    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiOperation(value = "资讯分页查询接口", notes = "获取资讯列表接口", httpMethod = "POST")
    @ResponseBody
    public RestResponse newsPageQuery(
            @ApiParam(name = "pageSize", value = "分页查询条数", required = true) @RequestParam int pageSize,
            @ApiParam(name = "pageNum", value = "分页查询第几页", required = true) @RequestParam int pageNum,
            @ApiParam(name = "order", value = "排序方式", required = false) @RequestParam(required = false) String order,
            @ApiParam(name = "type", value = "分页查询类型") String type) {
        try {
            return this.getSuccessResponse(newsService.showAllNews(pageSize, pageNum, type, order));
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/pageQueryByLabel", method = RequestMethod.POST)
    @ApiOperation(value = "根据标签查询咨询（分页）", notes = "根据标签查询咨询", httpMethod = "POST")
    @ResponseBody
    public RestResponse pageQueryByLabel(
            @ApiParam(name = "pageSize", value = "分页查询条数", required = false) @RequestParam(required = false) Integer pageSize,
            @ApiParam(name = "pageNum", value = "分页查询第几页", required = true) @RequestParam Integer pageNum,
            @ApiParam(name = "label", value = "咨询标签") @RequestParam(required = false) String label) {
        try {
            return this.getSuccessResponse(newsService.pageQueryByLabel(pageSize, pageNum, label));
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/protal", method = RequestMethod.POST)
    @ApiOperation(value = "首页展示资讯接口", notes = "获取资讯列表接口", httpMethod = "POST")
    @ResponseBody
    public RestResponse showProtal(
            @ApiParam(name = "typeOne", value = "分页查询类型", required = true) @RequestParam String typeOne,
            @ApiParam(name = "typeTwo", value = "分页查询类型", required = true) @RequestParam String typeTwo,
            @ApiParam(name = "typeThree", value = "分页查询类型", required = true) @RequestParam String typeThree) {
        try {
            return this.getSuccessResponse(newsService.showProtalData(typeOne, typeTwo, typeThree));
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    /*@RequestMapping(value = "/content/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "资讯详细接口", notes = "获取每条资讯内容接口",httpMethod = "GET")
    @ResponseBody
    public Object getNewsContent(
            @ApiParam(name = "id", value = "每条资讯ID", required = true) @PathVariable("id") Long id) {
        try {
            return this.getSuccessResponse(newsService.getNewsContent(id));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }*/


    @RequestMapping(value = "/content", method = RequestMethod.GET)
    @ApiOperation(value = "资讯详细接口", notes = "获取每条资讯内容接口", httpMethod = "GET")
    @ResponseBody
    public Object getNewsContent(
            @ApiParam(name = "id", value = "每条资讯ID", required = true) @RequestParam Long id,
            @ApiParam(name = "type", value = "分页查询类型", required = false) @RequestParam(required = false) String type,
            @ApiParam(name = "pageSize", value = "分页查询条数", required = false) @RequestParam(required = false) Integer pageSize,
            @ApiParam(name = "pageNum", value = "分页查询第几页", required = false) @RequestParam(required = false) Integer pageNum) {
        try {
            return this.getSuccessResponse(newsService.getNewsContent(id, type, pageSize, pageNum));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/contentByLabel", method = RequestMethod.GET)
    @ApiOperation(value = "资讯详细接口(根据标签排序)", notes = "资讯详细接口(根据标签排序)", httpMethod = "GET")
    @ResponseBody
    public Object getNewsContentByLabel(
            @ApiParam(name = "id", value = "每条资讯ID", required = true) @RequestParam Long id,
            @ApiParam(name = "label", value = "咨询标签", required = true) @RequestParam String label,
            @ApiParam(name = "pageSize", value = "分页查询条数", required = false) @RequestParam(required = false) Integer pageSize,
            @ApiParam(name = "pageNum", value = "分页查询第几页", required = true) @RequestParam Integer pageNum) {
        try {
            return this.getSuccessResponse(newsService.getNewsContentByLabel(id, label, pageSize, pageNum));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "资讯详细接口", notes = "获取每条资讯内容接口", httpMethod = "GET")
    @ResponseBody
    public Object updatePageDate(
            @ApiParam(name = "id", value = "每条资讯ID", required = true) @PathVariable("id") Long id) {
        try {
            return this.getSuccessResponse(newsService.findById(id));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @GetMapping(value = "/praise/{id}")
    @ApiOperation(value = "资讯点赞接口", notes = "资讯点赞接口", httpMethod = "GET")
    @ResponseBody
    public RestResponse praise(
            @ApiParam(name = "id", value = "资讯ID", required = true) @PathVariable("id") Long id) {
        try {
            return this.getSuccessResponse(this.newsService.praise(id));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @GetMapping(value = "type")
    @ResponseBody
    public RestResponse getNewsType() {
        List<TreeBook> news_types = treeBookService.getNameAndCodeByCode("news_types");
        Map map = new HashMap();
        for (TreeBook treeBook : news_types) {
            map.put(treeBook.getCode(), treeBook.getName());
        }
        return getSuccessResponse(map);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(Map map) {
        try {
            Assert.notNull(map, "查询数据为空!");
            return this.getSuccessResponse(this.newsService.query(map));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/querymap", method = RequestMethod.GET)
    @ResponseBody
    public Object queryMap(Map map) {
        try {
            Assert.notNull(map, "查询数据为空!");
            return this.getSuccessResponse(this.newsService.queryMap(map));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @GetMapping(value = "/counts")
    @ResponseBody
    public Object count(Map map) {
        try {
            Assert.notNull(map, "查询数据为空!");
            return this.getSuccessResponse(this.newsService.count(map));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestParam String news, String content, String newsPhoto) {
        try {
            Assert.hasText(news, "保存数据不能为空!");
            //String s = "["+news +"]";
            News news1 = new Gson().fromJson(news, News.class);
            newsService.save(news1, content, newsPhoto);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @GetMapping(value = "/updatepage")
    public String update(@RequestParam Long id, Model model) {
        String path = "/news/updateNews";
        if (id == null) {
            return path;
        }
        News byId = newsService.findById(id);
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        model.addAttribute("news", byId);
        model.addAttribute("userId", userId);
        return path;
    }

    @GetMapping(value = "/addpage")
    public String addNewsPage(Model model) {
        Long userId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
        model.addAttribute("userId", userId);
        return "/news/addNews";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam String news, String content, String newsPhoto) {
        try {

            Assert.hasText(news, "修改数据不能为空!");
            //String s = "["+news +"]";
            News news1 = new Gson().fromJson(news, News.class);
            newsService.update(news1, content, newsPhoto);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteById(@PathVariable Long id) {
        try {
            Assert.notNull(id, "修改数据为空!");
            newsService.deleteById(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    //分页查询
    @RequestMapping(value = "/showNews", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse newsPageQuery(@RequestParam Map params, HttpServletRequest request) {
        try {
            DataTableRequest r = DataTableUtils.getParam(request);
            Integer counts = newsService.count(params);
            params.put("start", r.getStart());
            params.put("limit", r.getLength());
            params.put("count", counts);
            List<Map> maps = newsService.queryMap(params);

            translateUtil.translateList(new String[]{"type", "status"}, maps);
            Integer count = Integer.parseInt(params.get("count").toString());
            return new DataTableResponse(r.getDraw(), count, count, maps, "");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataTableResponse(0, 0, 0, null, "");
        }
    }

    //后台管理资讯页面
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showIndex() {
        return "/news/newsList";
    }

    /**
     * Excel导入资讯
     */
    @RequestMapping(value = "/importNews", method = RequestMethod.POST)
    @ResponseBody
    public Object importNewsByExc(@RequestParam("file") MultipartFile file,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {
            // @RequestParam("file") MultipartFile file 是用来接收前端传递过来的文件
            // 1.创建workbook对象，读取整个文档
            InputStream inputStream = file.getInputStream();
            /*POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);*/
            Workbook wb = WorkbookFactory.create(inputStream);
            // 2.读取页脚sheet
            Sheet sheetAt = wb.getSheetAt(0);
            // 3.循环读取某一行
            List<Integer> list = new ArrayList<Integer>();
            for (Row row : sheetAt) {
                // 4.读取每一行的单元格
                if (row.getRowNum() == 0) continue; //第一行不读
                News news1 = new News();
                if (!StringUtils.isEmpty(row.getCell(0).getStringCellValue()) && row.getCell(0).getStringCellValue() != null) {
                    news1.setTypeLevel1Name(row.getCell(0).getStringCellValue());
                } else {
                    list.add(row.getRowNum() + 1);
                    continue;
                }//  服务大类
                if (!StringUtils.isEmpty(row.getCell(1).getStringCellValue()) && row.getCell(1).getStringCellValue() != null) {
                    news1.setTitle(row.getCell(1).getStringCellValue());
                } else {
                    list.add(row.getRowNum() + 1);
                    continue;
                }//   标题
                if (!StringUtils.isEmpty(row.getCell(2).getStringCellValue()) && row.getCell(2).getStringCellValue() != null) {
                    news1.setLabel(row.getCell(2).getStringCellValue());
                } else {
                    list.add(row.getRowNum() + 1);
                    continue;
                }//  标签
                if (!StringUtils.isEmpty(row.getCell(3).getStringCellValue()) && row.getCell(3).getStringCellValue() != null) {
                    news1.setOrigin(row.getCell(3).getStringCellValue());
                } else {
                    list.add(row.getRowNum() + 1);
                    continue;
                }//   来源
                /*String type = row.getCell(1).getStringCellValue();//   分类  需要在字典翻译成code
                String status = row.getCell(1).getStringCellValue();//   状态  需要在字典翻译成code*/

                if (!StringUtils.isEmpty(row.getCell(4).getStringCellValue()) && row.getCell(4).getStringCellValue() != null) {
                    String newsPhoto = row.getCell(4).getStringCellValue();
                    String photo = "<img src=\"".concat(newsPhoto).concat("\" alt=\"\" />");
                    news1.setNewsPhoto(photo);
                } //   资讯图片
                if (!StringUtils.isEmpty(row.getCell(5).getStringCellValue()) && row.getCell(5).getStringCellValue() != null) {
                    news1.setContent(row.getCell(5).getStringCellValue());
                } else {
                    list.add(row.getRowNum() + 1);
                    continue;
                } //   文本内容
                newsService.excSave(news1);
            }
            //
            if (list.size() > 0) {
                Workbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = (HSSFSheet) workbook.createSheet("未导入数据");
                HSSFRow r0 = sheet.createRow(0); //未导入数据的行
                //设置表头
                Row row0 = sheetAt.getRow(0);
                Cell cell = r0.createCell(0);
                cell.setCellValue(row0.getCell(0).getStringCellValue());
                cell = r0.createCell(1);
                cell.setCellValue(row0.getCell(1).getStringCellValue());
                cell = r0.createCell(2);
                cell.setCellValue(row0.getCell(2).getStringCellValue());
                cell = r0.createCell(3);
                cell.setCellValue(row0.getCell(3).getStringCellValue());
                cell = r0.createCell(4);
                cell.setCellValue(row0.getCell(4).getStringCellValue());
                cell = r0.createCell(5);
                cell.setCellValue(row0.getCell(5).getStringCellValue());
                cell = r0.createCell(6);
                cell.setCellValue(row0.getCell(6).getStringCellValue());
                for (int i = 0; i < list.size(); i++) {
                    sheetAt.getRow(list.get(i));
                    row0 = sheetAt.getRow(list.get(i));
                    r0 = sheet.createRow(list.get(i));
                    cell = r0.createCell(0);
                    cell.setCellValue(row0.getCell(0).getStringCellValue());
                    cell = r0.createCell(1);
                    cell.setCellValue(row0.getCell(1).getStringCellValue());
                    cell = r0.createCell(2);
                    cell.setCellValue(row0.getCell(2).getStringCellValue());
                    cell = r0.createCell(3);
                    cell.setCellValue(row0.getCell(3).getStringCellValue());
                    cell = r0.createCell(4);
                    cell.setCellValue(row0.getCell(4).getStringCellValue());
                    cell = r0.createCell(5);
                    cell.setCellValue(row0.getCell(5).getStringCellValue());
                    cell = r0.createCell(6);
                    cell.setCellValue(row0.getCell(6).getStringCellValue());
                }
                try {
                    response.setContentType("application/vnd.ms-excel");
                    String filename = "未导入数据" + list.size() + ".xls";
                    String agent = request.getHeader("user-agent");
                    filename = FileUtils.encodeDownloadFilename(filename, agent);
                    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    workbook.write(ouputStream);
                    ouputStream.flush();
                    ouputStream.close();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return this.getSuccessResponse("success");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }

    }


    /**
     * 相似新闻
     * @param limit
     * @param newsLabels
     * @return
     */
    @RequestMapping(value = "/likeNews", method = RequestMethod.POST)
    @ResponseBody
    public Object likeNews(@ApiParam(name = "limit", value = "查询条数", required = true) @RequestParam Integer limit,
                           @ApiParam(name = "newsLabels", value = "标签(多个标签以逗号分隔)", required = true) @RequestParam String newsLabels) {
        try {

            List<News> list = newsService.findByLabel(limit,newsLabels);
            return this.getSuccessResponse(list);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


}
