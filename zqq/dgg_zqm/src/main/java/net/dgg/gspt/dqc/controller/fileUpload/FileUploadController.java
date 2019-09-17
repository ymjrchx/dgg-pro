package net.dgg.gspt.dqc.controller.fileUpload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.constant.UploadFileType;
import net.dgg.gspt.dqc.constant.UserIdKeyConstant;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import net.dgg.gspt.dqc.services.fileUpload.FileUploadService;
import net.dgg.gspt.dqc.utils.StringUtils;
import net.dgg.tmd.foundation.platform.session.CommonSession;
import net.dgg.tmd.foundation.platform.session.SessionManager;
import net.dgg.tmd.foundation.platform.user.dao.UserRecorderDAO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import net.fblock.web.common.BaseController;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <FileUploadController>
 * @despriction 文件上传
 * @create 2018/10/10 9:45
 **/
@RequestMapping("/file")
@Api(description = "文件")
@Controller
public class FileUploadController extends BaseController {
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserRecorderDAO userRecorderDAO;

    @RequestMapping(value = "singleUpload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "单文件上传", notes = "单文件上传", httpMethod = "POST")
    public Object singleUpload(HttpServletRequest request,
                               @ApiParam(name = "userId", value = "用户id", required = false) @RequestParam(required = false) String userId,
                               @ApiParam(name = "file", value = "单文件", required = true) @RequestParam("file") MultipartFile file,
                               @ApiParam(name = "type", value = "文件用途", required = true) @RequestParam String type,
                               @ApiParam(name = "dataId", value = "文件绑定的业务数据ID", required = false) @RequestParam(required = false) String dataId) {
        try {
            //this.checkUserLogin(userId);
            List<Map> maps = fileUploadService.upload(type, dataId, file);
            return this.getSuccessResponse(maps.get(0));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "multiUpload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "多文件上传", notes = "多文件上传", httpMethod = "POST")
    public Object MultiUpload(HttpServletRequest request,
                              @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                              @ApiParam(name = "files", value = "多文件", required = true) @RequestPart("file") MultipartFile[] files,
                              @ApiParam(name = "type", value = "文件用途", required = true) @RequestParam String type,
                              @ApiParam(name = "dataId", value = "文件绑定的业务数据ID", required = false) @RequestParam(required = false) String dataId) {
        try {
            this.checkUserLogin(userId);
            List<Map> maps = fileUploadService.upload(type, dataId, files);
            return this.getSuccessResponse(maps);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "htmlEditorUpload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "富文本编辑器上传", notes = "富文本编辑器上传", httpMethod = "POST")
    public Object htmlEditorUpload(HttpServletRequest request,
                                   @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                   @ApiParam(name = "imgFile", value = "单文件", required = true) @RequestParam("imgFile") MultipartFile file,
                                   @ApiParam(name = "type", value = "文件用途", required = true) @RequestParam String type,
                                   @ApiParam(name = "dataId", value = "文件绑定的业务数据ID", required = false) @RequestParam(required = false) String dataId) {
        try {
            this.checkUserLogin(userId);
            List<Map> maps = fileUploadService.upload(type, dataId, file);
            Map reMap = maps.get(0);
            String url = String.valueOf(reMap.get("fsHost")).concat(String.valueOf(reMap.get("fsPath")));
            Map resultMap = new HashMap();
            resultMap.put("error", 0);
            resultMap.put("url", url);
            JSONObject json = JSONObject.fromObject(resultMap);
            return json.toString();
        } catch (Exception e) {
            Map resultMap = new HashMap();
            resultMap.put("error", 1);
            resultMap.put("message", "上传失败");
            JSONObject json = JSONObject.fromObject(resultMap);
            return json.toString();
        }
    }

    @RequestMapping(value = "createTrademark", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "创建文字商标", notes = "创建文字商标", httpMethod = "GET")
    public Object createTrademark(HttpServletRequest request,
                                  @ApiParam(name = "userId", value = "用户id", required = false) @RequestParam(required = false) String userId,
                                  @ApiParam(name = "text", value = "商标文字", required = true) @RequestParam String text,
                                  @ApiParam(name = "imgWidth", value = "图片宽度", required = true) @RequestParam Integer imgWidth,
                                  @ApiParam(name = "imgHeight", value = "图片高度", required = true) @RequestParam Integer imgHeight) {
        try {
            Map reMap = this.fileUploadService.createTrademark(userId, text, imgWidth, imgHeight, UploadFileType.AUTO_ORDER_TRADEMARK);
            return this.getSuccessResponse(reMap);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "trademarkSearchUpload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "商标搜索文件上传", notes = "商标搜索文件上传", httpMethod = "POST")
    public Object trademarkSearchUpload(HttpServletRequest request,
                                        @ApiParam(name = "file", value = "单文件", required = true) @RequestParam("file") MultipartFile file) {
        try {
            List<Map> maps = fileUploadService.upload(UploadFileType.TRADEMARK_SEARCH, null, file);
            return this.getSuccessResponse(maps.get(0));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "multiUploadBackground", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "后台多文件上传", notes = "后台多文件上传", httpMethod = "POST")
    public Object multiUploadBackground(HttpServletRequest request,
                                        @ApiParam(name = "file", value = "多文件", required = true) @RequestPart("file") MultipartFile[] files,
                                        @ApiParam(name = "type", value = "文件用途", required = true) @RequestParam String type,
                                        @ApiParam(name = "dataId", value = "文件绑定的业务数据ID", required = false) @RequestParam(required = false) String dataId) {
        try {
            boolean login = false;
            // 后台登录验证
            CommonSession session = sessionManager.getCurrentSession();
            if (null != session) {
                Long uId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
                UserEntity userEntity = uId == null ? null : userRecorderDAO.findUserEntityByUserId(uId);
                login = null != userEntity;
            }
            Assert.isTrue(login, "未登录用户无法上传文件！");
            List<Map> maps = fileUploadService.upload(type, dataId, files);
            return this.getSuccessResponse(maps);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除文件", notes = "删除文件", httpMethod = "POST")
    public Object delete(HttpServletRequest request,
                         @ApiParam(name = "groupPath", value = "group路径", required = true) @RequestParam("groupPath") String groupPath) {
        try {
            fileUploadService.delete(groupPath);
            return this.getSuccessResponse(null);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    private void checkUserLogin(String userId) {
        boolean login = false;
        // 网站登录验证
        if (!StringUtils.isEmpty(userId) && RedisUtils.exists(userId)) {
            login = true;
        }
        // 后台登录验证
        CommonSession session = sessionManager.getCurrentSession();
        if (null != session) {
            Long uId = sessionManager.getCurrentSession().getValue(UserIdKeyConstant.KEY, Long.class);
            UserEntity userEntity = uId == null ? null : userRecorderDAO.findUserEntityByUserId(uId);
            login = null != userEntity;
        }
        Assert.isTrue(login, "未登录用户无法上传文件！");
    }

}
