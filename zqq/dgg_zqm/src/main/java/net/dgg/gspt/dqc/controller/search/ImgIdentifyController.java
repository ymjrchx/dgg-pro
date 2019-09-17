package net.dgg.gspt.dqc.controller.search;

import net.dgg.gspt.dqc.config.ImgConfig;
import net.dgg.gspt.dqc.framework.imgidentify.Base64Util;
import net.dgg.gspt.dqc.framework.imgidentify.FileUtil;
import net.dgg.gspt.dqc.framework.imgidentify.HttpUtil;
import net.dgg.gspt.dqc.framework.imgidentify.ImgDealUtils;
import net.dgg.gspt.dqc.framework.interceptor.ConstMethod;
import net.dgg.gspt.dqc.services.search.ImgService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.net.URLEncoder;

/**
 * @ClassName: ImgIdentifyController
 * @Description: 图片识别
 * @Author: jiangsh
 * @Date: 2018/11/14 15:09
 */
@RestController
@RequestMapping("/imgidentify")
public class ImgIdentifyController extends BaseController implements ConstMethod {

    @Resource
    private ImgConfig imgConfig;
    @Resource
    private ImgService imgService;

    /**
     * 身份证识别
     *
     * @param filePath   图片路径
     * @param idCardSide 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
     * @return
     */
    @RequestMapping(value = "/idCard", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse identifyIdCard(@RequestParam String filePath,
                                       @RequestParam String idCardSide) {
        String tmpPath = "";
        try {
            tmpPath = ImgDealUtils.getTmpPath(filePath);
            final byte[] imgData = FileUtil.readFileByBytes(tmpPath);
            final String params = "id_card_side=" + idCardSide + "&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(Base64Util.encode(imgData), "UTF-8");
            return getSuccessResponse(imgService.parseIdCardData(HttpUtil.post(imgConfig.getIdcardIdentificate(), imgConfig.getIdCardAccessToken(), params), idCardSide));
        } catch (IllegalArgumentException e) {
            return getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return getFailResponse("系统异常！");
        } finally {
            ImgDealUtils.delete(tmpPath);
        }
    }


    /**
     * 营业执照识别
     *
     * @param filePath 图片路径
     * @return
     */
    @RequestMapping(value = "/license", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse identifyIdCard(@RequestParam String filePath) {
        String tmpPath = "";
        try {
            tmpPath = ImgDealUtils.getTmpPath(filePath);
            final byte[] imgData = FileUtil.readFileByBytes(tmpPath);
            final String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Base64Util.encode(imgData), "UTF-8");
            return getSuccessResponse(imgService.parseLicenseData(HttpUtil.post(imgConfig.getLicenseIdentificate(), imgConfig.getLicenseAccessToken(), params)));
        } catch (IllegalArgumentException e) {
            return getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return getFailResponse("系统异常！");
        } finally {
            ImgDealUtils.delete(tmpPath);
        }
    }


    /**
     * 下载图片至本地
     *
     * @param filePath 图片路径
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse download(@RequestParam String filePath) {
        try {
            return getSuccessResponse(ImgDealUtils.download(filePath, ImgDealUtils.fileNameDeal(filePath), ImgDealUtils.getSavePath()));
        } catch (Exception e) {
            return getFailResponse("fail：" + e.getMessage());
        }
    }

    /**
     * 删除临时文件
     *
     * @param fileName 文件名
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse delete(@RequestParam String fileName) {
        try {
            ImgDealUtils.delete(ImgDealUtils.getSavePath().concat(File.separator).concat(fileName));
            return getSuccessResponse("success");
        } catch (Exception e) {
            return getFailResponse("fail：" + e.getMessage());
        }
    }
}
