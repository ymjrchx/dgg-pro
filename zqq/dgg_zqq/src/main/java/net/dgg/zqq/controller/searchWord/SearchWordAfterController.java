package net.dgg.zqq.controller.searchWord;

import io.swagger.annotations.ApiParam;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.dgg.zqq.entity.searchWord.SearchWord;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.dgg.zqq.services.searchWord.SearchWordService;
import net.dgg.zqq.services.template.TemplateService;
import net.dgg.zqq.utils.StringUtils;
import net.fblock.web.common.BaseController;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/27 11:19
 */
@Controller
@RequestMapping("/searchWord")
public class SearchWordAfterController extends BaseController {

    @Autowired
    SearchWordService searchWordService;
    @Autowired
    TemplateService templateService;
    @Autowired
    FileUploadService fileUploadService;

    //分页查询
    @RequestMapping(value = "/showWord", method = RequestMethod.POST)
    @ResponseBody
    public Object pageQuery(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Map> list = this.searchWordService.pageQuery(params);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, list, "");
    }

    //后台管理搜索词页面
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showIndex(Model model) {
        List<Map> list = this.templateService.pageQuery(new HashMap() {{
            put("templateType", "template_type_99");
        }});
        String host = this.fileUploadService.getHost();
        String url = host.concat((String) list.get(0).get("fileUrl"));
        model.addAttribute("fileUrl", url);
        return "/searchWord/index";
    }

    //删除操作
    @RequestMapping(value = "/removeWord", method = RequestMethod.POST)
    @ResponseBody
    public Object removeWord(@RequestParam Long id) {
        try {
            this.searchWordService.deleteWordById(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    //返回添加页面
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String showAddPage() {
        return "/searchWord/addWord";
    }

    //添加操作
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addWord(@RequestParam String word, @RequestParam int flag) {

        try {
            this.searchWordService.addWord(word, flag);
            return this.getSuccessResponse("success!");
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        }
    }

    //修改启用禁用状态
    @RequestMapping(value = "/updateFlag", method = RequestMethod.POST)
    @ResponseBody
    public Object updateFlag(Long id) {
        try {
            this.searchWordService.updateFlag(id);
            return this.getSuccessResponse("success");
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @ResponseBody
    public Object importData(@ApiParam(name = "file", value = "文件", required = true) @RequestParam("file") MultipartFile file) {
        try {
            Assert.isTrue(!file.isEmpty(), "不能上传空文件！");
            Assert.isTrue(file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx"), "只能上传excel文件");
            // @RequestParam("file") MultipartFile file 是用来接收前端传递过来的文件
            // 1.创建workbook对象，读取整个文档
            InputStream inputStream = file.getInputStream();
            /*POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);*/
            Workbook wb = WorkbookFactory.create(inputStream);
            // 2.读取页脚sheet
            Sheet sheetAt = wb.getSheetAt(0);
            // 3.循环读取某一行
            for (Row row : sheetAt) {
                // 4.读取每一行的单元格
                SearchWord searchWord = new SearchWord();
                if (row.getRowNum() == 0) continue; //第一行不读
                if (!StringUtils.isEmpty(row.getCell(0).getStringCellValue()) && row.getCell(0).getStringCellValue() != null) {
                    searchWordService.addWord1(row.getCell(0).getStringCellValue());
                } else {
                    continue;
                }
            }
            return this.getSuccessResponse("导入数据成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常");
        }
    }

}
