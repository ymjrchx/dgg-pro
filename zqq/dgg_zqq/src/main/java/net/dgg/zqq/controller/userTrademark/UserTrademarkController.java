package net.dgg.zqq.controller.userTrademark;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.entity.userTrademark.UserCompany;
import net.dgg.zqq.entity.userTrademark.UserTrademark;
import net.dgg.zqq.services.userTrademark.UserTrademarkService;
import net.dgg.zqq.utils.FileUtils;
import net.dgg.zqq.utils.StringUtils;
import net.fblock.core.common.KeyWorker;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userTrademark")
@Api(description = "商标管理")
public class UserTrademarkController extends BaseController {


    @Autowired
    private UserTrademarkService userTrademarkService;

    /*
     * 企业查询接口 用户输入企业名称 查询出企业信息
     */
    @RequestMapping(value = "/findCompanyByName", method = RequestMethod.GET)
    @ApiOperation(value = "企业查询接口", notes = "查询导入企业",httpMethod = "GET")
    @ResponseBody
    public RestResponse findCompanyByName(@ApiParam(name = "name", value = "企业名称", required = true) @RequestParam String name,
                                          @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId){
        try{
            Map map = new HashMap();
            List list =  userTrademarkService.findTrademarkByName(name,userId);
            map.put("list",list);
            map.put("count",list.size());
            return this.getSuccessResponse(map);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


    /*
     * 导入企业和企业下的商标
     */
    @RequestMapping(value = "/importCompany", method = RequestMethod.POST)
    @ApiOperation(value = "导入企业下的商标", notes = "根据企业名称数组导入企业下的商标", httpMethod = "POST")
    @ResponseBody
    public RestResponse importCompany(@ApiParam(name = "applicantCns", value = "企业名称数组JSON串", required = true) @RequestParam String applicantCns,
                                      @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId) {

        try{
            Assert.hasText(applicantCns, "applicantCns不能为空");
            Gson gson = new Gson();
            List<String> applicantCnsList = gson.fromJson(applicantCns, new TypeToken<List<String>>() {
            }.getType());
            userTrademarkService.importTrademark(applicantCnsList, userId);
           return this.getSuccessResponse("数据导入成功");
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


    /*
     *  查询用户下的企业
     */
    @RequestMapping(value = "/findImportCompanyByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户已导入（已删除）企业", notes = "根据userId查询企业",httpMethod = "GET")
    @ResponseBody
    public RestResponse findImportCompanyByUserId(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "flag", value = "0：已删除；1：已导入", required = true) @RequestParam Integer flag,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页", required = true) @RequestParam Integer pageNum) {
        try {
            Map map = new HashMap();
            List<UserCompany> list = userTrademarkService.findCompanyByUserId(userId,flag,pageSize, pageNum);
            map.put("list",list);
            map.put("count",list.size());
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


    /*
     *  删除用户下的企业
     */
    @RequestMapping(value = "/delCompanyByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户已导入的企业", notes = "根据企业id删除用户已导入的企业",httpMethod = "GET")
    @ResponseBody
    public RestResponse delCompanyByUserId(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "ids", value = "企业id数组，用逗号分隔", required = true) @RequestParam String ids) {
        try {
            userTrademarkService.delCompanyByUserId(userId,ids);
            return this.getSuccessResponse("删除企业和企业下的商标成功");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }



     /*
      *  根据商标名称和用户ID查询商标 分页
      */
    @RequestMapping(value = "/findByUserIdAndName", method = RequestMethod.GET)
    @ApiOperation(value = "根据商标名称查询商标", notes = "根据商标名称（可不传）查询",httpMethod = "GET")
    @ResponseBody
    public RestResponse findByIdAndUserIdAndName(
            @ApiParam(name = "name", value = "商标名称", required = false) @RequestParam(value="name",required = false) String name,
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam(value="pageSize") Integer pageSize,
            @ApiParam(name = "pageNum", value = "当前页码", required = true) @RequestParam(value="pageNum") Integer pageNum){
        try{
            Map<String,Object> data = userTrademarkService.findByNameAndUserId(name,userId,pageSize,pageNum);
            return this.getSuccessResponse(data);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /*
     * 删除用户下指定商标
     */
    @RequestMapping(value = "/deleteArr", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户下指定商标(数组)", notes = "根据id删除用户下的商标",httpMethod = "GET")
    @ResponseBody
    public RestResponse deleteArr(
            @ApiParam(name = "ids", value = "商标ID数组", required = true) @RequestParam String ids,
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId){
        try{
            Assert.notNull(ids,"ids不能为空");
            String[] ids1 = ids.split(",");
            Long[] ids2 = paseArr(ids1);
            userTrademarkService.deleteArr(ids2,userId);
            return this.getSuccessResponse("删除成功");
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }


    private Long[] paseArr(String[] ids1) {
        Long[] ids = new Long[ids1.length];
        for (int i = 0; i < ids1.length; i++) {
            ids[i]=Long.parseLong(ids1[i]);
        }
        return ids;
    }


    /*
     *   导出excel
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    @ApiOperation(value = "导出excel", notes = "将用户下的商标导出到Excel中",httpMethod = "GET")
    @ResponseBody
    public RestResponse exportExcel(@ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId, HttpServletRequest request, HttpServletResponse response){
        try{
            //获取数据
            List<UserTrademark> list = userTrademarkService.findByAll(userId);
            Assert.isTrue(list.size() > 0, "该用户没有导入商标数据");
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            HSSFSheet sheet = hssfWorkbook.createSheet("商标数据");
            sheet.setColumnWidth(0, 30 * 256);
            sheet.setColumnWidth(1, 30 * 256);
            sheet.setColumnWidth(2, 30 * 256);
            sheet.setColumnWidth(3, 30 * 256);
            sheet.setColumnWidth(4, 30 * 256);
            sheet.setColumnWidth(5, 30 * 256);

            //创建表头
            HSSFRow headRow = sheet.createRow(0);
            headRow.setHeightInPoints(20);
            headRow.createCell(0).setCellValue("商标名称");
            headRow.createCell(1).setCellValue("注册号");
            headRow.createCell(2).setCellValue("类别");
            headRow.createCell(3).setCellValue("申请日期");
            headRow.createCell(4).setCellValue("状态");
            headRow.createCell(5).setCellValue("申请人");


            //表格数据
            for (UserTrademark userTrademark : list) {
                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.setHeightInPoints(20);
                HSSFCell cell0 = dataRow.createCell(0);
                if (!StringUtils.isEmpty(userTrademark.getName())) {
                    cell0.setCellValue(userTrademark.getName());
                }
                HSSFCell cell1 =dataRow.createCell(1);
                if (!StringUtils.isEmpty(userTrademark.getRegisterNo())) {
                    cell1.setCellValue(userTrademark.getRegisterNo());
                }
                HSSFCell cell2 =dataRow.createCell(2);
                if (!StringUtils.isEmpty(userTrademark.getClassLevel1Code())) {
                    cell2.setCellValue(userTrademark.getClassLevel1Code());
                }

                HSSFCell dataCell = dataRow.createCell(3);
                HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
                HSSFDataFormat format= hssfWorkbook.createDataFormat();
                cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));
                dataCell.setCellStyle(cellStyle);
                if(userTrademark.getApplyDate()!=null){
                    dataCell.setCellValue(userTrademark.getApplyDate());
                }

                HSSFCell cell4 = dataRow.createCell(4);
                if (!StringUtils.isEmpty(userTrademark.getStatus())) {
                    cell4.setCellValue(userTrademark.getStatus());
                }

                HSSFCell cell5 = dataRow.createCell(5);
                if (!StringUtils.isEmpty(userTrademark.getApplicant())) {
                    cell5.setCellValue(userTrademark.getApplicant());
                }
            }

            //下载导出
            response.setContentType("application/vnd.ms-excel");
            String filename = "商标数据"+ KeyWorker.nextId()+".xls";
            String agent = request.getHeader("user-agent");
            filename = FileUtils.encodeDownloadFilename(filename,agent);
            response.setHeader("Content-Disposition","attachment;filename="+filename);
            ServletOutputStream ouputStream = response.getOutputStream();
            hssfWorkbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();

            return null;
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /* *//*
     * 查询用户已删除的商标
     *//*
    @RequestMapping(value = "/findDeleteByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户已删除的商标", notes = "根据userId查询已经删除的商标",httpMethod = "GET")
    @ResponseBody
    public RestResponse findDeleteByUserId(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "count", value = "查询条数", required = true) @RequestParam Integer count
            ){
        try{
            List<UserTrademark> list = userTrademarkService.findDelete(userId,count);
            return this.getSuccessResponse(list);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }*/


   /* *//*
     *   根据商标ID查询某个商标的详情
     *//*
    @RequestMapping(value = "/findByIdAndUserId", method = RequestMethod.GET)
    @ApiOperation(value = "查询商标详情", notes = "根据商标ID查询某个商标的详情",httpMethod = "GET")
    @ResponseBody
    public RestResponse findByIdAndUserId(
            @ApiParam(name = "id", value = "商标ID", required = true) @RequestParam Long id,
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId){
        try{
            UserTrademark userTrademark = userTrademarkService.findById(id,userId);
            return this.getSuccessResponse(userTrademark);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }

    }*/





}
