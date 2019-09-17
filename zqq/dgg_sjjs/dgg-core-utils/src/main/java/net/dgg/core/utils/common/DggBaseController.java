package net.dgg.core.utils.common;

import net.dgg.core.utils.bean.DggRestResponse;
import net.dgg.core.utils.common.DggConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础控制器
 *
 * @author nature
 * @create 2017-12-20 16:02
 */
public abstract class DggBaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取成功返回值
     *
     * @param data 数据内容
     * @param <T>  返回值中内容的类型
     * @return 返回值
     */
    protected <T> DggRestResponse<T> getSuccessResponse(T data) {
        return this.buildResponse(DggConstants.SUCCESS_CODE, "请求成功", data);
    }

    /**
     * 获取失败的返回值
     *
     * @param msg 返回消息
     * @param <T> 返回值中内容的类型
     * @return 返回值
     */
    protected <T> DggRestResponse<T> getFailResponse(String msg) {
        return this.buildResponse(DggConstants.FAIL_CODE, msg, null);
    }

    /**
     * 构造返回值
     *
     * @param code 返回值code
     * @param msg  返回消息
     * @param data 数据内容
     * @param <T>  返回值中内容的类型
     * @return 返回值
     */
    protected <T> DggRestResponse<T> buildResponse(int code, String msg, T data) {
        DggRestResponse<T> response = new DggRestResponse<>();
        response.setData(data);
        response.setCode(code);
        response.setMsg(msg);

        return response;
    }
}
