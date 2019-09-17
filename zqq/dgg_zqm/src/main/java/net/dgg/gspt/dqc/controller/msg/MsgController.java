package net.dgg.gspt.dqc.controller.msg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.gspt.dqc.controller.treebook.TranslateUtil;
import net.dgg.gspt.dqc.entity.msg.Msg;
import net.dgg.gspt.dqc.services.msg.MsgService;
import net.dgg.iboss.base.util.datatable.DataTableUtils;
import net.dgg.iboss.base.util.datatable.domain.DataTableRequest;
import net.dgg.iboss.base.util.datatable.domain.DataTableResponse;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msg")
@Api(description = "消息")
public class MsgController extends BaseController {
    @Autowired
    private MsgService msgService;
    @Autowired
    private TranslateUtil translateUtil;

    //分页查询
    @RequestMapping(value = "/pagequery", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResponse msgPageQuery(@RequestParam Map params, HttpServletRequest request) {
        DataTableRequest r = DataTableUtils.getParam(request);
        params.put("start", r.getStart());
        params.put("limit", r.getLength());
        List<Msg> msgs = msgService.query(params);
        translateUtil.translateList(new String[]{"status"}, msgs);
        Integer count = Integer.parseInt(params.get("count").toString());
        return new DataTableResponse(r.getDraw(), count, count, msgs, "");
    }

    //消息详情接口
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable Long id) {
        try {
            this.msgService.updateMsgReaded(id); //标记为已读
            return this.getSuccessResponse(this.msgService.findById(id));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(Map map) {
        try {
            return this.getSuccessResponse(this.msgService.query(map));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "个人消息查询接口", notes = "获取消息接口",httpMethod = "GET")
    @ResponseBody
    public Object getPersonalMsg(
            @ApiParam(name = "pageSize", value = "分页查询条数", required = true) @RequestParam int pageSize,
            @ApiParam(name = "pageNum", value = "分页查询第几页", required = true)@RequestParam int pageNum,
            @ApiParam(name = "userId", value = "用户id", required = true)@RequestParam String userId) {
        try {
            Assert.notNull(userId,"未登录");
            return this.getSuccessResponse(this.msgService.getPersonalMsg(pageSize,pageNum,userId));
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
            return this.getSuccessResponse(this.msgService.queryMap(map));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/counts", method = RequestMethod.GET)
    @ResponseBody
    public Object count(@RequestParam String userId) {
        try {
            Assert.notNull(userId, "用户未登录!");
            return this.getSuccessResponse(this.msgService.count(userId));
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object deleteById(@PathVariable Long id) {
        try {
            Assert.notNull(id, "删除消息数据为空!");
            msgService.deleteById(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    //设置消息已读
    @RequestMapping(value = "/readed/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object updateMsgReaded(@PathVariable Long id) {
        try {
            Assert.notNull(id, "设置已读失败");
            msgService.updateMsgReaded(id);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestParam String msg) {
        try {
            Assert.isNull(msg, "保存消息数据为空!");
            String s = "["+msg +"]";
            List<Msg> msgList = new Gson().fromJson(s, new TypeToken<List<Msg>>() {
            }.getType());
            msgService.save(msgList);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(Msg msg) {
        try {
            Assert.isNull(msg, "修改消息数据为空!");
            msgService.update(msg);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }
}
