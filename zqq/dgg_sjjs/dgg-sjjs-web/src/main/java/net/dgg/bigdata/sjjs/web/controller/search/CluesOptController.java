package net.dgg.bigdata.sjjs.web.controller.search;

import net.dgg.bigdata.sjjs.web.constant.ConstMethod;
import net.dgg.bigdata.sjjs.web.entity.dto.search.clues.YkClues;
import net.dgg.bigdata.sjjs.web.entity.dto.search.clues.YkCluesRecord;
import net.dgg.bigdata.sjjs.web.service.search.CluesOptService;
import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggBaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: CluesOptController
 * @Description: 线索操作
 * @Author: jiangsh
 * @Date: 2018/12/24 11:17
 */
@RestController
@RequestMapping("/cluesOpt")
public class CluesOptController extends DggBaseController implements ConstMethod {

    @Resource
    private CluesOptService cluesOptService;

    /**
     * 存储线索领取等等记录信息
     */
    @RequestMapping(value = "/saveCluesRecord", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse saveCluesRecord(@RequestBody YkCluesRecord ycr) {
        if (StringUtils.isEmpty(ycr.getComId())) throw new IllegalArgumentException("参数异常！");
        return getSuccessResponse(cluesOptService.addRecordDocument(ycr));
    }


    /**
     * 存储IBOSS响应线索信息
     */
    @RequestMapping(value = "/saveCluesConver", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse saveCluesConver(@RequestBody YkClues yc) {
        if (StringUtils.isEmpty(yc.getComId())) throw new IllegalArgumentException("参数异常！");
        return getSuccessResponse(cluesOptService.addCluesConverDocument(yc.getComId(), yc));
    }


    /**
     * 获取线索
     */
    @RequestMapping(value = "/getClues", method = RequestMethod.POST)
    @ResponseBody
    public DggRestResponse getClues(@RequestBody String... companyId) {
        if (companyId.length == 0) throw new IllegalArgumentException("请输入必要的公司id！");
        return getSuccessResponse(cluesOptService.getClues(companyId));
    }

}
