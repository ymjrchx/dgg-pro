package net.dgg.zqq.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.dao.TreeBookDao;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.entity.order.CommentRecord;
import net.dgg.zqq.services.CommentRecordService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.dgg.iboss.base.util.StringUtils.encryptionNumber;


/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/9/29 8:52
 */

@Controller
@RequestMapping("/Comment")
@Api(description = "评论")
public class CommentRecordController extends BaseController {

    @Autowired
    CommentRecordService commentRecordService;
    @Autowired
    TranslateUtil translateUtil;
    @Autowired
    TreeBookDao treeBookDao;



    /**
     * 获取评价列表接口
     *
     * @param serviceTypeLevel3Code 服务所属三级类别code
     * @param pageIndex             当前页
     * @param pageSize              需要显示的条数
     */
    @RequestMapping(value = "/Record", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取评价列表接口", notes = "获取评价列表接口")
    public Object Record(
            @ApiParam(name = "serviceTypeLevel1Code", value = "一级分类") @RequestParam(required = false) String serviceTypeLevel1Code,
            @ApiParam(name = "serviceTypeLevel3Code", value = "三级分类") @RequestParam(required = false) String serviceTypeLevel3Code,
            @ApiParam(name = "pageIndex", value = "页码", required = true) @RequestParam int pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam int pageSize
    ) {

        Map typeCode = new HashMap();
        if (!StringUtils.isEmpty(serviceTypeLevel1Code)) {
            typeCode.put("serviceTypeLevel1Code", serviceTypeLevel1Code);
        }
        if (!StringUtils.isEmpty(serviceTypeLevel3Code)) {
            typeCode.put("serviceTypeLevel1Code", commentRecordService.getLevel1Code(serviceTypeLevel3Code));
        }
        typeCode.put("flag", 1);
        int total = this.commentRecordService.recordCount(typeCode); //该类的总评价条数
        List<Map> list = new ArrayList<Map>();
        List dataList = this.commentRecordService.queryRecord(typeCode, pageIndex, pageSize, total);
        for (int i = 0; i < dataList.size(); i++) {
            Map map = new HashMap();
            CommentRecord commentRecord = (net.dgg.zqq.entity.order.CommentRecord) dataList.get(i);
            User user = this.commentRecordService.findUser(commentRecord.getUserId());
            if (null != user) {
                map.put("username", encryptionNumber(user.getUsername()));
                map.put("userId", commentRecord.getUserId());
                map.put("level", commentRecord.getLevel());
                map.put("getContent", commentRecord.getContent());
                map.put("createTime", commentRecord.getCreateTime());
                String translate3code = translateUtil.translateCode(commentRecord.getServiceTypeLevel3Code());
                map.put("serviceTypeLevel3Code", translate3code);
                list.add(map);
            }
        }
        //获取好 中 坏 评价分别的条数
        List list1 = this.commentRecordService.GetCount(typeCode, total);
        Map<String, List> listMap = new HashMap<String, List>();
        listMap.put("data", list);
        listMap.put("count", list1);
        if (total > 0)
            return getSuccessResponse(listMap);
        else
            return getFailResponse("未找到相关用户评价");
    }


}
